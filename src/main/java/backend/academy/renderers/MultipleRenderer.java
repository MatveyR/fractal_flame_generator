package backend.academy.renderers;

import backend.academy.FractalImage;
import backend.academy.Pixel;
import backend.academy.Point;
import backend.academy.transformations.Variation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import static backend.academy.renderers.RendererUtils.getRandomPoint;
import static backend.academy.renderers.RendererUtils.rotate;

public class MultipleRenderer implements Renderer {
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final int TIMEOUT = 60;
    static final double X_LIMIT = 1.777;
    static final double Y_LIMIT = 1;
    static final int MULTIPLIER = 2;

    @Override
    public FractalImage render(
        FractalImage image,
        List<Variation> variations,
        int symmetry,
        int samples,
        int iters,
        int threads
    ) {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int num = 0; num < samples; ++num) {
            Point point = getRandomPoint();
            service.submit(() -> {
                for (int i = 0; i < threads; ++i) {
                    renderSample(point, variations, symmetry, image, iters / threads);
                }
            });
        }

        service.shutdown();
        try {
            if (!service.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
        return image;
    }

    @SuppressWarnings("ParameterAssignment")
    private static void renderSample(
        Point point,
        List<Variation> variations,
        int symmetry,
        FractalImage image,
        int repeats
    ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int step = 0; step < repeats; ++step) {
            Variation variation = variations.get(random.nextInt(variations.size()));
            point = variation.trans().apply(point);

            for (int s = 0; s < symmetry; ++s) {
                var rotadedPoint = rotate(point, s * Math.PI * 2 / symmetry);

                int x =
                    (int) (image.width() - (X_LIMIT - rotadedPoint.x()) / (X_LIMIT * MULTIPLIER) * image.width());
                int y =
                    (int) (image.height() - (Y_LIMIT - rotadedPoint.y()) / (Y_LIMIT * MULTIPLIER) * image.height());

                LOCK.readLock().lock();
                Pixel pixel = image.getPixel(x, y);

                if (pixel == null) {
                    LOCK.readLock().unlock();
                    continue;
                }

                if (pixel.hitCount() == 0) {
                    pixel = new Pixel(variation.r(), variation.g(), variation.b(), 1, 0);
                } else {
                    pixel = new Pixel(
                        (variation.r() + pixel.r()) / MULTIPLIER,
                        (variation.g() + pixel.g()) / MULTIPLIER,
                        (variation.b() + pixel.b()) / MULTIPLIER,
                        pixel.hitCount() + 1,
                        pixel.normal()
                    );
                }
                LOCK.readLock().unlock();
                LOCK.writeLock().lock();
                image.setPixel(x, y, pixel);
                LOCK.writeLock().unlock();
            }
        }
    }
}

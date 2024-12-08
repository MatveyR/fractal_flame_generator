package backend.academy.renderers;

import backend.academy.FractalImage;
import backend.academy.Pixel;
import backend.academy.Point;
import backend.academy.transformations.Variation;
import java.util.List;
import java.util.Random;

public class SingleRenderer implements Renderer {
    static final double X_LIMIT = 1.777;
    static final double Y_LIMIT = 1;
    static final int MULTIPLIER = 2;

    static Random rand = new Random();

    @Override
    public FractalImage render(
        FractalImage image,
        List<Variation> variations,
        int symmetry,
        int samples,
        int iters,
        int threads
    ) {
        for (int num = 0; num < samples; ++num) {
            Point point = getRandomPoint();

            for (int step = 0; step < iters; ++step) {
                Variation variation = variations.get(rand.nextInt(variations.size()));
                point = variation.trans().apply(point);

                for (int s = 0; s < symmetry; ++s) {
                    double theta = s * Math.PI * 2 / symmetry;
                    var rotadedPoint = rotate(point, theta);

                    int x = (int)
                        (image.width() - (X_LIMIT - rotadedPoint.x()) / (X_LIMIT * MULTIPLIER) * image.width());
                    int y = (int)
                        (image.height() - (Y_LIMIT - rotadedPoint.y()) / (Y_LIMIT * MULTIPLIER) * image.height());

                    Pixel pixel = image.getPixel(x, y);

                    if (pixel == null) {
                        continue;
                    }

                    Pixel newPixel = getNewPixel(pixel, variation);
                    image.setPixel(x, y, newPixel);
                }
            }
        }
        return image;
    }

    private static Pixel getNewPixel(Pixel pixel, Variation variation) {
        Pixel newPixel;
        if (pixel.hitCount() == 0) {
            newPixel =
                new Pixel(variation.r(), variation.g(), variation.b(), 1, 0);
        } else {
            newPixel = new Pixel(
                (variation.r() + pixel.r()) / 2,
                (variation.g() + pixel.g()) / 2,
                (variation.b() + pixel.b()) / 2,
                pixel.hitCount() + 1,
                pixel.normal()
            );
        }
        return newPixel;
    }

    public static Point getRandomPoint() {
        double newX = rand.nextDouble(-1 * X_LIMIT, X_LIMIT);
        double newY = rand.nextDouble(-1 * Y_LIMIT, Y_LIMIT);
        return new Point(newX, newY);
    }

    public static Point rotate(Point point, double theta) {
        double rotatedX = point.x() * Math.cos(theta) - point.y() * Math.sin(theta);
        double rotatedY = point.x() * Math.sin(theta) + point.y() * Math.cos(theta);

        return new Point(rotatedX, rotatedY);
    }
}

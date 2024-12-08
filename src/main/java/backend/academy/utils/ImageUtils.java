package backend.academy.utils;

import backend.academy.FractalImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;

public class ImageUtils {
    private static final int A = 24;
    private static final int R = 16;
    private static final int G = 8;
    private static final int MAX = 255;
    private static final int BLACK = MAX << A;

    @SneakyThrows
    public void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.width(); ++x) {
            for (int y = 0; y < image.height(); ++y) {
                var pixel = image.getPixel(x, y);
                if (pixel.hitCount() == 0) {
                    bufferedImage.setRGB(x, y, BLACK);
                    continue;
                }
                int color = (MAX << A) | (pixel.r() << R) | (pixel.g() << G) | pixel.b();
                bufferedImage.setRGB(x, y, color);
            }
        }

        File outputFile = new File(filename.toString());
        ImageIO.write(bufferedImage, format.name(), outputFile);
    }
}

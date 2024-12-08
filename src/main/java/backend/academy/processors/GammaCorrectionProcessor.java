package backend.academy.processors;

import backend.academy.FractalImage;
import backend.academy.Pixel;

public class GammaCorrectionProcessor implements ImageProcessor {
    private static final double GAMMA = 2.2;

    @Override
    public void process(FractalImage image) {
        double max = 0.0;
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                Pixel pixel = image.getPixel(row, col);
                if (pixel.hitCount() != 0) {
                    image.setPixel(row, col,
                        new Pixel(pixel.r(), pixel.g(), pixel.b(), pixel.hitCount(), Math.log10(pixel.hitCount())));
                    if (image.getPixel(row, col).normal() > max) {
                        max = image.getPixel(row, col).normal();
                    }
                }
            }
        }
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                Pixel pixel = image.getPixel(row, col);
                double normal = pixel.normal() / max;
                int red = (int) (pixel.r() * Math.pow(normal, (1.0 / GAMMA)));
                int green = (int) (pixel.g() * Math.pow(normal, (1.0 / GAMMA)));
                int blue = (int) (pixel.b() * Math.pow(normal, (1.0 / GAMMA)));
                image.setPixel(row, col, new Pixel(red, green, blue, pixel.hitCount(), normal));
            }
        }
    }
}

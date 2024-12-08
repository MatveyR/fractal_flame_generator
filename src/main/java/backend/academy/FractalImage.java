package backend.academy;

import java.util.Arrays;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[][] data = new Pixel[width][height];
        for (Pixel[] pixels : data) {
            Arrays.fill(pixels, new Pixel(0, 0, 0, 0, 0));
        }
        return new FractalImage(data, width, height);
    }

    boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel getPixel(int x, int y) {
        if (!contains(x, y)) {
            return null;
        }
        return data[y][x];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        if (!contains(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        data[y][x] = pixel;
    }
}

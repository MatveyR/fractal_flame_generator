package backend.academy.renderers;

import backend.academy.Point;
import java.util.Random;

public class RendererUtils {
    static final double X_LIMIT = 1.777;
    static final double Y_LIMIT = 1;
    static final Random RANDOM = new Random();

    private RendererUtils() {}

    public static Point rotate(Point point, double theta) {
        double rotatedX = point.x() * Math.cos(theta) - point.y() * Math.sin(theta);
        double rotatedY = point.x() * Math.sin(theta) + point.y() * Math.cos(theta);
        return new Point(rotatedX, rotatedY);
    }

    public static Point getRandomPoint() {
        double newX = RANDOM.nextDouble(-1 * X_LIMIT, X_LIMIT);
        double newY = RANDOM.nextDouble(-1 * Y_LIMIT, Y_LIMIT);
        return new Point(newX, newY);
    }
}

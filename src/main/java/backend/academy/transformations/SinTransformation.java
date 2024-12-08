package backend.academy.transformations;

import backend.academy.Point;

public class SinTransformation implements Transformation {
    private static final double COEFF = 1.2;

    @Override
    public Point apply(Point point) {
        double newX = Math.sin(point.x());
        double newY = Math.sin(point.y());
        return new Point(COEFF * newX, COEFF * newY);
    }
}

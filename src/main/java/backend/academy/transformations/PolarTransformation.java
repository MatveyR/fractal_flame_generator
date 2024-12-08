package backend.academy.transformations;

import backend.academy.Point;

public class PolarTransformation implements Transformation {
    private static final double COEFF = 4;

    @Override
    public Point apply(Point point) {
        double newX = Math.atan(point.x() / point.y()) / Math.PI;
        double newY = Math.sqrt(point.x() * point.x() + point.y() * point.y()) - 1;
        return new Point(COEFF * newX, newY);
    }
}

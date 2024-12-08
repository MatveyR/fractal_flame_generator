package backend.academy.transformations;

import backend.academy.Point;

public class HeartTransformation implements Transformation {
    private static final double COEFF = 0.5;

    @Override
    public Point apply(Point point) {
        double newX = Math.sqrt(point.x() * point.x() + point.y() * point.y())
            * Math.sin(Math.atan(point.x() / point.y()) * Math.sqrt(point.x() * point.x() + point.y() * point.y()));
        double newY = -Math.sqrt(point.x() * point.x() + point.y() * point.y())
            * Math.cos(Math.atan(point.x() / point.y()) * Math.sqrt(point.x() * point.x() + point.y() * point.y()));
        return new Point(COEFF * newX, COEFF * newY);
    }
}

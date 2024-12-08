package backend.academy.transformations;

import backend.academy.Point;

public class SpiralTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = (1 / Math.sqrt(point.x() * point.x() + point.y() * point.y()))
            * (Math.cos(Math.atan(point.x() / point.y()))
            + Math.sin(Math.sqrt(point.x() * point.x() + point.y() * point.y())));
        double newY = (1 / Math.sqrt(point.x() * point.x() + point.y() * point.y()))
            * (Math.sin(Math.atan(point.x() / point.y()))
            - Math.cos(Math.sqrt(point.x() * point.x() + point.y() * point.y())));
        return new Point(newX, newY);
    }
}

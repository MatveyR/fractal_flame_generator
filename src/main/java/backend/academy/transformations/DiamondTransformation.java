package backend.academy.transformations;

import backend.academy.Point;

public class DiamondTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = Math.sin(Math.atan(point.x() / point.y()))
            * Math.cos(Math.sqrt(point.x() * point.x() + point.y() * point.y()));
        double newY = Math.sin(Math.sqrt(point.x() * point.x() + point.y() * point.y()))
            * Math.cos(Math.atan(point.x() / point.y()));
        return new Point(newX, newY);
    }
}

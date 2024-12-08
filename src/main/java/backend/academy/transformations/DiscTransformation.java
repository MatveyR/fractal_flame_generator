package backend.academy.transformations;

import backend.academy.Point;

public class DiscTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = (Math.atan(point.x() / point.y()) / Math.PI)
            * Math.sin(Math.PI * Math.sqrt(point.x() * point.x() + point.y() * point.y()));
        double newY = (Math.atan(point.x() / point.y()) / Math.PI)
            * Math.cos(Math.PI * Math.sqrt(point.x() * point.x() + point.y() * point.y()));
        return new Point(newX, newY);
    }
}

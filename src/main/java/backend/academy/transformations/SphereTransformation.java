package backend.academy.transformations;

import backend.academy.Point;

public class SphereTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = point.x() / (point.x() * point.x() + point.y() * point.y());
        double newY = point.y() / (point.x() * point.x() + point.y() * point.y());
        return new Point(newX, newY);
    }
}

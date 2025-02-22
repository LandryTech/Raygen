package src;

public class Player {

    private double x;
    private double y;
    private double direction;
    private double speed;

    public Player(double x, double y, double direction, double speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    public double toRad(double degrees) {
        return Math.toRadians(degrees);
    }

    public double[] getPosition() {
        return new double[]{x, y};
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double newDirection) {
        this.direction = newDirection;
    }

    public void setPos(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public void setPos(java.awt.geom.Point2D newPoint) {
        this.x = newPoint.getX();
        this.y = newPoint.getY();
    }
}
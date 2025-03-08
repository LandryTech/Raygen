package src;

import java.awt.geom.Point2D;

public class Player {

    private static double x = 75;
    private static double y = -50;
    private static double direction = 0;
    private static double speed = 0;

    public Player(double x, double y, double direction, double speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    public static double toRad(double degrees) {
        return Math.toRadians(degrees);
    }

    public static Point2D.Double getPosition() {
        return new Point2D.Double(x, y);
    }

    public static double getDirection() {
        return direction;
    }

    public void setDirection(double newDirection) {
        this.direction = newDirection;
    }

    public void setPos(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public void setPos(Point2D newPoint) {
        this.x = newPoint.getX();
        this.y = newPoint.getY();
    }

    public void moveForward(){
        x += Math.cos(toRad(direction)) * speed;
        y += Math.sin(toRad(direction)) * speed;
    }

    public void rotateLeft(double angle){
        direction -= angle;
    }
    public void rotateRight(double angle){
        direction += angle;
    }
}
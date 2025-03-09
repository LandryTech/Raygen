package src;

import java.awt.geom.Point2D;

public class Player {

    private double x = 75;
    private double y = -50;
    private double direction = 180;
    private static final double MOVE_SPEED = 0.2;

    public Player() {}

    public static double toRad(double degrees) {
        return Math.toRadians(degrees);
    }

    public Point2D.Double getPosition() {
        return new Point2D.Double(x, y);
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double newDirection) {
        direction = newDirection % 360;
        if (direction < 0) direction += 360;
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
        System.out.println("Player moving forward");
        x += Math.cos(toRad(direction)) * MOVE_SPEED;
        y += Math.sin(toRad(direction)) * MOVE_SPEED;
    }
    public void moveBackward(){
        System.out.println("Player moving backward");
        x -= Math.cos(toRad(direction)) * MOVE_SPEED;
        y -= Math.sin(toRad(direction)) * MOVE_SPEED;
    }
    public void rotateLeft(double angle){
        System.out.println("Rotating left by " + angle);
        setDirection(direction - angle);
    }
    public void rotateRight(double angle){
        System.out.println("Rotating right by " + angle);
        setDirection(direction + angle);
    }
}
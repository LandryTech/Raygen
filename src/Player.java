package src;

import java.awt.geom.Point2D;

/**
 * The Player class represents the player in the game. It manages the player's position,
 * direction, and movement.
 */
public class Player {
    // Player's position and direction
    private double x = 10; // X-coordinate of the player's position
    private double y = 10; // Y-coordinate of the player's position
    private double direction = 45; // Player's facing direction in degrees
    private static final double MOVE_SPEED = 0.35; // Speed at which the player moves

    /**
     * Default constructor for the Player class.
     */
    public Player() {}

    /**
     * Convert an angle from degrees to radians.
     *
     * @param degrees The angle in degrees.
     * @return The angle in radians.
     */
    public static double toRad(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Returns the player's current position as a Point2D.Double object.
     *
     * @return The player's position.
     */
    public Point2D.Double getPosition() {
        return new Point2D.Double(x, y);
    }

    /**
     * Returns the player's current direction in degrees.
     *
     * @return The player's direction.
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Sets the player's direction to a new value (in degrees).
     * Ensures the direction is within the range [0,360).
     *
     * @param newDirection The new direction in degrees
     */
    public void setDirection(double newDirection) {
        direction = newDirection % 360;
        if (direction < 0) direction += 360;
    }

    /**
     * Sets the player's position using explicit X and Y coordinates.
     *
     * @param newX The new X-coordinate
     * @param newY The new Y-coordinate
     */
    public void setPos(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * Sets the player's position using a Point2D object.
     *
     * @param newPoint The new position as a Point2D object.
     */
    public void setPos(Point2D newPoint) {
        this.x = newPoint.getX();
        this.y = newPoint.getY();
    }

    /**
     * Moves the player forward based on their current direction.
     */
    public void moveForward(){
        x += Math.cos(toRad(direction)) * MOVE_SPEED;
        y += Math.sin(toRad(direction)) * MOVE_SPEED;
    }

    /**
     * Moves the player backward based on their current direction.
     */
    public void moveBackward(){
        x -= Math.cos(toRad(direction)) * MOVE_SPEED;
        y -= Math.sin(toRad(direction)) * MOVE_SPEED;
    }

    /**
     * Moves the player to the left (strafe left) based on their current direction.
     */
    public void strafeLeft() {
        x += (Math.cos(toRad(direction - 90)) * MOVE_SPEED) / 2;
        y += (Math.sin(toRad(direction - 90)) * MOVE_SPEED) / 2;
    }

    /**
     * Moves the player to the right (strafe right) based on their current direction.
     */
    public void strafeRight() {
        x += (Math.cos(toRad(direction + 90)) * MOVE_SPEED) / 2;
        y += (Math.sin(toRad(direction + 90)) * MOVE_SPEED) / 2;
    }

    /**
     * Rotates the player to the left by a specified angle.
     *
     * @param angle The angle to rotate (in degrees)
     */
    public void rotateLeft(double angle){
        setDirection(direction - angle);
    }

    /**
     * Rotates the player to the right by a specified angle.
     *
     * @param angle The angle to rotate (in degrees)
     */
    public void rotateRight(double angle){
        setDirection(direction + angle);
    }
}
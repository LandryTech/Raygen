package src;

import java.awt.geom.Point2D;

/**
 * The Player class represents the player in the game. It manages the player's position,
 * direction, and movement with collision detection.
 */
public class Player {
    // Player's position and direction
    private double x = 10; // X-coordinate of the player's position
    private double y = 10; // Y-coordinate of the player's position
    private double direction = 45; // Player's facing direction in degrees
    private static double MOVE_SPEED = 0.35; // Speed at which the player moves
    private CollisionManager collisionManager; // Reference to the collision manager

    /**
     * Default constructor for the Player class.
     */
    public Player() {
        // Will be initialized in setCollisionManager
    }

    /**
     * Sets the collision manager for this player.
     *
     * @param collisionManager The collision manager to use
     */
    public void setCollisionManager(CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

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
        // Only set the position if it doesn't cause a collision
        Point2D.Double currentPos = getPosition();
        Point2D.Double newPos = new Point2D.Double(newX, newY);

        if (collisionManager == null || !collisionManager.checkCollision(currentPos, newPos)) {
            this.x = newX;
            this.y = newY;
        }
    }

    /**
     * Sets the player's position using a Point2D object.
     *
     * @param newPoint The new position as a Point2D object.
     */
    public void setPos(Point2D newPoint) {
        setPos(newPoint.getX(), newPoint.getY());
    }

    /**
     * Moves the player forward based on their current direction, with collision detection.
     */
    public void moveForward() {
        double newX = x + Math.cos(toRad(direction)) * MOVE_SPEED;
        double newY = y + Math.sin(toRad(direction)) * MOVE_SPEED;
        Point2D.Double currentPos = getPosition();
        Point2D.Double newPos = new Point2D.Double(newX, newY);

        if (collisionManager == null) {
            x = newX;
            y = newY;
        } else {
            // Check if the move would cause a collision
            if (!collisionManager.checkCollision(currentPos, newPos)) {
                x = newX;
                y = newY;
            }
        }
    }

    /**
     * Moves the player backward based on their current direction, with collision detection.
     */
    public void moveBackward() {
        double newX = x - Math.cos(toRad(direction)) * MOVE_SPEED;
        double newY = y - Math.sin(toRad(direction)) * MOVE_SPEED;
        Point2D.Double currentPos = getPosition();
        Point2D.Double newPos = new Point2D.Double(newX, newY);

        if (collisionManager == null) {
            x = newX;
            y = newY;
        } else {
            // Check if the move would cause a collision
            if (!collisionManager.checkCollision(currentPos, newPos)) {
                x = newX;
                y = newY;
            }
        }
    }

    /**
     * Moves the player to the left (strafe left) based on their current direction, with collision detection.
     */
    public void strafeLeft() {
        double newX = x + (Math.cos(toRad(direction - 90)) * MOVE_SPEED) / 2;
        double newY = y + (Math.sin(toRad(direction - 90)) * MOVE_SPEED) / 2;
        Point2D.Double currentPos = getPosition();
        Point2D.Double newPos = new Point2D.Double(newX, newY);

        if (collisionManager == null) {
            x = newX;
            y = newY;
        } else {
            // Check if the move would cause a collision
            if (!collisionManager.checkCollision(currentPos, newPos)) {
                x = newX;
                y = newY;
            }
        }
    }

    /**
     * Moves the player to the right (strafe right) based on their current direction, with collision detection.
     */
    public void strafeRight() {
        double newX = x + (Math.cos(toRad(direction + 90)) * MOVE_SPEED) / 2;
        double newY = y + (Math.sin(toRad(direction + 90)) * MOVE_SPEED) / 2;
        Point2D.Double currentPos = getPosition();
        Point2D.Double newPos = new Point2D.Double(newX, newY);

        if (collisionManager == null) {
            x = newX;
            y = newY;
        } else {
            // Check if the move would cause a collision
            if (!collisionManager.checkCollision(currentPos, newPos)) {
                x = newX;
                y = newY;
            }
        }
    }

    /**
     * Rotates the player to the left by a specified angle.
     *
     * @param angle The angle to rotate (in degrees)
     */
    public void rotateLeft(double angle) {
        setDirection(direction - angle);
    }

    /**
     * Rotates the player to the right by a specified angle.
     *
     * @param angle The angle to rotate (in degrees)
     */
    public void rotateRight(double angle) {
        setDirection(direction + angle);
    }

    /**
     * @return Player Movement Speed
     */
    public double getPlayerSpeed(){return MOVE_SPEED;}

    /**
     * Sets the players speed by to the specified speed
     *
     * @param speed
     */
    public void setPlayerSpeed(double speed){MOVE_SPEED = speed;}
}
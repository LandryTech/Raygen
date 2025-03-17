package src;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * The CollisionManager class handles collision detection and resolution between
 * the player and walls in the game. It prevents the player from walking through walls.
 */
public class CollisionManager {
    private double collisionTolerance; // Minimum distance allowed between player and walls
    private Line2D.Double[] mapData; // Reference to map walls

    /**
     * Constructs a CollisionManager with the specified map data.
     *
     * @param mapData The array of wall lines from the map
     */
    public CollisionManager(Line2D.Double[] mapData) {
        this.mapData = mapData;
        this.collisionTolerance = 1.0; // Default collision tolerance
    }

    /**
     * Default constructor that initializes with current map data.
     */
    public CollisionManager() {
        this(Map.getMapData());
        this.collisionTolerance = 1.0; // Default collision tolerance
    }

    /**
     * Checks if a potential move would result in a collision with any wall.
     *
     * @param currentPosition The player's current position
     * @param targetPosition The position the player is trying to move to
     * @return true if a collision would occur, false otherwise
     */
    public boolean checkCollision(Point2D.Double currentPosition, Point2D.Double targetPosition) {
        if (mapData == null) return false;

        // Create a line representing the player's movement
        Line2D.Double movementLine = new Line2D.Double(currentPosition, targetPosition);

        // Check distance to each wall
        for (Line2D.Double wall : mapData) {
            // First check if we're too close to the wall
            if (wall.ptSegDist(targetPosition) < collisionTolerance) {
                return true; // Collision detected
            }

            // Also check if we're crossing the wall
            if (movementLine.intersectsLine(wall)) {
                return true; // Collision detected
            }
        }

        return false; // No collision
    }

    /**
     * Resolves a collision by returning a safe position.
     * This method is called when a collision is detected to determine where the player should be.
     *
     * @param currentPosition The player's current position
     * @param targetPosition The position the player is trying to move to
     * @return A safe position that doesn't collide with walls
     */
    public Point2D.Double resolveCollision(Point2D.Double currentPosition, Point2D.Double targetPosition) {
        // If there's no collision, return the target position
        if (!checkCollision(currentPosition, targetPosition)) {
            return targetPosition;
        }

        // Otherwise, return the current position (no movement)
        return currentPosition;
    }

    /**
     * Sets the collision tolerance (minimum distance allowed between player and walls).
     *
     * @param tolerance The new collision tolerance
     */
    public void setCollisionTolerance(double tolerance) {
        this.collisionTolerance = tolerance;
    }

    /**
     * Gets the current collision tolerance.
     *
     * @return The current collision tolerance
     */
    public double getCollisionTolerance() {
        return this.collisionTolerance;
    }

    /**
     * Updates the map data reference when a new map is loaded.
     *
     * @param mapData The new map data
     */
    public void updateMapData(Line2D.Double[] mapData) {
        this.mapData = mapData;
    }
}
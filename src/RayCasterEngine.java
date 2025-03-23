package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The RayCasterEngine class is responsible for rendering the game world using raycasting.
 * It extends JPanel to provide a custom drawing surface for the game.
 */
public class RayCasterEngine extends JPanel implements MouseMotionListener {
    private final Player player; // The player object, using to determine the viewpoint
    private int centerX, centerY;
    private Robot robot;
    private double fov; // Field of view in degrees (default is 90 degrees)
    private double mouseSens = 0.4;
    private final Dimension2D resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private int maxRenderDistance; // Maximum distance a ray can travel
    private boolean settingsMenuOpen = false; // Tracks if the settings menu is open
    int width = (int)resolution.getWidth();
    int height= (int)resolution.getHeight();
    public static Line2D.Double[] map = Map.getMapData(); // Map data (walls)
    double[] closestDistances = new double[width]; // Stores the closest intersection distance for each ray


    /**
     * Constructs a RayCasterEngine with the specified player.
     *
     * @param player The player object, used to determine the viewpoint and position.
     */
    public RayCasterEngine(Player player){
        this.player = player;
        this.fov = 90.0; // Default FOV
        this.maxRenderDistance = 250; // Default max render distance
        try{
            this.robot = new Robot();
        }catch(AWTException e){
            e.printStackTrace();
        }
        addMouseMotionListener(this);
        setFocusable(true); // Allow the panel to receive focus
        requestFocusInWindow(); // Request focus for key events
        repaint(); // Trigger an initial paint of the panel

        setupKeyBindings();
    }

    /**
     * Configures key bindings for the game, allowing interaction through keyboard input.
     * Specifically, binds the spacebar to toggle the settings menu.
     */
    private void setupKeyBindings(){
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        KeyStroke spaceKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
        inputMap.put(spaceKeyStroke, "toggleSettingsMenu");
        actionMap.put("toggleSettingsMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSettingsMenu();
                if(settingsMenuOpen){
                    UI ui = Raygen.ui;
                    ui.createSettingsMenu(RayCasterEngine.this, player, Raygen.collisionManager, Raygen.settings);
                }
            }
        });
    }

    /**
     * Toggles the settings menu on or off. If opened, it recenters the mouse cursor.
     */
    public void toggleSettingsMenu(){
        settingsMenuOpen = !settingsMenuOpen;
        if(settingsMenuOpen){
            robot.mouseMove(centerX + getLocationOnScreen().x, centerY + getLocationOnScreen().y);
            showCursor();
        } else {
            hideCursor();
        }
    }


    /**
     * Handles mouse movement events to update the player's direction based on teh cursor position.
     * If the settings menu is open, mouse movement is ignored.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e){
        if(robot == null || settingsMenuOpen) return;

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        int currentMouseX = e.getX();
        int deltaX = currentMouseX - centerX;

        player.setDirection(player.getDirection() + deltaX * mouseSens);
        robot.mouseMove(centerX + getLocationOnScreen().x, centerY + getLocationOnScreen().y);
    }
    @Override
    public void mouseDragged(MouseEvent e){}

    /**
     * Overrides the paint method to render the game world.
     *
     * @param g  the <code>Graphics</code> object used for rendering.
     */
    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        List<Point2D.Double> intersections = castRays(player.getPosition(), player.getDirection(), fov, closestDistances.length);
        drawLevel((Graphics2D) g);
    }

    /**
     * Draws the level by rendering walls based on the closest distances calculated by raycasting.
     *
     * @param g The Graphics2D object used for rendering.
     */
    public void drawLevel(Graphics2D g){
        g.setColor(new Color(0, 20, 220));
        g.fillRect(0, 0, width, height / 2); // Creates the Sky
        g.setColor(new Color(0, 120, 20));
        g.fillRect(0, height / 2, width, height); // Creates the Ground
        for(int i = 0; i < closestDistances.length; i++){
            double distance = closestDistances[i];
            if(distance != Double.MAX_VALUE){
                double wallHeight = 2000 / distance;
                // Creates RGB color based off distance away from wall
                int colored = (int) clamp(Math.ceil(((wallHeight*20) / (distance))), 80, 200);

                g.setColor(new Color(colored, colored, colored)); // Sets color of wall to certain gradient of color

                g.draw(new Line2D.Double(i, (double) height / 2 - wallHeight, i, (double) height / 2 + wallHeight));
            }
        }
        g.scale(2,2);
        g.setColor(Color.BLACK);
        g.drawString(String.format("Direction: %f",player.getDirection()), 50,50);
    }

    /**
     * Used to limit the value of a number between certain parameters
     *
     * @param value The given value to be clamped
     * @param minimum Smallest number allowed
     * @param maximum Largest number allowed
     * @return number between limits
     */
    public static double clamp(double value, double minimum, double maximum){
        return Math.min(maximum, Math.max(minimum, value));
    }

    /**
     * Casts a large amount of rays from the player's position in the specified field of view.
     *
     * @param playerPosition The player's current position.
     * @param playerDirection The player's current direction (in degrees).
     * @param fov The field of view (in degrees).
     * @param rayCount The number of rays to cast.
     * @return A list of intersection points between rays and walls.
     */
    public List<Point2D.Double> castRays(Point2D.Double playerPosition, double playerDirection, double fov, int rayCount){
        List<Point2D.Double> intersections = new ArrayList<>();
        double angleIncrement = fov / rayCount; // Angle between each ray
        double startAngle = playerDirection - (fov / 2); // Starting angle for the first ray

        // Cast rays within the field of view
        for(int i = 0; i < rayCount; i++){
            double angle = startAngle + i * angleIncrement;
            Point2D.Double intersection = castRay(playerPosition, angle, i);
            if(intersection != null){
                intersections.add(intersection); // Add the intersection point to the list
            }
        }
        return intersections;
    }

    /**
     * Casts a single ray form the player's position at the specified angle.
     *
     * @param playerPosition The player's current position.
     * @param angle The angle at which to cast the ray (in degrees).
     * @param rayIndex The index of the ray (used to store the closest distance).
     * @return The closest intersection point between the ray and a wall,
     * or null if no intersection is found.
     */
    public Point2D.Double castRay(Point2D.Double playerPosition, double angle, int rayIndex){
        double radians = toRad(angle); // Converts angle to radians

        //Calculate the end point of the ray
        Point2D.Double rayEnd = new Point2D.Double(
                playerPosition.x + maxRenderDistance * Math.cos(radians),
                playerPosition.y + maxRenderDistance * Math.sin(radians)
        );

        // Create a line representing the ray
        Line2D.Double ray = new Line2D.Double(playerPosition, rayEnd);
        Point2D.Double closestIntersection = null;
        double closestDistance = Double.MAX_VALUE;

        // Check for intersections with each wall
        for(Line2D.Double wall : map){
            if(ray.intersectsLine(wall)){
                Point2D.Double intersection = getIntersection(ray, wall);
                if(intersection != null){
                    double distance = playerPosition.distance(intersection);

                    if(distance < closestDistance){
                        closestDistance = distance;
                        closestIntersection = intersection;
                    }
                }
            }
        }
        closestDistances[rayIndex] = closestDistance; // Store the closest distance for this ray
        return closestIntersection;
    }

    /**
     * Calculates the intersection point between two lines.
     *
     * @param L1 The first line.
     * @param L2 The second line
     * @return The intersection point, or null if the lines are parallel
     */
    public Point2D.Double getIntersection(Line2D.Double L1, Line2D.Double L2){
        double X1 = L1.getX1(), Y1 = L1.getY1();
        double X2 = L1.getX2(), Y2 = L1.getY2();
        double X3 = L2.getX1(), Y3 = L2.getY1();
        double X4 = L2.getX2(), Y4 = L2.getY2();

        // Calculate the denominator for the intersection formula
        double denominator = ((X1 - X2) * (Y3 - Y4) - (Y1 - Y2) * (X3 - X4));
        if (denominator == 0) return null; // Lines are parallel

        // Calculate the intersection components
        double numeratorX = (X1 * Y2 - Y1 * X2); // Represents the numerator for the X-coordinate of the intersection
        double numeratorY = (X3 * Y4 - Y3 * X4); // Represents the numerator for the Y-coordinate of the intersection

        // Calculate the intersection point
        return new Point2D.Double(
                (numeratorX * (X3 - X4) - (X1 - X2) * numeratorY) / denominator,
                (numeratorX * (Y3 - Y4) - (Y1 - Y2) * numeratorY) / denominator
        );
    }

    /**
     * Triggers a repaint of the panel to update the game view.
     */
    public void update(){
        repaint();
    }

    /**
     * Converts an angle from radians to degrees.
     *
     * @param fg The angle in radians
     * @return The angle in degrees
     */
    public double toDeg(double fg) {
        return Math.toDegrees(fg);
    }

    /**
     * Converts an angle from degrees to radians
     *
     * @param fg The angle in degrees
     * @return The angle in radians
     */
    public double toRad(double fg) {
        return Math.toRadians(fg);
    }

    /**
     * Used to hide the cursor while in the game.
     */
    private void hideCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
    }

    /**
     * Shows the cursor while the settings menu is open.
     */
    private void showCursor() {
        setCursor(Cursor.getDefaultCursor());
    }

    public double getMouseSens(){return mouseSens;}
    public void setMouseSens(double mouseSens){this.mouseSens = mouseSens;}
    public void setFOV(double fov){this.fov = fov;}
    public double getFov(){return fov;}
    public void setMaxRenderDistance(int maxRenderDistance){
        this.maxRenderDistance = maxRenderDistance;
    }
    public int getMaxRenderDistance(){
        return maxRenderDistance;
    }
}
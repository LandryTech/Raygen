package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RayCasterEngine extends JPanel {
    private Player player;
    private double fov = 90; // 90 degrees -> should come from settings later
    private final Dimension2D resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private final int maxRenderDistance = 1000;
    int width = (int)resolution.getWidth();
    int height= (int)resolution.getHeight();
    public static Line2D.Double[] lev2 = new Line2D.Double[]{
            new Line2D.Double(0, 0, 0, -50), new Line2D.Double(50, -50, 50, 0),
            new Line2D.Double(40, -50, 50, -50), new Line2D.Double(0, -50, 10, -50),
            new Line2D.Double(10, 0, 0, 0), new Line2D.Double(50, 0, 40, 0),
            new Line2D.Double(0, 0, 0, 0), new Line2D.Double(0, -50, 0, -100),
            new Line2D.Double(0, -100, 100, -100), new Line2D.Double(100, -100, 100, 0),
            new Line2D.Double(50, 0, 100, 0), new Line2D.Double(30, 0, 60, 0),
            new Line2D.Double(60, 0, 60, 60), new Line2D.Double(60, 60, 40, 60),
            new Line2D.Double(40, 40, 40, 60), new Line2D.Double(20, 0, 0, 0),
            new Line2D.Double(50, 50, 30, 50), new Line2D.Double(30, 60, 0, 60),
            new Line2D.Double(0, 60, 0, 0), new Line2D.Double(10, 10, 20, 10),
            new Line2D.Double(40, 0, 40, 20), new Line2D.Double(40, 10, 50, 10),
            new Line2D.Double(20, 10, 20, 20), new Line2D.Double(0, 20, 20, 20),
            new Line2D.Double(30, 10, 30, 40), new Line2D.Double(10, 30, 50, 30),
            new Line2D.Double(0, 40, 30, 40), new Line2D.Double(20, 40, 20, 50),
            new Line2D.Double(10, 50, 10, 60), new Line2D.Double(50, 20, 60, 20),
            new Line2D.Double(50, 40, 60, 40)};
    double[] closestDistances = new double[width];

    public RayCasterEngine(Player player){
        this.player = player;
        setFocusable(true);
        requestFocus();
        repaint();
        //repaint(0,0,(int)width, (int)height);
    }

    public void paint(Graphics g){
        //g.fillRect(0,0,(int)width,(int)height);
        super.paintComponent(g);
        List<Point2D.Double> intersections = castRays(player.getPosition(), player.getDirection(), fov, closestDistances.length);
        drawLevel((Graphics2D) g); //this is funny
    }
    public void drawLevel(Graphics2D g){
        for(int i = 0; i < closestDistances.length; i++){
            double distance = closestDistances[i];
            if(distance != Double.MAX_VALUE){
                double wallHeight = 2000 / distance;
                g.draw(new Line2D.Double(i, (double) height / 2 - wallHeight, i, (double) height / 2 + wallHeight));
            }
        }
    }

    public List<Point2D.Double> castRays(Point2D.Double playerPosition, double playerDirection, double fov, int rayCount){
        List<Point2D.Double> intersections = new ArrayList<>();
        double angleIncrement = fov / rayCount;
        double startAngle = playerDirection - (fov / 2);

        for(int i = 0; i < rayCount; i++){
            double angle = startAngle + i * angleIncrement;
            Point2D.Double intersection = castRay(playerPosition, angle, i);
            if(intersection != null){
                intersections.add(intersection);
            }
        }
        return intersections;
    }

    public Point2D.Double castRay(Point2D.Double playerPosition, double angle, int rayIndex){
        double radians = toRad(angle);

        Point2D.Double rayEnd = new Point2D.Double(
                playerPosition.x + maxRenderDistance * Math.cos(radians),
                playerPosition.y + maxRenderDistance * Math.sin(radians)
        );

        Line2D.Double ray = new Line2D.Double(playerPosition, rayEnd);
        Point2D.Double closestIntersection = null;
        double closestDistance = Double.MAX_VALUE;

        for(Line2D.Double wall : lev2){
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
        closestDistances[rayIndex] = closestDistance;
        return closestIntersection;
    }

    //public void renderScene(ArrayList<Ray> rays){}
    //public void updateSettings(Settings settings) {}
    //public void adjustView(){}

    public Point2D.Double getIntersection(Line2D.Double L1, Line2D.Double L2){
        double X1 = L1.getX1(), Y1 = L1.getY1();
        double X2 = L1.getX2(), Y2 = L1.getY2();
        double X3 = L2.getX1(), Y3 = L2.getY1();
        double X4 = L2.getX2(), Y4 = L2.getY2();

        double denominator = ((X1 - X2) * (Y3 - Y4) - (Y1 - Y2) * (X3 - X4));
        if (denominator == 0) return null;

        double numerp1 = (X1 * Y2 - Y1 * X2);
        double numerp2 = (X3 * Y4 - Y3 * X4);

        return new Point2D.Double(
                (numerp1 * (X3 - X4) - (X1 - X2) * numerp2) / denominator,
                (numerp1 * (Y3 - Y4) - (Y1 - Y2) * numerp2) / denominator
        );
    }

    public void update(){
        repaint();
    }

    public double toDeg(double fg) {
        return Math.toDegrees(fg);
    }
    public double toRad(double fg) {
        return Math.toRadians(fg);
    }
}
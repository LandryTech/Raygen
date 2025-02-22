package src;

public class Ray{
    private double angle;
    private double distance;
    private boolean hitWall;
    private double hitX;
    private double hitY;

    public Ray(){}

    public void cast(Map map, double startX, double startY, double angle){
    }

    public double[] getHitCoordinates(){
        return new double[0];
    }

    public double getAngle(){
        return 0.0;
    }

    public void setAngle(double angle){
    }
}
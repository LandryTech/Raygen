package src;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Map{
    private int width;
    private int height;
    private ArrayList<Double> x1 = new ArrayList<Double>();
    private ArrayList<Double> y1 = new ArrayList<Double>();
    private ArrayList<Double> x2 = new ArrayList<Double>();
    private ArrayList<Double> y2 = new ArrayList<Double>();
    private int lastPosition;
    private int currentMap;
    private String[] mapNames;
    private Line2D.Double[] mapData;
    private int[][] spawnPoints;

    public Line2D.Double[] loadMapData(String fileName){
        return new Line2D.Double[0];
    }

    public void clearMap(){
    }

}
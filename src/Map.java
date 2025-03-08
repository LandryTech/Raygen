package src;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Map{
    private static ArrayList<Double> x1 = new ArrayList<Double>();
    private static ArrayList<Double> y1 = new ArrayList<Double>();
    private static ArrayList<Double> x2 = new ArrayList<Double>();
    private static ArrayList<Double> y2 = new ArrayList<Double>();
    private int currentMap;
    private String[] mapNames = {"maps/default.svg","",""};
    public static Line2D.Double[] mapData;
    private int[][] spawnPoints;

    public Map() throws FileNotFoundException {
       this(0);
    }
    public Map(int selectedMap) throws ArrayIndexOutOfBoundsException {
        getCoordinates(mapNames[selectedMap]);
        loadMapData();
    }

    public void loadMapData(){
        mapData = new Line2D.Double[x1.size()];
        for(int i = 0; i<mapData.length; i++){
            mapData[i] = new Line2D.Double(x1.get(i),y1.get(i), x2.get(i), y2.get(i));
        }
    }

    public static void getCoordinates(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.contains("<line")) {
                    // Extract x1, y1, x2, y2 values from the line element
                    double x1Val = extractValue(line, "x1=\"");
                    double y1Val = extractValue(line, "y1=\"");
                    double x2Val = extractValue(line, "x2=\"");
                    double y2Val = extractValue(line, "y2=\"");

                    // Add the extracted values to the respective lists
                    x1.add(x1Val);
                    y1.add(y1Val);
                    x2.add(x2Val);
                    y2.add(y2Val);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The getCoordinates Method Failed: File not found");
        } catch (NumberFormatException e) {
            System.out.println("The getCoordinates Method Failed: Number format exception");
        }
    }

    private static double extractValue(String line, String attribute) {
        int startIndex = line.indexOf(attribute) + attribute.length();
        int endIndex = line.indexOf("\"", startIndex);
        return Double.parseDouble(line.substring(startIndex, endIndex));
    }

    public void clearMap(){
        mapData = null;
    }

}
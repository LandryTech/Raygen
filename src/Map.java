package src;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Map class is responsible for loading and managing map data for the game.
 * It reads map data from SVG files and converts it into a format usable by the game.
 */
public class Map{
    // Lists to store coordinates of line segments from the map file
    private static ArrayList<Double> x1 = new ArrayList<Double>(); // Starting X coordinates
    private static ArrayList<Double> y1 = new ArrayList<Double>(); // Starting Y coordinates
    private static ArrayList<Double> x2 = new ArrayList<Double>(); // Ending X coordinates
    private static ArrayList<Double> y2 = new ArrayList<Double>(); // Ending Y coordinates

    private int currentMap; // Index of the currently loaded map
    private String[] mapNames = {"maps/default.svg","",""}; // File paths for available maps
    public static Line2D.Double[] mapData; // Array of Lines to represent the map
    private int[][] spawnPoints; // Spawn points for the player (not currently used)

    /**
     * Default constructor that loads the default map (index 0).
     *
     * @throws FileNotFoundException If the map file is not found.
     */
    public Map() throws FileNotFoundException {
       this(0);
    }

    /**
     * Constructor that loads a specific map based on the selected index.
     *
     * @param selectedMap // The index of the map loaded
     * @throws ArrayIndexOutOfBoundsException // If the selected map index is invalid.
     */
    public Map(int selectedMap) throws ArrayIndexOutOfBoundsException {
        getCoordinates(mapNames[selectedMap]); // Load coordinates from the map file
        loadMapData(); // Convert coordinates into Line2D.Double objects
    }

    /**
     * Converts the loaded coordinates into Line2D.Double objects and stores them in mapData.
     */
    public void loadMapData(){
        mapData = new Line2D.Double[x1.size()]; // Initialize the mapData array
        for(int i = 0; i<mapData.length; i++){
            // Create a Line2D.Double object for each line segment
            mapData[i] = new Line2D.Double(x1.get(i),y1.get(i), x2.get(i), y2.get(i));
        }
    }

    /**
     * Reads and SVG file and extracts line segment coordinates.
     *
     * @param fileName The path to the SVG file containing the map data.
     */
    public static void getCoordinates(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim(); // Read and trim each line
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

    /**
     * Extracts a numeric value from a string based on a specific attribute
     *
     * @param line The string containing the attribute and value.
     * @param attribute The attribute to search for (e.g., "x1=\"").
     * @return The extracted numeric value
     */
    private static double extractValue(String line, String attribute) {
        int startIndex = line.indexOf(attribute) + attribute.length(); // Start of the value
        int endIndex = line.indexOf("\"", startIndex); // End of the value
        return Double.parseDouble(line.substring(startIndex, endIndex)); // Parse and return the value
    }

    /**
     * Returns the map data as an array of Line2D.Double objects.
     *
     * @return The map data
     */
    public static Line2D.Double[] getMapData(){return mapData;}

    /**
     * Clears the current map data.
     */
    public void clearMap(){
        mapData = null;
    }

}
package src;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Map{
    private int width;
    private int height;
    private ArrayList<Double> x1 = new ArrayList<Double>();
    private ArrayList<Double> y1 = new ArrayList<Double>();
    private ArrayList<Double> x2 = new ArrayList<Double>();
    private ArrayList<Double> y2 = new ArrayList<Double>();
    private int lastPosition;
    private int currentMap;
    private String[] mapNames = {"maps/default.svg"};
    public static Line2D.Double[] mapData;
    private int[][] spawnPoints;

    public Map() throws FileNotFoundException {
       this(0);
    }
    public Map(int selectedMap) throws FileNotFoundException {
        getCoordinates(mapNames[selectedMap]);
        loadMapData();
    }

    public void loadMapData(){
        mapData = new Line2D.Double[x1.size()];
        for(int i = 0; i<mapData.length; i++){
            mapData[i] = new Line2D.Double(x1.get(i),y1.get(i), x2.get(i), y2.get(i));
        }
    }

    public void getCoordinates(String fileName) {
        File map = new File(fileName);
        if(!(map.length() > 0)) return;

        try(Scanner sc = new Scanner(map)){
            while(true){
                String line = sc.nextLine();
                if(line.contains("</svg>")) return;
                if(!(line.contains("line x1 ="))){
                    line = sc.nextLine();
                }


                x1.add(Double.parseDouble(
                        line.substring(
                                line.indexOf("x1=\"") + 4,
                                line.indexOf("\"", line.indexOf("x1=\"") + 4)
                                )
                        )
                );
                y1.add(Double.parseDouble(
                                line.substring(
                                        line.indexOf("y1=\"") + 4,
                                        line.indexOf("\"", line.indexOf("y1=\"") + 4)
                                )
                        )
                );
                x2.add(Double.parseDouble(
                                line.substring(
                                        line.indexOf("x2=\"") + 4,
                                        line.indexOf("\"", line.indexOf("x2=\"") + 4)
                                )
                        )
                );
                y2.add(Double.parseDouble(
                                line.substring(
                                        line.indexOf("y2=\"") + 4,
                                        line.indexOf("\"", line.indexOf("y2=\"") + 4)
                                )
                        )
                );
            }
        }catch(InputMismatchException | FileNotFoundException e){
            e.printStackTrace();
        }

    }

    public void clearMap(){
        mapData = null;
    }

}
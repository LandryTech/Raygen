package src;

import java.awt.geom.Dimension2D;
import java.util.ArrayList;

public class RayCasterEngine{
    private double fov = (4 * Math.PI) / 9; // 80 degrees
    private Dimension2D resolution;
    private double maxRenderDistance;

    public RayCasterEngine(){

    }

    public ArrayList<Ray> castRays(Player player, Map map){
        return null;
    }
    
    public void renderScene(ArrayList<Ray> rays){
    }

    public void updateSettings(Settings settings) {
    }

    public void adjustView(){
    }
}
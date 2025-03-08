package src;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;

public class Raygen extends JFrame{
    static boolean isRunning;
    int currentFrame;
    static RayCasterEngine engine;
    static CollisionManager collisionManager;
    static Player player;
    static UI ui;
    static Settings settings;
    static Map map;

    public Raygen(){
        super("Raygen");

        // set the content pane of the frame to the class.
        setContentPane(new RayCasterEngine());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        setVisible(true);

        //repaint(0, 0, width, height);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ui = new UI(selectedMap -> {
                System.out.println("Map " + selectedMap + " selected. Starting game...");
                startGame(selectedMap - 1);
            });
            ui.createAndShowGUI("Select a Map");
        });
    }

    public static void startGame(int selectedMap){
        isRunning = true;
        map = new Map(selectedMap);
        System.out.println("Game has started");
        new Raygen();
    }

    public static void render(){

    }

    public static void stopGame(){
        isRunning = false;
        System.exit(0);
    }
}
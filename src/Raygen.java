package src;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Raygen extends JFrame{
    static boolean isRunning;
    int currentFrame;
    RayCasterEngine engine;
    CollisionManager collisionManager;
    Player player;
    static UI ui;
    Settings settings;
    static Map map;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ui = new UI(selectedMap -> {
                System.out.println("Map " + selectedMap + " selected. Starting game...");
                startGame();
            });
            ui.createAndShowGUI("Select a Map");
        });
    }

    public static void startGame(){
        isRunning = true;
        System.out.println("Game has started");
    }

    public static void render(){

    }

    public static void stopGame(){
        isRunning = false;
    }
}
package src;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;

public class Raygen extends JFrame{
    static boolean isRunning;
    static CollisionManager collisionManager;
    static Player player;
    static UI ui;
    static Settings settings;
    static Map map;
    static RayCasterEngine rayCasterEngine;
    static InputHandler inputHandler;

    public Raygen(){
        super("Raygen");

        // Initialize player first
        player = new Player();

        // Set up input handler
        inputHandler = new InputHandler();

        // Initialize raycasting engine
        rayCasterEngine = new RayCasterEngine(player);
        rayCasterEngine.addKeyListener(inputHandler);

        // Set up window properties
        setContentPane(rayCasterEngine);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        rayCasterEngine.requestFocusInWindow();

        gameLoop();
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

    public void gameLoop(){
        new Thread(() -> {
            while(isRunning){
                inputHandler.handleInput(player);
                rayCasterEngine.repaint();
                try{
                    Thread.sleep(16);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void stopGame(){
        isRunning = false;
        System.exit(0);
    }
}
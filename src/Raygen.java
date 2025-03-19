package src;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The main class for the Raygen application. This class initializes the game window,
 * sets up the game loop, and manages the overall game state.
 * It extends JFrame to create the game window.
 */
public class Raygen extends JFrame{
    // Static variables to manage game state and components
    static boolean isRunning; // Tracks whether the game is running
    static CollisionManager collisionManager; // Manages collision detection
    static Player player; // Represents the player in the game
    static UI ui; // Handles the user interface for map selection
    static Settings settings; // Stores game settings
    static Map map; // Represents the current game map
    static RayCasterEngine rayCasterEngine; // Handles rendering and raycasting logic
    static InputHandler inputHandler; // Manages user input

    /**
     * Constructs the Raygen game window and initializes game components.
     * Sets up the player, input handler, raycasting engine, and game loop.
     */
    public Raygen(){
        super("Raygen"); // Set the window title

        // Initialize player first
        player = new Player();

        // Initialize the collision manager with the map data
        collisionManager = new CollisionManager(Map.getMapData());

        // Connect the collision manager to the player
        player.setCollisionManager(collisionManager);

        // Set up input handler
        inputHandler = new InputHandler();

        // Initialize raycasting engine with the player
        rayCasterEngine = new RayCasterEngine(player);
        rayCasterEngine.addKeyListener(inputHandler); // Registers key events through inputHandler

        // Set up window properties
        setContentPane(rayCasterEngine); // Set the raycasting engine as the content pane
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) screenSize.getWidth(), (int) screenSize.getHeight()); // Sets to full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on window close
        setVisible(true); // Makes window visible
        rayCasterEngine.requestFocusInWindow();

        // Starts the game loop
        gameLoop();
    }

    /**
     * Initializes the UI for map selection and starts the game when a map is selected.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize the UI with a callback for map selection
            ui = new UI(selectedMap -> {
                System.out.println("Map " + selectedMap + " selected. Starting game...");
                startGame(selectedMap - 1); // Start the game with selected map index
            });
            ui.createAndShowGUI("Select a Map"); // Display the map selection UI
        });
    }

    /**
     * Starts the game with the selected map.
     * Initializes the game map and creates the game window.
     *
     * @param selectedMap The index of the selected Map
     */
    public static void startGame(int selectedMap){
        isRunning = true;
        map = new Map(selectedMap); // Initialize the game map
        System.out.println("Game has started");
        new Raygen(); // Create the game window
    }

    /**
     * The game loop that continuously updates the game state and renders the game.
     */
    public void gameLoop(){
        new Thread(() -> {
            while(isRunning){
                inputHandler.handleInput(player); // Handle player input
                rayCasterEngine.repaint(); // Render the game
                try{
                    Thread.sleep(16); // Paces frames for ~60 FPS (16ms per frame)
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start(); // Start the game loop thread
    }

    /**
     * Stops the game and exits the application.
     */
    public static void stopGame(){
        isRunning = false;
        System.exit(0);
    }
}
package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The InputHandler class manages user input for the game.
 * It implements the KeyListener interface to handle keyboard events.
 */
public class InputHandler implements KeyListener {
    private boolean wKey, aKey, sKey, dKey; // Flags for movement keys (W, A, S, D)

    /**
     * Default constructor for the InputHandler class.
     */
    public InputHandler(){}

    /**
     * Handles the player input based on the current state of the key flags.
     * Calls the appropriate player movement or rotation methods.
     *
     * @param player The player object to control.
     */
    public void handleInput(Player player) {
        if (wKey) player.moveForward(); // Move the player forward
        if (sKey) player.moveBackward(); // Move the player backwards
        if(aKey) player.strafeLeft(); // Move the player left
        if(dKey) player.strafeRight(); // Move the player right
    }

    /**
     * Called when a key is pressed. Updates the corresponding key flag.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wKey = true; // W key pressed
            case KeyEvent.VK_A -> aKey = true; // A key pressed
            case KeyEvent.VK_S -> sKey = true; // S key pressed
            case KeyEvent.VK_D -> dKey = true; // D key pressed
            case KeyEvent.VK_ESCAPE -> Raygen.stopGame();
        }
    }

    /**
     * Called when a key is released. Updates the corresponding key flag.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wKey = false; // W key released
            case KeyEvent.VK_A -> aKey = false; // A key released
            case KeyEvent.VK_S -> sKey = false; // S key released
            case KeyEvent.VK_D -> dKey = false; // D key released
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
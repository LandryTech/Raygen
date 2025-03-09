package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
    private static final double TURN_ANGLE = 1.5;
    private boolean wKey, aKey, sKey, dKey;
    private boolean leftKey, rightKey;

    public InputHandler(){}

    public void handleInput(Player player) {
        if (wKey) {
            player.moveForward();
        }
        if (sKey) {
            player.moveBackward();
        }
        if (leftKey) {
            player.rotateLeft(TURN_ANGLE);
        }
        if (rightKey) {
            player.rotateRight(TURN_ANGLE);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wKey = true;
            case KeyEvent.VK_A -> aKey = true;
            case KeyEvent.VK_S -> sKey = true;
            case KeyEvent.VK_D -> dKey = true;
            case KeyEvent.VK_LEFT -> leftKey = true;
            case KeyEvent.VK_RIGHT -> rightKey = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wKey = false;
            case KeyEvent.VK_A -> aKey = false;
            case KeyEvent.VK_S -> sKey = false;
            case KeyEvent.VK_D -> dKey = false;
            case KeyEvent.VK_LEFT -> leftKey = false;
            case KeyEvent.VK_RIGHT -> rightKey = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
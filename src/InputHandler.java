package src;

import javax.swing.*;
import java.util.Set;

public class InputHandler{
    private Set<KeyStroke> keyPressed;
    private double mouseDeltaX;
    private double mouseDeltaY;

    public void handleInput(Player player){
        if(keyPressed.contains(KeyStroke.getKeyStroke("W"))){
            // move forward
        }
        if(keyPressed.contains(KeyStroke.getKeyStroke("A"))){
            // move left
        }
    }

    public void handleMenuNavigation(UI ui){
    }

    public void movement(Player player, Set<KeyStroke> key, double mouseDx, double mouseDy){
    }

    public Set<KeyStroke> getKeyPressed(){
        return keyPressed;
    }

    public void setKeyPressed(Set<KeyStroke> keyPressed){
        this.keyPressed = keyPressed;
    }

    public double getMouseDeltaX(){
        return mouseDeltaX;
    }

    public void setMouseDeltaX(double mouseDeltaX){
        this.mouseDeltaX = mouseDeltaX;
    }

    public double getMouseDeltaY(){
        return mouseDeltaY;
    }

    public void setMouseDeltaY(double mouseDeltaY){
        this.mouseDeltaY = mouseDeltaY;
    }
}
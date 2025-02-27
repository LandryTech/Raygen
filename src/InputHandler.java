package src;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Set;

public class InputHandler{
    private Set<KeyEvent> keyPressed;
    private double mouseDeltaX;
    private double mouseDeltaY;

    public InputHandler(){

    }

    public void handleInput(Player player){

    }

    public void handleMenuNavigation(UI ui){
    }

    public void movement(Player player, Set<KeyStroke> key, double mouseDx, double mouseDy){
    }

    public Set<KeyEvent> getKeyPressed(){
        return keyPressed;
    }

    public void setKeyPressed(Set<KeyEvent> keyPressed){
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
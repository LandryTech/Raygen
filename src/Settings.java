package src;

import java.awt.*;
import java.awt.geom.Dimension2D;

public abstract class Settings{
    private double mouseSensitivity;
    private double resolutionWidth;
    private double resolutionHeight;
    private double fov;

    // Default constructor
    public Settings() {
        this.loadDefault();
    }

    // Parameterized constructor
    public Settings(double mouseSensitivity, int resolutionWidth, int resolutionHeight, double fov) {
        this.mouseSensitivity = mouseSensitivity;
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        this.fov = fov;
    }

    public void loadDefault(){
        Dimension2D resolution = Toolkit.getDefaultToolkit().getScreenSize();
        this.resolutionWidth = resolution.getWidth();
        this.resolutionHeight = resolution.getHeight();
        this.mouseSensitivity = 1.0; // Default sensitivity
        this.fov = 90.0; // Default field of view
    }

    public void applySettings(double mouseSensitivity, int resolutionWidth, int resolutionHeight, double fov){
        this.mouseSensitivity = mouseSensitivity;
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        this.fov = fov;
    }

    public void saveToFile(String filename){
    }

    public void loadFromFile(String filename){
    }

    public void resetToDefault(){
        this.loadDefault();
    }
}
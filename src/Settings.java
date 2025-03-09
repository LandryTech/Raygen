package src;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.io.*;
import java.util.Properties;

public class Settings{
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.resolutionWidth = screenSize.getWidth();
        this.resolutionHeight = screenSize.getHeight();
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
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("mouseSensitivity=" + mouseSensitivity + "\n");
            writer.write("resolutionWidth=" + resolutionWidth + "\n");
            writer.write("resolutionHeight=" + resolutionHeight + "\n");
            writer.write("fov=" + fov + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Properties properties = new Properties();
            properties.load(reader);

            this.mouseSensitivity = Double.parseDouble(properties.getProperty("mouseSensitivity"));
            this.resolutionWidth = Double.parseDouble(properties.getProperty("resolutionWidth"));
            this.resolutionHeight = Double.parseDouble(properties.getProperty("resolutionHeight"));
            this.fov = Double.parseDouble(properties.getProperty("fov"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetToDefault(){
        this.loadDefault();
    }
}


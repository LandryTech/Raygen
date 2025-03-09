package src;

import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 * The Settings class manages game settings such as mouse sensitivity, resolution, and field of view (FOV).
 * It provides methods to load, save, and reset settings to default values.
 */
public class Settings{
    private double mouseSensitivity; // Sensitivity of mouse input
    private double resolutionWidth; // Width of the game resolution
    private double resolutionHeight; // Height of the game resolution
    private double fov; // Field of view in degrees

    /**
     * Default constructor that initializes settings with default values.
     */
    public Settings() {
        this.loadDefault();
    }

    /**
     * Constructor that initializes settings with specified values.
     *
     * @param mouseSensitivity The sensitivity of mouse input
     * @param resolutionWidth The width of the game resolution
     * @param resolutionHeight The height of the game resolution
     * @param fov The field of view in degrees
     */
    public Settings(double mouseSensitivity, int resolutionWidth, int resolutionHeight, double fov) {
        this.mouseSensitivity = mouseSensitivity;
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        this.fov = fov;
    }

    /**
     * Loads default settings, including screen resolution, mouse sensitivity, and FOV.
     */
    public void loadDefault(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.resolutionWidth = screenSize.getWidth();
        this.resolutionHeight = screenSize.getHeight();
        this.mouseSensitivity = 1.0; // Default sensitivity
        this.fov = 90.0; // Default field of view
    }

    /**
     * Applies new settings to the game.
     *
     * @param mouseSensitivity The new mouse sensitivity
     * @param resolutionWidth The new resolution width
     * @param resolutionHeight The new resolution height
     * @param fov The new field of view
     */
    public void applySettings(double mouseSensitivity, int resolutionWidth, int resolutionHeight, double fov){
        this.mouseSensitivity = mouseSensitivity;
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        this.fov = fov;
    }

    /**
     * Saves the current settings to a file.
     *
     * @param filename The name of the file to save the settings to.
     */
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

    /**
     * Loads settings from a file.
     *
     * @param filename The name of the file to load the settings from.
     */
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

    /**
     * Resets settings to their default values.
     */
    public void resetToDefault(){
        this.loadDefault();
    }
}


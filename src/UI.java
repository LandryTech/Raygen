package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.function.Consumer;

/**
 * The UI class is responsible for creating and managing the game's user interface,
 * including the map selection menu.
 */
public class UI extends JFrame{
    private int selectedMap; // The currently selected map index
    private Consumer<Integer> onMapSelected; // Callback to notify when a map is selected
    private JButton[] mapButtons = new JButton[3]; // Buttons for selecting maps
    private JFrame settingsFrame;
    private JSlider fovSlider;
    private JSlider renderDistanceSlider;
    private JSlider playerSpeedSlider;
    private JSlider colTolSlider;

    /**
     * Constructs a UI object with a callback for map selection.
     *
     * @param onMapSelected A callback function that is called when a map is selected.
     */
    public UI(Consumer<Integer> onMapSelected){
        this.onMapSelected = onMapSelected;
        this.selectedMap = -1;
    }

    /**
     * Creates and displays the map selection GUI.
     *
     * @param menuName The title of the menu window.
     */
    public void createAndShowGUI(String menuName) {
        Dimension2D GUISize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame(menuName); // Creates main window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the application on window close
        frame.setSize((int)GUISize.getWidth()/3, (int)GUISize.getHeight()/2); // Set menu size
        frame.setResizable(false); // Disable resizing
        frame.setLayout(new BorderLayout()); // Use BorderLayout for the main frame
        frame.setBackground(Color.BLACK);

        // Create a panel for map selection buttons
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(2, 2, 10, 10));

        // Create buttons for each map
        for (int i = 0; i < 3; i++) {
            int mapIndex = i + 1; // Map 1, 2, 3
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Map " + mapIndex));

            // Add a placeholder for the map image
            String imagePath = "resources/map" + mapIndex + ".png";
            ImageIcon mapImage = new ImageIcon(imagePath);
            JLabel imageLabel = new JLabel(mapImage, SwingConstants.CENTER);
            panel.add(imageLabel, BorderLayout.CENTER);

            // Create a button to select the map
            mapButtons[i] = new JButton("Select Map " + mapIndex);
            mapButtons[i].setBackground(Color.LIGHT_GRAY); // Set default button color
            mapButtons[i].addActionListener(e -> selectMap(mapIndex));

            panel.add(mapButtons[i], BorderLayout.SOUTH); // Add the button to the panel
            mapPanel.add(panel); // Add the panel to the map selection area
        }

        // Create a launch button to start the game
        JButton launchButton = new JButton("Launch");
        launchButton.addActionListener(e -> {
            if (selectedMap == -1) {
                // Show an error message if no map is selected
                JOptionPane.showMessageDialog(frame, "Please select a map before launching!");
            }else {
                // Notify the user and close the menu
                JOptionPane.showMessageDialog(frame, "Launching Map " + selectedMap + "...");
                frame.dispose(); // Close the menu window

                onMapSelected.accept(selectedMap); // Notify the callback with the selected map
            }
        });

        // Add components to the main frame
        frame.add(mapPanel, BorderLayout.CENTER);
        frame.add(launchButton, BorderLayout.SOUTH);

        // Center the window on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createSettingsMenu(RayCasterEngine rayCasterEngine, Player player, CollisionManager cm){
        Dimension2D cRes = Toolkit.getDefaultToolkit().getScreenSize(); // Current Resolution
        settingsFrame = new JFrame("Settings");
        settingsFrame.setSize((int)cRes.getWidth()/2,(int) cRes.getHeight()/4);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setLayout(new GridLayout(3,2));

        // FOV Slider
        fovSlider = new JSlider(0,360,(int) rayCasterEngine.getFov());
        fovSlider.setMajorTickSpacing(45); // Increment of FOV slider
        fovSlider.setPaintTicks(true);
        fovSlider.setPaintLabels(true);
        fovSlider.setSnapToTicks(true);
        settingsFrame.add(new JLabel("FOV (0-360):", SwingConstants.CENTER));
        settingsFrame.add(fovSlider);

        // Render Distance Slider
        renderDistanceSlider = new JSlider(0,250, rayCasterEngine.getMaxRenderDistance());
        renderDistanceSlider.setMajorTickSpacing(25); // Increment of render distance slider
        renderDistanceSlider.setPaintTicks(true);
        renderDistanceSlider.setPaintLabels(true);
        renderDistanceSlider.setSnapToTicks(true);
        settingsFrame.add(new JLabel("Render Distance (0-250):", SwingConstants.CENTER));
        settingsFrame.add(renderDistanceSlider);

        // Player Movement Speed Slider
        playerSpeedSlider = new JSlider(0,100, (int) player.getPlayerSpeed()+1);
        playerSpeedSlider.setMajorTickSpacing(20);
        playerSpeedSlider.setPaintTicks(true);
        playerSpeedSlider.setPaintLabels(true);
        settingsFrame.add(new JLabel("Player Movement Speed (0 - 100):", SwingConstants.CENTER));
        settingsFrame.add(playerSpeedSlider);

        // Collision Tolerance Slider
        colTolSlider = new JSlider(0,100, (int) cm.getCollisionTolerance() * 100);
        colTolSlider.setMajorTickSpacing(10);
        colTolSlider.setPaintTicks(true);
        colTolSlider.setPaintLabels(true);
        settingsFrame.add(new JLabel("Collision Tolerance (0.0 - 1.0):", SwingConstants.CENTER));
        settingsFrame.add(colTolSlider);

        //Apply Setting Button
        JButton applyButton = new JButton("Apply Settings");
        applyButton.addActionListener(e -> {
            rayCasterEngine.setFOV(fovSlider.getValue());
            rayCasterEngine.setMaxRenderDistance(renderDistanceSlider.getValue());
            player.setPlayerSpeed(playerSpeedSlider.getValue() * 3.0/20.0);
            double colTolValue = colTolSlider.getValue() /100.0;
            cm.setCollisionTolerance(colTolValue);
            settingsFrame.dispose();
            rayCasterEngine.toggleSettingsMenu();
        });

        // Close Setting Button
        JButton closeButton = new JButton("Close Settings (Don't Save)");
        closeButton.addActionListener(e -> {
            settingsFrame.dispose();
            rayCasterEngine.toggleSettingsMenu();
        });

        settingsFrame.add(applyButton);
        settingsFrame.add(closeButton);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
    }

    /**
     * Updates the selected map and highlights the corresponding button.
     *
     * @param mapIndex The index of the selected map.
     */
    private void selectMap(int mapIndex){
        selectedMap = mapIndex;
        for(JButton button : mapButtons){
            button.setBackground(Color.LIGHT_GRAY); // Reset all buttons to default color
        }
        mapButtons[mapIndex - 1].setBackground(Color.GREEN); // Highlight the selected button
    }
}
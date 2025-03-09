package src;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * The UI class is responsible for creating and managing the game's user interface,
 * including the map selection menu.
 */
public class UI extends JFrame{
    private int selectedMap; // The currently selected map index
    private Consumer<Integer> onMapSelected; // Callback to notify when a map is selected
    private JButton[] mapButtons = new JButton[3]; // Buttons for selecting maps

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
        JFrame frame = new JFrame(menuName); // Creates main window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application on window close
        frame.setSize(400, 300); // Set menu size
        frame.setResizable(false); // Disable resizing
        frame.setLayout(new BorderLayout()); // Use BorderLayout for the main frame

        // Create a panel for map selection buttons
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(1, 3, 10, 10));

        // Create buttons for each map
        for (int i = 0; i < 3; i++) {
            int mapIndex = i + 1; // Map 1, 2, 3
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Map " + mapIndex));

            // Add a placeholder for the map image
            String imagePath = "maps/map" + mapIndex + ".png";
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
            }else if(selectedMap == 2 || selectedMap == 3){
                JOptionPane.showMessageDialog(frame, "This map currently isn't available, Please select Map 1");
            } else {
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

    public void renderHUD(Player player){
        // TODO: Implement HUD rendering
    }

    public void updateSettings(Settings settings){
        // TODO: Implement settings update logic
    }

    public void displayMessage(String message){
        // TODO: Implement message display logic
    }
}
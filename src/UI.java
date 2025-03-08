package src;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class UI extends JFrame{
    private int selectedMap;
    private Consumer<Integer> onMapSelected;
    private JButton[] mapButtons = new JButton[3];

    public UI(Consumer<Integer> onMapSelected){
        this.onMapSelected = onMapSelected;
        this.selectedMap = -1;
    }

    public void createAndShowGUI(String menuName) {
        JFrame frame = new JFrame(menuName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(1, 3, 10, 10));

        for (int i = 0; i < 3; i++) {
            int mapIndex = i + 1; // Map 1, 2, 3
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Map " + mapIndex));

            JLabel imageLabel = new JLabel("[Image Here]", SwingConstants.CENTER);
            panel.add(imageLabel, BorderLayout.CENTER);

            // Create a larger button for selection
            mapButtons[i] = new JButton("Select Map " + mapIndex);
            mapButtons[i].setBackground(Color.LIGHT_GRAY);
            mapButtons[i].addActionListener(e -> selectMap(mapIndex));

            panel.add(mapButtons[i], BorderLayout.SOUTH);
            mapPanel.add(panel);
        }

        JButton launchButton = new JButton("Launch");
        launchButton.addActionListener(e -> {
            if (selectedMap == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a map before launching!");
            } else {
                JOptionPane.showMessageDialog(frame, "Launching Map " + selectedMap + "...");
                frame.dispose();
                onMapSelected.accept(selectedMap); // Notify Raygen
            }
        });

        frame.add(mapPanel, BorderLayout.CENTER);
        frame.add(launchButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void selectMap(int mapIndex){
        selectedMap = mapIndex;
        for(JButton button : mapButtons){
            button.setBackground(Color.LIGHT_GRAY);
        }
        mapButtons[mapIndex - 1].setBackground(Color.GREEN);
    }

    public void renderHUD(Player player){
    }

    public void updateSettings(Settings settings){
    }

    public void displayMessage(String message){
    }
}
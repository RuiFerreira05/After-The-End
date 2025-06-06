package tps.tp4.ui.swing;
import javax.swing.*;

import tps.tp4.Utils;

import java.awt.*;

public class UI_MainMenu extends UI_Menu {

    public UI_MainMenu(UI_Controller controller) {
        super(controller);
    }
    
    @Override
    public void BuildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        mainPanel.setBounds(0, 0, 800, 600);

        JLabel titleLabel = new JLabel("After the End");
        titleLabel.setFont(UI_Swing.TITLE_FONT);
        titleLabel.setForeground(UI_Swing.TITLE_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = Utils.createButton("Start Game", (e) -> {
            controller.showPanel("SETTINGS_MENU");
        });

        JButton settingsButton = Utils.createButton("Settings", (e) -> {
            controller.showPanel("SETTINGS_MENU");
        });

        JButton exitButton = Utils.createButton("Exit", (e) -> {
            System.exit(0);
        });

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(120));
        mainPanel.add(startButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(settingsButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(exitButton);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);
    }
}
package tps.tp4.ui.swing;

import java.awt.Component;

import javax.swing.*;

import tps.tp4.Colony;
import tps.tp4.Names;
import tps.tp4.Utils;
import tps.tp4.errors.PopulationLimitException;
import tps.tp4.settlers.Settler;

public class UI_NewGameMenu extends UI_Menu {

    public UI_NewGameMenu(UI_Controller controller) {
        super(controller);
    }

    @Override
    public void BuildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        mainPanel.setBounds(0, 0, 800, 600);

        JLabel titleLabel = new JLabel("And so, the journey begins...");
        titleLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(34f));
        titleLabel.setForeground(UI_Swing.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel colonyNameLabel = new JLabel("What is the name of your colony?");
        colonyNameLabel.setFont(UI_Swing.DEFAULT_FONT);
        colonyNameLabel.setForeground(UI_Swing.TEXT_COLOR);
        colonyNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField colonyNameInput = new JTextField(20);
        colonyNameInput.setFont(UI_Swing.DEFAULT_FONT);
        colonyNameInput.setForeground(UI_Swing.TEXT_COLOR);
        colonyNameInput.setBackground(UI_Swing.BACKGROUND_COLOR.brighter());
        colonyNameInput.setBorder(BorderFactory.createLineBorder(UI_Swing.TEXT_COLOR, 1));
        colonyNameInput.setMaximumSize(colonyNameInput.getPreferredSize());
        
        JLabel settlerNameLabel = new JLabel("And its first settler?");
        settlerNameLabel.setFont(UI_Swing.DEFAULT_FONT);
        settlerNameLabel.setForeground(UI_Swing.TEXT_COLOR);
        settlerNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField settlerNameInput = new JTextField(20);
        settlerNameInput.setFont(UI_Swing.DEFAULT_FONT);
        settlerNameInput.setForeground(UI_Swing.TEXT_COLOR);
        settlerNameInput.setBackground(UI_Swing.BACKGROUND_COLOR.brighter());
        settlerNameInput.setBorder(BorderFactory.createLineBorder(UI_Swing.TEXT_COLOR, 1));
        settlerNameInput.setMaximumSize(settlerNameInput.getPreferredSize());

        JButton randomButton = Utils.createButton("Random Name", (event) -> {
            String randomName = Names.pickRandom();
            settlerNameInput.setText(randomName);
        });
        randomButton.setFont(UI_Swing.DEFAULT_FONT.deriveFont(14f));
        randomButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = Utils.createButton("Back to saves", (event) -> {
            controller.showPanel("SAVES_MENU");
        });

        JButton continueButton = Utils.createButton("Continue", (event) -> {
            String colonyName = colonyNameInput.getText().trim();
            String settlerName = settlerNameInput.getText().trim();
            if (colonyName.isEmpty() || settlerName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both the colony name and the settler name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new game?", "Confirm New Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Colony colony = new Colony(colonyName);
                try {
                    colony.addSettler(new Settler(settlerName, colony));
                } catch (PopulationLimitException e) {
                    // Shouldn't happen
                }
                controller.getApp().colony = colony;
                controller.getApp().logger.info("New game started with colony: " + colonyName + " and settler: " + settlerName);
                controller.showPanel("PLAY_MENU"); //TODO
            }
        });

        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(continueButton);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(colonyNameLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(colonyNameInput);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(settlerNameLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(settlerNameInput);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(randomButton);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(bottomPanel);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        add(mainPanel);
    }
}

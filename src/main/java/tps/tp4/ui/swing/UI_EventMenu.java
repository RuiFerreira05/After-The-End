package tps.tp4.ui.swing;

import tps.tp4.Utils;
import tps.tp4.Events.Event;

import java.awt.*;

import javax.swing.*;

public class UI_EventMenu extends UI_Menu {

    Event event;
    JTextArea descriptionLabel;

    public UI_EventMenu(UI_Controller controller) {
        super(controller);
    }
    
    @Override
    public void BuildUI() {
        this.event = controller.getApp().currentEvent;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        mainPanel.setBounds(0, 0, 800, 600);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(event.getName());
        titleLabel.setFont(UI_Swing.TITLE_FONT);
        titleLabel.setForeground(UI_Swing.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        descriptionLabel = new JTextArea(event.getDescription());
        descriptionLabel.setFont(UI_Swing.DEFAULT_FONT);
        descriptionLabel.setForeground(UI_Swing.TEXT_COLOR);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setEditable(false);
        descriptionLabel.setOpaque(false);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < event.getOptions().length; i++) {
            int index = i;
            JButton optionButton = Utils.createButton(event.getOptions()[i], (e) -> {
                String consequence = event.impact(controller.getApp().colony, index);
                JOptionPane.showMessageDialog(null, consequence, "Consequences", JOptionPane.INFORMATION_MESSAGE);
                controller.registerPanel(new UI_PlayMenu(controller), "PLAY_MENU");
                controller.showPanel("PLAY_MENU");
            });
            optionButton.setFont(UI_Swing.DEFAULT_FONT);
            optionsPanel.add(optionButton);
            optionsPanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        }

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(descriptionLabel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(optionsPanel);
    
        add(mainPanel);
    }
}

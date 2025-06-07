package tps.tp4.ui.swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import tps.tp4.Settings;

import java.awt.*;

public class UI_SettingsMenu extends UI_Menu {

    public UI_SettingsMenu(UI_Controller controller) {
        super(controller);
    }

    @Override
    public void BuildUI() {
        JPanel settingsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        settingsPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        settingsPanel.setBounds(0, 0, 800, 600);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        TitledBorder leftBorder = BorderFactory.createTitledBorder(border, "Options");
        leftBorder.setTitleFont(UI_Swing.DEFAULT_FONT);
        leftBorder.setTitleColor(UI_Swing.TITLE_COLOR);
        optionsPanel.setBorder(leftBorder);
        settingsPanel.add(optionsPanel);

        JTextPane settingsTextPane = new JTextPane();
        settingsTextPane.setEditable(false);
        settingsTextPane.setText("Settings will be displayed here.");
        settingsTextPane.setFont(UI_Swing.DEFAULT_FONT);
        settingsTextPane.setBackground(UI_Swing.BACKGROUND_COLOR);
        settingsTextPane.setForeground(UI_Swing.TITLE_COLOR);
        settingsTextPane.setText(Settings.outputString());
        settingsTextPane.setCaretPosition(0); // Scroll to the top
        

        JScrollPane scrollPane = new JScrollPane(settingsTextPane);
        scrollPane.setBackground(UI_Swing.BACKGROUND_COLOR);
        TitledBorder rightBorder = BorderFactory.createTitledBorder(border, "Settings");
        rightBorder.setTitleFont(UI_Swing.DEFAULT_FONT);
        rightBorder.setTitleColor(UI_Swing.TITLE_COLOR);
        scrollPane.setBorder(rightBorder);
        settingsPanel.add(scrollPane);

        add(settingsPanel);
    }
}

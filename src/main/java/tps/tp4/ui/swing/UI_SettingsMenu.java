package tps.tp4.ui.swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import tps.tp4.Settings;
import tps.tp4.Utils;
import tps.tp4.XMLParser;

import java.awt.*;
import java.io.File;

public class UI_SettingsMenu extends UI_Menu {

    private JTextPane settingsTextPane;

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
        leftBorder.setTitleColor(UI_Swing.TEXT_COLOR);
        optionsPanel.setBorder(leftBorder);

        JButton LoadButton = Utils.createButton("Load Settings from xml file", (event) -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setDialogTitle("Select Settings to load");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(java.io.File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
                }

                @Override
                public String getDescription() {
                    return "XML Files (*.xml)";
                }
            });
            fileChooser.setAcceptAllFileFilterUsed(false);

            File selectedFile = null;
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null && selectedFile.exists()) {
                    try {
                        XMLParser.loadXMLSettings(selectedFile);
                        settingsTextPane.setText(Settings.outputString());
                        settingsTextPane.setCaretPosition(0);
                        JOptionPane.showMessageDialog(null, "Settings loaded successfully from: " + selectedFile.getName(), "Load Settings", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error loading settings from file: " + selectedFile.getName(), "Load Settings", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No file selected or file does not exist.", "Load Settings", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton SaveButton = Utils.createButton("Save Settings to xml file", (event) -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setDialogTitle("Save Settings to XML File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            File selectedFile = null;
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null) {
                    if (!selectedFile.getName().toLowerCase().endsWith(".xml")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".xml");
                    }
                    try {
                        XMLParser.writeXMLSettings(selectedFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Settings saved successfully to: " + selectedFile.getName(), "Save Settings", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error saving settings to file: " + selectedFile.getName(), "Save Settings", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton ResetButton = Utils.createButton("Reset Settings to default", (e) -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset settings to default?", "Reset Settings", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    XMLParser.loadXMLSettings(new File(Settings.DEFAULT_SETTINGS_FILE));
                    settingsTextPane.setText(Settings.outputString());
                    settingsTextPane.setCaretPosition(0);
                    JOptionPane.showMessageDialog(null, "Settings have been reset to default.", "Reset Settings", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error resetting settings: " + ex.getMessage(), "Reset Settings", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = Utils.createButton("Back to Main Menu", (e) -> {
            controller.showPanel("MAIN_MENU");
        });

        // optionsPanel.add(Box.createVerticalGlue());
        optionsPanel.add(Box.createVerticalStrut(50));
        optionsPanel.add(LoadButton);
        optionsPanel.add(Box.createVerticalStrut(20));
        optionsPanel.add(SaveButton);
        optionsPanel.add(Box.createVerticalStrut(20));
        optionsPanel.add(ResetButton);
        optionsPanel.add(Box.createVerticalStrut(20));
        optionsPanel.add(backButton);
        optionsPanel.add(Box.createVerticalGlue());

        settingsPanel.add(optionsPanel);


        settingsTextPane = new JTextPane();
        settingsTextPane.setEditable(false);
        settingsTextPane.setText("Settings will be displayed here.");
        settingsTextPane.setFont(UI_Swing.DEFAULT_FONT.deriveFont(16f));
        settingsTextPane.setBackground(UI_Swing.BACKGROUND_COLOR);
        settingsTextPane.setForeground(UI_Swing.TEXT_COLOR);
        settingsTextPane.setText(Settings.outputString());
        settingsTextPane.setCaretPosition(0);
        
        JScrollPane scrollPane = new JScrollPane(settingsTextPane);
        scrollPane.setBackground(UI_Swing.BACKGROUND_COLOR);
        TitledBorder rightBorder = BorderFactory.createTitledBorder(border, "Settings");
        rightBorder.setTitleFont(UI_Swing.DEFAULT_FONT);
        rightBorder.setTitleColor(UI_Swing.TEXT_COLOR);
        scrollPane.setBorder(rightBorder);
        settingsPanel.add(scrollPane);

        add(settingsPanel);
    }
}

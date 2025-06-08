package tps.tp4.ui.swing;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import tps.tp4.App;
import tps.tp4.Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.List;

public class UI_SavesMenu extends UI_Menu {

    private List<File> saveFiles;

    public UI_SavesMenu(UI_Controller controller) {
        super(controller);
    }

    @Override
    public void BuildUI() {
        saveFiles = controller.getApp().saveFiles;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        mainPanel.setBounds(0, 0, 800, 600);

        JPanel savesPanel = new JPanel(new GridLayout(App.MAX_SAVES, 1, 10, 10));
        savesPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        
        // saves
        for (File saveFile : saveFiles) {
            JButton playSaveButton = new JButton();
            playSaveButton.setBackground(UI_Swing.BACKGROUND_COLOR);
            playSaveButton.addActionListener((event) -> {
                try {
                    controller.getApp().parseColony(saveFile);
                    JOptionPane.showMessageDialog(null, "Save loaded successfully: " + saveFile.getName(), "Load Successful", JOptionPane.INFORMATION_MESSAGE);
                    controller.showPanel("MAIN_MENU"); //TODO
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Failed to load save: " + e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
                    controller.getApp().logger.error("Failed to load save: " + e.getMessage(), e);
                }
            });
            playSaveButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    playSaveButton.setBackground(UI_Swing.BACKGROUND_COLOR.darker());
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    playSaveButton.setBackground(UI_Swing.BACKGROUND_COLOR);
                }
            });
            playSaveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            JPanel savePanel = new JPanel();
            savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.X_AXIS));
            savePanel.setOpaque(false);

            JLabel saveLabel = new JLabel(saveFile.getName().replace(".save", ""));
            saveLabel.setForeground(UI_Swing.TEXT_COLOR);
            saveLabel.setFont(UI_Swing.DEFAULT_FONT);

            JButton deleteButton = Utils.createButton("Delete", (e) -> {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this save?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    controller.getApp().deleteSaveFile(saveFile);
                    updateSaveFiles();
                }
            });

            JButton exportButton = Utils.createButton("Export", (event) -> {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setDialogTitle("Export Save");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setSelectedFile(saveFile);
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null && selectedFile.exists()) {
                        int response = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.NO_OPTION) {
                            return;
                        }
                    }
                    try {
                        controller.getApp().parseColony(saveFile);
                        controller.getApp().exportColonyToXML(selectedFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Save exported successfully to " + selectedFile.getAbsolutePath(), "Export Successful", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Failed to export save: " + e.getMessage(), "Export Failed", JOptionPane.ERROR_MESSAGE);
                        controller.getApp().logger.error("Failed to export save: " + e.getMessage(), e);
                    }
                }
            });

            savePanel.add(Box.createHorizontalStrut(20));
            savePanel.add(saveLabel);
            savePanel.add(Box.createHorizontalGlue());
            savePanel.add(exportButton);
            savePanel.add(Box.createHorizontalStrut(10));
            savePanel.add(deleteButton);
            savePanel.add(Box.createHorizontalStrut(20));

            playSaveButton.add(savePanel);

            savesPanel.add(playSaveButton);
        }

        // Empty saves
        for (int i = saveFiles.size(); i < App.MAX_SAVES; i++) {
            JButton playSaveButton = new JButton();
            playSaveButton.setBackground(UI_Swing.BACKGROUND_COLOR);
            playSaveButton.addActionListener((event) -> {
                controller.showPanel("NEW_GAME_MENU");
            });
            playSaveButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    playSaveButton.setBackground(UI_Swing.BACKGROUND_COLOR.darker());
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    playSaveButton.setBackground(UI_Swing.BACKGROUND_COLOR);
                }
            });
            playSaveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            JPanel savePanel = new JPanel();
            savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.X_AXIS));
            savePanel.setOpaque(false);

            JLabel saveLabel = new JLabel("Empty Save");
            saveLabel.setForeground(UI_Swing.TEXT_COLOR);
            saveLabel.setFont(UI_Swing.DEFAULT_FONT);

            JButton importButton = Utils.createButton("Import", (event) -> {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setDialogTitle("Import Save");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
                    }

                    @Override
                    public String getDescription() {
                        return "XML Files (*.xml)";
                    }
                });
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        try {
                            controller.getApp().loadColonyFromXML(selectedFile);
                            controller.getApp().saveGame();
                            controller.getApp().saveFiles.clear();
                            controller.getApp().loadSaves();
                            updateSaveFiles();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Failed to import save: " + e.getMessage(), "Import Error", JOptionPane.ERROR_MESSAGE);
                            controller.getApp().logger.error("Failed to import save: " + e.getMessage(), e);
                        }
                    }
                }
            });

            savePanel.add(Box.createHorizontalStrut(20));
            savePanel.add(saveLabel);
            savePanel.add(Box.createHorizontalGlue());
            savePanel.add(importButton);
            savePanel.add(Box.createHorizontalStrut(20));

            playSaveButton.add(savePanel);
            savesPanel.add(playSaveButton);
        }

        JScrollPane scrollPane = new JScrollPane(savesPanel);
        scrollPane.setBackground(UI_Swing.BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton backButton = Utils.createButton("Back to menu", (event) -> {
            controller.showPanel("MAIN_MENU");
        });
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(backButton);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));

        add(mainPanel);
    }

    private void updateSaveFiles() {
        saveFiles = controller.getApp().saveFiles;
        removeAll();
        BuildUI();
        revalidate();
        repaint();
    }
}

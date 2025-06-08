package tps.tp4.ui.swing;

import javax.swing.*;
import java.awt.*;

import tps.tp4.Colony;
import tps.tp4.Utils;
import tps.tp4.Events.Event;
import tps.tp4.errors.NotEnoughResourcesException;
import tps.tp4.settlers.Settler;
import tps.tp4.structures.Structure;
import tps.tp4.structures.StructureFactory;
import tps.tp4.structures.StructureTypes;


public class UI_PlayMenu extends UI_Menu {

    private Colony colony;

    public UI_PlayMenu(UI_Controller controller) {
        super(controller);
    }
    
    @Override
    public void BuildUI() {
        this.colony = controller.getApp().colony;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        mainPanel.setBounds(0, 0, 800, 600);
        
        //UL Panel
        JPanel ulPanel = new JPanel();
        ulPanel.setLayout(new BoxLayout(ulPanel, BoxLayout.Y_AXIS));
        ulPanel.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        ulPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel innerUlPanel = new JPanel();
        innerUlPanel.setLayout(new BoxLayout(innerUlPanel, BoxLayout.Y_AXIS));
        innerUlPanel.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        innerUlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel colonyName = new JLabel(colony.getColonyName());
        colonyName.setFont(UI_Swing.DEFAULT_FONT.deriveFont(32f));
        colonyName.setForeground(UI_Swing.TEXT_COLOR);
        colonyName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dayLabel = new JLabel("Day " + colony.getCurrDay());
        dayLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(24f));
        dayLabel.setForeground(UI_Swing.TEXT_COLOR);
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        innerUlPanel.add(colonyName);
        innerUlPanel.add(Box.createVerticalStrut(10));
        JSeparator ulseparator = new JSeparator(SwingConstants.HORIZONTAL);
        ulseparator.setForeground(UI_Swing.TEXT_COLOR);
        innerUlPanel.add(ulseparator);
        innerUlPanel.add(Box.createVerticalStrut(10));
        innerUlPanel.add(dayLabel);

        ulPanel.add(Box.createVerticalGlue());
        ulPanel.add(innerUlPanel);
        ulPanel.add(Box.createVerticalGlue());

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton nextDayButton = Utils.createButton("Next Day", (e) -> {
            Event event = controller.getApp().nextDay();
            if (event != null) {
                controller.registerPanel(new UI_EventMenu(controller), "EVENT_MENU");
                controller.showPanel("EVENT_MENU");
            }
            updateMenu();
        });

        JButton saveButton = Utils.createButton("Save Game", (event) -> {
            try {
                controller.getApp().saveGame();
                JOptionPane.showMessageDialog(null, "Game saved successfully!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failed to save game: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
                controller.getApp().logger.error("Failed to save game: " + e.getMessage(), e);
            }
        });

        JButton backButton = Utils.createButton("Back to Saves", (event) -> {
            controller.showPanel("SAVES_MENU");
        });

        JButton exitButton = Utils.createButton("Exit Game", (event) -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonsPanel.add(nextDayButton);
        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(saveButton);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(exitButton);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(UI_Swing.BACKGROUND_COLOR);

        leftPanel.add(ulPanel);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(buttonsPanel);
        leftPanel.add(Box.createVerticalGlue());

        // MAT Panel
        JPanel matPanel = new JPanel();
        matPanel.setLayout(new BoxLayout(matPanel, BoxLayout.X_AXIS));
        matPanel.setBackground(UI_Swing.BACKGROUND_3_COLOR);
        matPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel woodPanel = new JPanel();
        woodPanel.setLayout(new BoxLayout(woodPanel, BoxLayout.Y_AXIS));
        woodPanel.setOpaque(false);

        JLabel woodLabel = new JLabel("Wood: " + colony.getWood());
        woodLabel.setFont(UI_Swing.DEFAULT_FONT);
        woodLabel.setForeground(UI_Swing.TEXT_COLOR);
        woodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel woodProdLabel = new JLabel("("+ (colony.getWoodProduction() >= 0? "+" : "") + colony.getWoodProduction() + ")");
        woodProdLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(14f));
        woodProdLabel.setForeground(colony.getWoodProduction() >= 0 ? UI_Swing.TEXT_COLOR : UI_Swing.RED_COLOR);
        woodProdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        woodPanel.add(woodLabel);
        woodPanel.add(woodProdLabel);

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        foodPanel.setOpaque(false);

        JLabel foodLabel = new JLabel("Food: " + colony.getFood());
        foodLabel.setFont(UI_Swing.DEFAULT_FONT);
        foodLabel.setForeground(UI_Swing.TEXT_COLOR);
        foodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel foodProdLabel = new JLabel("("+ (colony.getFoodProduction() >= 0? "+" : "") + colony.getFoodProduction() + ")");
        foodProdLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(14f));
        foodProdLabel.setForeground(colony.getFoodProduction() >= 0 ? UI_Swing.TEXT_COLOR : UI_Swing.RED_COLOR);
        foodProdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        foodPanel.add(foodLabel);
        foodPanel.add(foodProdLabel);

        JPanel stonePanel = new JPanel();
        stonePanel.setLayout(new BoxLayout(stonePanel, BoxLayout.Y_AXIS));
        stonePanel.setOpaque(false);

        JLabel stoneLabel = new JLabel("Stone: " + colony.getStone());
        stoneLabel.setFont(UI_Swing.DEFAULT_FONT);
        stoneLabel.setForeground(UI_Swing.TEXT_COLOR);
        stoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stoneProdLabel = new JLabel("("+ (colony.getStoneProduction() >= 0? "+" : "") + colony.getStoneProduction() + ")");
        stoneProdLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(14f));
        stoneProdLabel.setForeground(colony.getStoneProduction() >= 0 ? UI_Swing.TEXT_COLOR : UI_Swing.RED_COLOR);
        stoneProdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        stonePanel.add(stoneLabel);
        stonePanel.add(stoneProdLabel);

        JPanel metalPanel = new JPanel();
        metalPanel.setLayout(new BoxLayout(metalPanel, BoxLayout.Y_AXIS));
        metalPanel.setOpaque(false);

        JLabel metalLabel = new JLabel("Metal: " + colony.getMetal());
        metalLabel.setFont(UI_Swing.DEFAULT_FONT);
        metalLabel.setForeground(UI_Swing.TEXT_COLOR);
        metalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel metalProdLabel = new JLabel("("+ (colony.getMetalProduction() >= 0? "+" : "") + colony.getMetalProduction() + ")");
        metalProdLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(14f));
        metalProdLabel.setForeground(colony.getMetalProduction() >= 0 ? UI_Swing.TEXT_COLOR : UI_Swing.RED_COLOR);
        metalProdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        metalPanel.add(metalLabel);
        metalPanel.add(metalProdLabel);

        matPanel.add(Box.createHorizontalGlue());
        matPanel.add(woodPanel);
        matPanel.add(Box.createHorizontalGlue());
        matPanel.add(new JSeparator(SwingConstants.VERTICAL));
        matPanel.add(Box.createHorizontalGlue());
        matPanel.add(foodPanel);
        matPanel.add(Box.createHorizontalGlue());
        matPanel.add(new JSeparator(SwingConstants.VERTICAL));
        matPanel.add(Box.createHorizontalGlue());
        matPanel.add(stonePanel);
        matPanel.add(Box.createHorizontalGlue());
        matPanel.add(new JSeparator(SwingConstants.VERTICAL));
        matPanel.add(Box.createHorizontalGlue());
        matPanel.add(metalPanel);
        matPanel.add(Box.createHorizontalGlue());
        
        // Settlers Panel
        JPanel settlersPanel = new JPanel();
        settlersPanel.setLayout(new BoxLayout(settlersPanel, BoxLayout.X_AXIS));
        settlersPanel.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        settlersPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        settlersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Settlers Text Area
        JTextArea settlersTextArea = new JTextArea();
        settlersTextArea.setEditable(false);
        settlersTextArea.setFont(UI_Swing.DEFAULT_FONT);
        settlersTextArea.setForeground(UI_Swing.TEXT_COLOR);
        settlersTextArea.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        settlersTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (Settler settler : colony.getSettlers()) {
            settlersTextArea.append(settler.getName() + " - " + settler.getHappiness() + "%\n");
        }
        settlersTextArea.setCaretPosition(0);
        
        JScrollPane settlersScrollPane = new JScrollPane(settlersTextArea);
        settlersScrollPane.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        settlersScrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Settler Overall Panel
        JPanel settlerOverallPanel = new JPanel();
        settlerOverallPanel.setLayout(new BoxLayout(settlerOverallPanel, BoxLayout.Y_AXIS));
        settlerOverallPanel.setOpaque(false);
        settlerOverallPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        settlerOverallPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        settlerOverallPanel.setMaximumSize(new Dimension(100, 50));
        
        JLabel happinessLabel = new JLabel(colony.getOverAllHappiness() + "%");
        happinessLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(16f));
        happinessLabel.setForeground(UI_Swing.TEXT_COLOR);
        happinessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel populationLabel = new JLabel(colony.getPopulation() + "/" + colony.getMaxPopulation());
        populationLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(16f));
        populationLabel.setForeground(UI_Swing.TEXT_COLOR);
        populationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        settlerOverallPanel.add(Box.createVerticalGlue());
        settlerOverallPanel.add(happinessLabel);
        settlerOverallPanel.add(Box.createVerticalGlue());
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(UI_Swing.TEXT_COLOR);
        separator.setMaximumSize(new Dimension(60, 1));
        settlerOverallPanel.add(separator);
        settlerOverallPanel.add(Box.createVerticalGlue());
        settlerOverallPanel.add(populationLabel);
        settlerOverallPanel.add(Box.createVerticalGlue());
        
        settlersPanel.add(settlersScrollPane);
        settlersPanel.add(settlerOverallPanel);

        // StructurePanel
        JPanel structurePanel = new JPanel();
        structurePanel.setLayout(new BoxLayout(structurePanel, BoxLayout.X_AXIS));
        structurePanel.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        structurePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        structurePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        structurePanel.setMinimumSize(new Dimension(0, 200));

        JTextArea leftStructureTextArea = new JTextArea();
        leftStructureTextArea.setEditable(false);
        leftStructureTextArea.setFont(UI_Swing.DEFAULT_FONT);
        leftStructureTextArea.setForeground(UI_Swing.TEXT_COLOR);
        leftStructureTextArea.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        leftStructureTextArea.setPreferredSize(new Dimension(100, 300));
        leftStructureTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (Structure structure : colony.getStructures()) {
            leftStructureTextArea.append(structure.getName() + "\n");
        }
        leftStructureTextArea.setCaretPosition(0);

        JScrollPane leftScrollPane = new JScrollPane(leftStructureTextArea);
        leftScrollPane.setOpaque(false);

        JPanel rightStructurePanel = new JPanel(new GridLayout(StructureTypes.values().length, 1));
        rightStructurePanel.setBackground(UI_Swing.BACKGROUND_2_COLOR);
        
        for (String name : StructureTypes.listNames()) {
            JPanel structureTypePanel = new JPanel();
            structureTypePanel.setLayout(new BoxLayout(structureTypePanel, BoxLayout.X_AXIS));
            structureTypePanel.setOpaque(false);

            JLabel structureTypeLabel = new JLabel(name);
            structureTypeLabel.setFont(UI_Swing.DEFAULT_FONT.deriveFont(18f));
            structureTypeLabel.setForeground(UI_Swing.TEXT_COLOR);
            structureTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton qBuildButton = Utils.createButton("Q.Build", (event) -> {
                try {
                    StructureFactory.createStructure(StructureTypes.valueOf(name));
                    colony.addStructure(StructureFactory.createStructure(StructureTypes.valueOf(name)));
                    updateMenu();
                } catch (NotEnoughResourcesException e) {
                    JOptionPane.showMessageDialog(null, "Not enough resources to build that", "Build Error", JOptionPane.ERROR_MESSAGE);
                    controller.getApp().logger.error("Failed to build structure: " + e.getMessage(), e);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Failed to build structure", "Build Error", JOptionPane.ERROR_MESSAGE);
                    controller.getApp().logger.error("Failed to build structure: " + e.getMessage(), e);
                }
            });
            qBuildButton.setFont(UI_Swing.DEFAULT_FONT.deriveFont(14f));

            JButton infoButton = Utils.createButton("Info", (event) -> {
                StringBuilder info = new StringBuilder();
                Structure structure = StructureFactory.createStructure(StructureTypes.valueOf(name));
                info.append("Name: ").append(structure.getName()).append("\n");
                info.append("Description: ").append(structure.getDescription()).append("\n");
                info.append("Cost: ").append(
                    structure.getCost()[0] + " wood" +
                    ", " + structure.getCost()[1] + " stone" +
                    ", " + structure.getCost()[2] + " metal\n"
                ).append("\n");
                JOptionPane.showMessageDialog(null, info.toString(), "Structure Info", JOptionPane.INFORMATION_MESSAGE);
            });
            infoButton.setFont(UI_Swing.DEFAULT_FONT.deriveFont(14f));

            structureTypePanel.add(structureTypeLabel);
            structureTypePanel.add(Box.createHorizontalGlue());
            structureTypePanel.add(qBuildButton);
            structureTypePanel.add(Box.createHorizontalStrut(10));
            structureTypePanel.add(infoButton);

            rightStructurePanel.add(structureTypePanel);
        }

        // Right Scroll Pane
        JScrollPane rightScrollPane = new JScrollPane(rightStructurePanel);
        rightScrollPane.setOpaque(false);
        rightScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        structurePanel.add(leftScrollPane);
        structurePanel.add(Box.createHorizontalGlue());
        JSeparator verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
        verticalSeparator.setForeground(UI_Swing.TEXT_COLOR);
        structurePanel.add(verticalSeparator);
        structurePanel.add(Box.createHorizontalGlue());
        structurePanel.add(rightScrollPane);

        // lrPanel
        JPanel lrPanel = new JPanel();
        lrPanel.setLayout(new BoxLayout(lrPanel, BoxLayout.Y_AXIS));
        lrPanel.setBackground(UI_Swing.BACKGROUND_COLOR);
        lrPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lrPanel.add(settlersPanel);
        lrPanel.add(Box.createVerticalStrut(20));
        lrPanel.add(structurePanel);
        
        // right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(UI_Swing.BACKGROUND_COLOR);

        rightPanel.add(matPanel);
        rightPanel.add(lrPanel);

        // And now all together
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
    }

    public void updateMenu() {
        removeAll();
        BuildUI();
        revalidate();
        repaint();
    }
    
}

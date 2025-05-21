package tps.tp4.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import tps.tp4.structures.Structure;
import tps.tp4.structures.StructureFactory;
import tps.tp4.structures.StructureTypes;
import tps.tp4.ui.UI_Console;
import tps.tp4.App;
import tps.tp4.Colony;
import tps.tp4.Utils;
import tps.tp4.errors.*;
import tps.tp4.settlers.Settler;

public class UI_Console implements UI {

    private static final int MAIN_MENU = 1;
    private static final int GAME_MENU = 2;
    private static final int SETTLER_MENU = 3;
    private static final int STRUCTURE_MENU = 4;
    private static final int EVENT_MENU = 5;
    private static final int BUILD_STRUCTURE_MENU = 6;
    private static final int EXIT = 7;
    private static final int PLAY_MENU = 8;
    private static final int NEW_GAME_MENU = 9;
    private static final int NEXT_DAY_MENU = 10;
    
    private static final int INITIAL_STATE = MAIN_MENU;
    
    private Scanner scanner = new Scanner(System.in);
    
    private int state;

    private App app;

    public UI_Console(App app) {
        this.app = app;
        this.scanner = new Scanner(System.in);
        this.state = INITIAL_STATE;
    }

    public void start() {
        app.loadSaves();
        while (true) {
            switch (state) {
    
                case MAIN_MENU:
                    mainMenu();
                    break;

                case PLAY_MENU:
                    playMenu();
                    break;

                case NEW_GAME_MENU:
                    newGameMenu();
                    break;

                case GAME_MENU:
                    gameMenu();
                    break;

                case NEXT_DAY_MENU:
                    nextDay();
                    break;

                case SETTLER_MENU:
                    settlerMenu();
                    break;

                case STRUCTURE_MENU:
                    structureMenu();
                    break;

                case BUILD_STRUCTURE_MENU:
                    buildStructureMenu();
                    break;

                case EVENT_MENU:
                    // eventMenu();
                    // TODO
                    break;

                case EXIT:
                    System.exit(0);
                    break;

                default:
                    app.logger.fatal("Invalid app state: " + state, new InvalidAppStateException(state));
                    System.exit(1);
                    break;
            }
        }
    }

    private void nextDay() {
        // TODO: For now, just skip the day
        app.nextDay();
    }
    
    private void structureMenu() {
        Utils.printTitle("Structures");
        String[] options = {
            "List structures",
            "Build a new structure",
            "Back to main menu"
        };
        int choice = Utils.choiceList(options, scanner);
        switch (choice) {
            case 1:
                listStructures();
                break;

            case 2:
                state = BUILD_STRUCTURE_MENU;
                break;
            
            case 3:
                state = GAME_MENU;
                break;
        }
    }

    private void buildStructureMenu() {
        Utils.printTitle("Build a new structure");
        ArrayList<String> options = StructureTypes.listNames(); // This snippet just returns all types of structures in the enum and converts it to a String array.
        options.add("Back to structures menu");
        int choice = Utils.choiceList(options.toArray(new String[0]), scanner);
        if (choice == options.size()) {
            state = STRUCTURE_MENU;
            return;
        }
        StructureTypes type = StructureTypes.listTypes().get(choice - 1);
        Structure structure = StructureFactory.createStructure(type);
        Utils.printTitle(structure.getName() + " - Cost: " + structure.getCost()[0] + " wood | " + structure.getCost()[1] + " stone | " + structure.getCost()[2] + " metal");
        System.out.println(structure.getStructureInfo(false));
        System.out.println("\nDo you want to build this structure? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            try {
                app.addStructureToColony(structure);
                Utils.printTitle(structure.getName() + " built!");
                System.out.println("Press enter to continue...");
                scanner.nextLine();
            } catch (NotEnoughResourcesException e) {
                Utils.printTitle("Not enough resources to build " + structure.getName());
                System.out.println("Press enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void settlerMenu() {
        Utils.printTitle("Settlers");
        String[] options = {
            "List settlers",
            "Back to main menu"
        };
        int choice = Utils.choiceList(options, scanner);
        switch (choice) {
            case 1:
                listSettlers();
                break;
            
            case 2:
                state = GAME_MENU;
                break;
        }
    }

    private void mainMenu() {
        Utils.printTitle("Welcome to After The End!");
        String[] options = {
            "Play",
            "Exit"
        };
        int choice = Utils.choiceList(options, scanner);
        switch (choice) {
            case 1:
                state = PLAY_MENU;
                break;
            
            case 2:
                System.exit(0);
                break;
        }
    }

    private void playMenu() {
        Utils.printTitle("Before The Fall...");
        String[] options = new String[App.MAX_SAVES + 3];
        for (int i = 0; i < app.saveFiles.size(); i++) {
            options[i] = app.saveFiles.get(i).getName().replace(".save", "");
        }
        for (int i = app.saveFiles.size(); i < App.MAX_SAVES; i++) {
            options[i] = "Empty save slot";
        }
        options[App.MAX_SAVES] = "delete save file";
        options[App.MAX_SAVES + 1] = "load XML file";
        options[App.MAX_SAVES + 2] = "Back to main menu";
        int choice = Utils.choiceList(options, scanner, App.MAX_SAVES);
        switch (choice) {
            case App.MAX_SAVES + 1: // Delete save file
                if (app.saveFiles.isEmpty()) {
                    Utils.printTitle("No save files to delete.");
                    break;
                }
                System.out.println("Enter the number of the save file to delete: ");
                int saveIndex = scanner.nextInt();
                scanner.nextLine();
                if (saveIndex < 1 || saveIndex > app.saveFiles.size()) {
                    Utils.printTitle("Invalid save file number.");
                    break;
                }
                File saveFile = app.saveFiles.get(saveIndex - 1);
                app.deleteSaveFile(saveFile);
                Utils.printTitle("Save file deleted");
                System.out.println("Press enter to continue...");
                scanner.nextLine();
                break;

            case App.MAX_SAVES + 2: // Load XML file
                Utils.printTitle("Load XML file");
                if (app.saveFiles.size() == App.MAX_SAVES) {
                    Utils.printTitle("Maximum number of save files reached.");
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                }
                System.out.println("Enter the name of the XML file to load: ");
                String xmlFileName = scanner.nextLine();
                File xmlFile = new File(xmlFileName);
                if (!xmlFile.exists()) {
                    Utils.printTitle("File not found: " + xmlFileName);
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                } else {
                    try {
                        // app.loadXMLFile(xmlFile); TODO
                        Utils.printTitle("Colony loaded successfully from XML file: " + xmlFileName);
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        state = GAME_MENU;
                    } catch (Exception e) {
                        Utils.printTitle("Error loading XML file: " + xmlFileName);
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    }
                }
                break;

            case App.MAX_SAVES + 3: // Back to main menu
                state = MAIN_MENU;
                break;

            default: // Load save file
                if (choice <= app.saveFiles.size()) {
                    File saveToLoad = app.saveFiles.get(choice - 1);
                    try {
                        app.parseColony(saveToLoad);
                    } catch (FileLoadException e) {
                        Utils.printTitle("Error loading save file: " + saveToLoad.getName());
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        return;
                    }
                    Utils.printTitle("Colony " + app.colony.getColonyName() + " loaded");
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    state = GAME_MENU;
                } else { // New game
                    state = NEW_GAME_MENU;
                }
                break;
        }
    }

    private void newGameMenu() {
        Utils.printTitle("New game");
        System.out.println("Enter the name of your colony: ");
        String colonyName = scanner.nextLine();
        Colony colony = new Colony(colonyName);
        app.logger.info("Colony created: " + colonyName);
        System.out.println("Enter the name of your first settler: ");
        String settlerName = scanner.nextLine();
        Settler settler = new Settler(settlerName, colony);
        app.logger.info("Settler created: " + settlerName);
        try {
            colony.addSettler(settler);
            app.logger.info("Settler added to colony: " + settlerName);
        } catch (PopulationLimitException e) {
            // This should never happen, as we just created the colony.
        }
        app.colony = colony;
        System.out.println("Colony created!");
        state = GAME_MENU;
    }
    
    private void gameMenu() {
        Utils.printTitle(app.colony.getColonyName() + " Colony - Day: " + app.colony.getCurrDay() +  " - Population: " + app.colony.getPopulation() + "/" + app.colony.getMaxPopulation());
        System.out.println("Wood: " + app.colony.getWood() + " | Food: " + app.colony.getFood() + " | Stone: " + app.colony.getStone() + " | Metal: " + app.colony.getMetal() + "\n");
        String[] options = {
            "Next day",
            "Settlers",
            "Structures",
            "Save game",
            "back to main menu",
            "Exit"
        };
        int choice = Utils.choiceList(options, scanner, 3);
        switch (choice) {
            case 1:
                state = NEXT_DAY_MENU;
                break;
            
            case 2:
                state = SETTLER_MENU;
                break;
            
            case 3:
                state = STRUCTURE_MENU;
                break;
            
            case 4:
                try {
                    app.saveGame();
                    Utils.printTitle("Game saved!");
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                } catch (Exception e) {
                    Utils.printTitle("Error saving game");
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                }
                break;

            case 5:
                try {
                    app.saveGame();
                    state = MAIN_MENU;
                } catch (FileSaveException e) {
                    Utils.printTitle("Error saving game: " + app.colony.getColonyName());
                    System.out.println("Do you still wish to continue? (y/n)");
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        state = MAIN_MENU;
                        break;
                    } else {
                        state = GAME_MENU;
                        break;
                    }
                }
                break;

            case 6:
                System.exit(0);
                break; 
        }
    }

    private void listSettlers() {
        Utils.printTitle("Settlers");
        if (app.colony.getSettlers().isEmpty()) {
            System.out.println("No settlers in the colony.");
        } else {
            for (Settler settler : app.colony.getSettlers()) {
                System.out.println(settler.getSettlerInfo(false));
            } 
        }
        System.out.println("\nOverall happiness: " + (int) app.colony.updateOverallHappiness() + "%");
        System.out.println("\nPress enter to continue...");
        scanner.nextLine();
    }

    private void listStructures() {
        Utils.printTitle("Structures");
        if (app.colony.getStructures().isEmpty()) {
            System.out.println("No structures in the colony.");
        } else {
            for (Structure structure : app.colony.getStructures()) {
                System.out.println(structure.getStructureInfo(false));
            }
        }
        System.out.println("\nPress enter to continue...");
        scanner.nextLine();
    }
}

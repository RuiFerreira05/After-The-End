package tps.tp4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tps.tp4.structures.Structure;
import tps.tp4.structures.StructureFactory;
import tps.tp4.structures.StructureTypes;
import tps.tp4.errors.*;
import tps.tp4.settlers.Settler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.PropertySource.Util;

/*
 * TODO:
 * 
 * - Events
 * 
 * - Save and load game
 *   \ multiple saves
 * 
 * - Settler options
 *   \ hunt, gather, assign to building(?), set as warrior
 * 
 * - Structure options
 *   \ destroy, assign settler(?) 
 */

public class App {

    private static final String SAVEPATH = "saves/";
    private static final int MAX_SAVES = 5;
    private static final int LOAD_SAVES = 0;
    private static final int MAIN_MENU = 1;
    private static final int GAME_MENU = 2;
    private static final int SETTLER_MENU = 3;
    private static final int STRUCTURE_MENU = 4;
    private static final int EVENT_MENU = 5;
    private static final int BUILD_STRUCTURE_MENU = 6;
    private static final int EXIT = 7;
    private static final int PLAY_MENU = 8;
    private static final int NEW_GAME_MENU = 9;

    private static final int INITIAL_STATE = MAIN_MENU;
    
    private Colony colony;
    private List<File> saveFiles;
    private Scanner scanner = new Scanner(System.in);
    private int state;
    private boolean debug;
    protected Logger logger;

    
    public App(boolean debug) {
        this.colony = null;
        this.saveFiles = new ArrayList<File>();
        this.scanner = new Scanner(System.in);
        this.state = INITIAL_STATE;
        this.debug = debug;
        this.logger = LogManager.getLogger(debug ? "debugLogger" : "defaultLogger");

        // run the exit method when the program is terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            exit();
        }));
    }

    private void exit() {
        if (colony != null) {
            try {
                saveGame();
            } catch (Exception e) {
                logger.error("Error saving game on exit: " + colony.getColonyName(), e);
                scanner.nextLine();
            }
        }
        logger.info("Exiting application.");
        scanner.close();
    }

    private void loadSaves() {
        File[] saves = new File(SAVEPATH).listFiles();
        if (saves != null) {
            for (File save : saves) {
                if (save.isFile() && save.getName().endsWith(".save")) {
                    saveFiles.add(save);
                }
            }
        }
    }

    public void start() {
        loadSaves();
        while (true) {
            switch (state) {
    
                case MAIN_MENU:
                    mainMenu();
                    break;

                case PLAY_MENU:
                    play();
                    break;

                case NEW_GAME_MENU:
                    newGame();
                    break;

                case GAME_MENU:
                    gameMenu();
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
                    logger.fatal("Invalid app state: " + state, new InvalidAppStateException(state));
                    System.exit(1);
                    break;
            }
        }
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

            default:
                logger.error("Invalid choice in structure menu: " + choice, new InvalidAppStateException(choice));
                System.exit(1);
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
                colony.addStructure(structure);
                logger.info("Structure built: " + structure.getName());
                Utils.printTitle(structure.getName() + " built!");
                System.out.println("Press enter to continue...");
                scanner.nextLine();
            } catch (NotEnoughResourcesException e) {
                logger.error("Not enough resources to build structure: " + structure.getName(), e);
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

            default:
                logger.error("Invalid choice in settler menu: " + choice, new InvalidAppStateException(choice));
                System.exit(1);
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

            default:
                // default case is not needed as the options are sanitized in the choiceList function.
                // If this triggers, something is very wrong...
                logger.error("Invalid choice in main menu: " + choice, new InvalidAppStateException(choice));
                System.exit(1);
                break;
        }
    }

    private void play() {
        Utils.printTitle("Before The Fall...");
        String[] options = new String[MAX_SAVES + 3];
        for (int i = 0; i < saveFiles.size(); i++) {
            options[i] = saveFiles.get(i).getName().replace(".save", "");
        }
        for (int i = saveFiles.size(); i < MAX_SAVES; i++) {
            options[i] = "Empty save slot";
        }
        options[MAX_SAVES] = "delete save file";
        options[MAX_SAVES + 1] = "load XML file";
        options[MAX_SAVES + 2] = "Back to main menu";
        int choice = Utils.choiceList(options, scanner, MAX_SAVES);
        switch (choice) {
            case MAX_SAVES + 1: // Delete save file
                if (saveFiles.isEmpty()) {
                    Utils.printTitle("No save files to delete.");
                    break;
                }
                System.out.println("Enter the number of the save file to delete: ");
                int saveIndex = scanner.nextInt();
                scanner.nextLine();
                if (saveIndex < 1 || saveIndex > saveFiles.size()) {
                    Utils.printTitle("Invalid save file number.");
                    break;
                }
                File saveFile = saveFiles.get(saveIndex - 1);
                saveFile.delete();
                saveFiles.remove(saveIndex - 1);
                logger.info("Save file deleted: " + saveFile.getName());
                Utils.printTitle("Save file deleted");
                System.out.println("Press enter to continue...");
                scanner.nextLine();
                break;

            case MAX_SAVES + 2: // Load XML file
                Utils.printTitle("Load XML file");
                if (saveFiles.size() == MAX_SAVES) {
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
                        Colony colony = XMLParser.parseColony(xmlFile);
                        this.colony = colony;
                        logger.info("Colony loaded from XML file: " + xmlFileName);
                        Utils.printTitle("Colony loaded successfully from XML file: " + xmlFileName);
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        state = GAME_MENU;
                    } catch (Exception e) {
                        logger.error("Error loading XML file: " + xmlFileName, e);
                        Utils.printTitle("Error loading XML file: " + xmlFileName);
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    }
                }
                break;

            case MAX_SAVES + 3: // Back to main menu
                state = MAIN_MENU;
                break;

            default: // Load save file
                if (choice <= saveFiles.size()) {
                    File saveToLoad = saveFiles.get(choice - 1);
                    try {
                        parseColony(saveToLoad);
                    } catch (FileLoadException e) {
                        logger.error("Error loading save file: " + saveToLoad.getName(), e);
                        Utils.printTitle("Error loading save file: " + saveToLoad.getName());
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        return;
                    }
                    Utils.printTitle("Colony " + colony.getColonyName() + " loaded");
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    state = GAME_MENU;
                } else { // New game
                    state = NEW_GAME_MENU;
                }
                break;
        }
    }

    private void newGame() {
        Utils.printTitle("New game");
        System.out.println("Enter the name of your colony: ");
        String colonyName = scanner.nextLine();
        Colony colony = new Colony(colonyName);
        logger.info("Colony created: " + colonyName);
        this.colony = colony;
        System.out.println("Enter the name of your first settler: ");
        String settlerName = scanner.nextLine();
        Settler settler = new Settler(settlerName, colony);
        logger.info("Settler created: " + settlerName);
        try {
            colony.addSettler(settler);
            logger.info("Settler added to colony: " + settlerName);
        } catch (PopulationLimitException e) {
            // This should never happen, as we just created the colony and the settler.
        }
        System.out.println("Colony created!");
        state = GAME_MENU;
    }

    private void parseColony(File saveFile) throws FileLoadException {
        try {
            FileInputStream fis = new FileInputStream(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            colony = (Colony) ois.readObject();
            ois.close();
            fis.close();
            logger.info("Colony loaded: " + colony.getColonyName());
        } catch (Exception e) {
            throw new FileLoadException("Error loading save file: " + saveFile.getName(), e);
        }
    }

    private void saveGame() throws FileSaveException {
        File saveFile = new File(SAVEPATH + colony.getColonyName() + ".save");
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        try {
            saveFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(colony);
        } catch (Exception e) {
            throw new FileSaveException("Error saving game: " + colony.getColonyName(), e);
        }
    }
    
    private void gameMenu() {
        Utils.printTitle(colony.getColonyName() + " Colony - Day: " + colony.getCurrDay() +  " - Population: " + colony.getPopulation() + "/" + colony.getMaxPopulation());
        System.out.println("Wood: " + colony.getWood() + " | Food: " + colony.getFood() + " | Stone: " + colony.getStone() + " | Metal: " + colony.getMetal() + "\n");
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
                nextDay();
                break;
            
            case 2:
                state = SETTLER_MENU;
                break;
            
            case 3:
                state = STRUCTURE_MENU;
                break;
            
            case 4:
                try {
                    saveGame();
                    Utils.printTitle("Game saved!");
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                } catch (Exception e) {
                    logger.error("Error saving game: " + colony.getColonyName(), e);
                    Utils.printTitle("Error saving game: " + colony.getColonyName());
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                }
                break;

            case 5:
                try {
                    saveGame();
                    state = MAIN_MENU;
                } catch (FileSaveException e) {
                    logger.error("Error saving game: " + colony.getColonyName(), e);
                    Utils.printTitle("Error saving game: " + colony.getColonyName());
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

            default:
                // default case is not needed as the options are sanitized in the choiceList function.
                // If this triggers, something is very wrong...
                logger.error("Invalid choice in main menu: " + choice, new InvalidAppStateException(choice));
                System.exit(1);
                break;  
        }
    }

    private void nextDay() {
        colony.nextDay();
    }

    private void listSettlers() {
        Utils.printTitle("Settlers");
        if (colony.getSettlers().isEmpty()) {
            System.out.println("No settlers in the colony.");
        } else {
            for (Settler settler : colony.getSettlers()) {
                System.out.println(settler.getSettlerInfo(false));
            } 
        }
        System.out.println("\nOverall happiness: " + (int) colony.updateOverallHappiness() + "%");
        System.out.println("\nPress enter to continue...");
        scanner.nextLine();
    }

    private void listStructures() {
        Utils.printTitle("Structures");
        if (colony.getStructures().isEmpty()) {
            System.out.println("No structures in the colony.");
        } else {
            for (Structure structure : colony.getStructures()) {
                System.out.println(structure.getStructureInfo(false));
            }
        }
        System.out.println("\nPress enter to continue...");
        scanner.nextLine();
    }

    public Colony getGame() {
        return colony;
    }

    public Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        App app = new App(false);
        app.start();
    }
}

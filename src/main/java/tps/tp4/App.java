package tps.tp4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tps.tp4.structures.Structure;
import tps.tp4.errors.*;
import tps.tp4.settlers.Settler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private final String SAVEPATH = "saves/";
    private final int MAX_SAVES = 5;
    private final int MAIN_MENU = 0;
    private final int GAME_MENU = 1;
    private final int SETTLER_MENU = 2;
    private final int STRUCTURE_MENU = 3;
    private final int EVENT_MENU = 4;
    private final int EXIT = 5;
    
    private Colony colony;
    private List<String> saveFiles;
    private Scanner scanner = new Scanner(System.in);
    private int state;
    private boolean debug;
    protected Logger logger;
    
    public App(boolean debug) {
        this.colony = null;
        this.saveFiles = new ArrayList<String>();
        this.scanner = new Scanner(System.in);
        this.state = MAIN_MENU;
        this.debug = debug;
        this.logger = LogManager.getLogger(debug ? "debugLogger" : "defaultLogger");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            exit();
        }));
    }

    private void exit() {
        if (colony != null) {
            saveGame();
        }
        logger.info("Exiting application.");
        scanner.close();
    }

    private void loadSaves() {
        // TODO
    }

    public void start() {
        loadSaves();
        while (true) {
            switch (state) {
                case MAIN_MENU:
                mainMenu();
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
                state = GAME_MENU;
                break;

            default:
                logger.error("Invalid choice in structure menu: " + choice, new InvalidAppStateException(choice));
                System.exit(1);
                break;  
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
            "New game",
            "Load game",
            "Exit"
        };
        int choice = Utils.choiceList(options, scanner);
        switch (choice) {
            case 1:
                newGame();
                break;
            
            case 2:
                loadGame();
                break;
            
            case 3:
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

    private void newGame() {
        Utils.printTitle("New game");
        System.out.println("Enter the name of your colony: ");
        String colonyName = scanner.nextLine();
        Colony colony = new Colony(colonyName, this);
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

    private void loadGame() {
        // TODO
    }

    private void saveGame() {
        // TODO
    }
    
    private void gameMenu() {
        Utils.printTitle(colony.getColonyName() + " Colony - Day: " + colony.getCurrDay() +  " - Population: " + colony.getPopulation() + "/" + colony.getMaxPopulation());
        System.out.println("\nWood: " + colony.getWood() + " | Food: " + colony.getFood() + " | Stone: " + colony.getStone() + " | Metal: " + colony.getMetal() + "\n");
        String[] options = {
            "Next day",
            "Settlers",
            "Structures",
            "Save game",
            "Exit"
        };
        int choice = Utils.choiceList(options, scanner);
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
                saveGame();
                break;

            case 5:
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
        System.out.println("\nOverall happiness: " + colony.updateOverallHappiness());
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

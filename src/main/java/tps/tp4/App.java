package tps.tp4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tps.tp4.Errors.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private final String SAVEPATH = "saves/";
    private final int MAIN_MENU = 0;
    private final int GAME_MENU = 1;
    
    private Game game;
    private List<String> saveFiles;
    private Scanner scanner = new Scanner(System.in);
    private int state;
    private boolean debug;
    protected Logger logger;
    
    public App(boolean debug) {
        this.game = null;
        this.saveFiles = new ArrayList<String>();
        this.scanner = new Scanner(System.in);
        this.state = MAIN_MENU;
        this.debug = debug;
        this.logger = LogManager.getLogger(debug ? "debugLogger" : "defaultLogger");
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

                // TODO

                default:
                logger.fatal("Invalid state: " + state, new InvalidAppStateException("Invalid state: " + state));
                System.exit(1);
            }
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
        
    }
    
    private void gameMenu() {
        // TODO
    }

    public static void main(String[] args) {
        App app = new App(false);
        app.start();
    }
}

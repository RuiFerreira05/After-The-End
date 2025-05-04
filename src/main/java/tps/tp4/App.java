package tps.tp4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tps.tp4.Errors.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private final String SAVEPATH = "saves/";
    private final int INITMENU = 0;
    private final int MAINMENU = 1;
    
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
        this.state = 4;
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
                case INITMENU:
                initMenu();
                break;

                case MAINMENU:
                mainMenu();
                break;

                // TODO

                default:
                logger.fatal("Invalid state: " + state, new InvalidAppStateException("Invalid state: " + state));
                System.exit(1);
            }
            break;
        }
    }
    
    private void initMenu() {
        Utils.printTitle("Welcome to After The End!");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        System.out.println("3. Exit");
        System.out.println();
        System.out.print(">> ");
        logger.info("Waiting for user input...");
        // String choice = scanner.nextLine();
        // TODO
    }
    
    private void mainMenu() {
        // TODO
    }

    public static void main(String[] args) {
        App app = new App(false);
        app.start();
    }
}

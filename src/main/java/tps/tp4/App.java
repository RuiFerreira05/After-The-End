package tps.tp4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import tps.tp4.structures.Structure;
import tps.tp4.ui.UI;
import tps.tp4.ui.UI_Console;
import tps.tp4.xml.XMLParser;
import tps.tp4.Events.E_GameOver;
import tps.tp4.Events.Event;
import tps.tp4.Events.EventFactory;
import tps.tp4.Events.EventTypes;
import tps.tp4.errors.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main application class for managing the game state, saves, and user interface.
 */
public class App {

    public static final String SAVEPATH = "saves/";
    public static final int MAX_SAVES = 5;
    
    public Colony colony;
    public List<File> saveFiles;
    public boolean debug;
    public Logger logger;
    
    public UI ui;

    /**
     * Constructs the main application, initializing settings, saves, and UI.
     * @param debug Whether to enable debug logging.
     */
    public App(boolean debug) {
        this.colony = null;
        this.saveFiles = new ArrayList<File>();
        this.debug = debug;
        this.logger = LogManager.getLogger(debug ? "debugLogger" : "defaultLogger");
        this.ui = new UI_Console(this);
        loadSaves();
        loadSettings();

        // run the exit method when the program is terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            exit();
        }));
    }

    /**
     * Loads a colony from an XML file using the XMLParser.
     * @param colonyFile The XML file containing the colony data.
     * @throws Exception If loading fails.
     */
    public void loadColonyFromXML(File colonyFile) throws Exception {
        try {
            colony = XMLParser.parseColony(colonyFile); 
        } catch (FileLoadException e) {
            logger.error("Error loading colony from file: " + colonyFile.getName(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while loading colony from file: " + colonyFile.getName(), e);
            throw e;
        }
    }

    /**
     * Loads settings from the user or default settings XML file,
     * with user settings taking precedence.
     */
    private void loadSettings() {
        File settingsFile = new File("src/main/java/tps/tp4/settings/user_settings.xml");
        if (!settingsFile.exists()) {
            settingsFile = new File("src/main/java/tps/tp4/settings/default_settings.xml");
        }
        try {
            XMLParser.loadXMLSettings(settingsFile);
            logger.info("Settings loaded from: " + settingsFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Error loading settings from file: " + settingsFile.getAbsolutePath(), e);
        }
    }

    /**
     * Saves the current game state on application exit, if a colony is loaded.
     */
    private void exit() {
        if (colony != null) {
            try {
                saveGame();
            } catch (Exception e) {
                logger.error("Error saving game on exit: " + colony.getColonyName(), e);
            }
        }
        logger.info("Exiting application.");
    }

    /**
     * Loads all save files from the save directory into the saveFiles list.
     */
    public void loadSaves() {
        File[] saves = new File(SAVEPATH).listFiles();
        if (saves != null) {
            for (File save : saves) {
                if (save.isFile() && save.getName().endsWith(".save")) {
                    saveFiles.add(save);
                }
            }
        }
    }

    /**
     * Loads a colony from a save file (.save).
     * @param saveFile The file to load.
     * @throws FileLoadException If loading fails.
     */
    public void parseColony(File saveFile) throws FileLoadException {
        try {
            FileInputStream fis = new FileInputStream(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            colony = (Colony) ois.readObject();
            ois.close();
            fis.close();
            logger.info("Colony loaded: " + colony.getColonyName());
        } catch (Exception e) {
            logger.error("Error loading save file: " + saveFile.getName(), e);
            throw new FileLoadException("Error loading save file: " + saveFile.getName(), e);
        }
    }

    /**
     * Deletes a save file and removes it from the saveFiles list.
     * @param saveFile The file to delete.
     */
    public void deleteSaveFile(File saveFile) {
        saveFile.delete();
        saveFiles.remove(saveFile);
        logger.info("Save file deleted: " + saveFile.getName());
    }

    /**
     * Saves the current colony to a save file.
     * @throws FileSaveException If saving fails.
     */
    public void saveGame() throws FileSaveException {
        File saveFile = new File(SAVEPATH + colony.getColonyName() + ".save");
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        try {
            saveFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(colony);
            oos.close();
            fos.close();
        } catch (Exception e) {
            logger.error("Error saving game: " + colony.getColonyName(), e);
            throw new FileSaveException("Error saving game: " + colony.getColonyName(), e);
        }
    }

    /**
     * Adds a structure to the current colony, updating resources accordingly.
     * @param structure The structure to add.
     * @throws NotEnoughResourcesException If resources are insufficient.
     */
    public void addStructureToColony(Structure structure) throws NotEnoughResourcesException {
        try {
            colony.addStructure(structure);
            logger.info("Structure added to colony: " + structure.getName());
        } catch (NotEnoughResourcesException e) {
            logger.error("Not enough resources to add structure: " + structure.getName(), e);
            throw e;
        }
    }

    /**
     * Advances the game by one day, triggering events and updating resources.
     * @return The event that occurred, or null if none.
     */
    public Event nextDay() {
        colony.nextDay();
        if (checkGameOver()) {
            logger.info("Game over");
            return new E_GameOver();
        }
        return EventFactory.createEvent(EventTypes.pickRandom());
    }

    /**
     * Checks if the game is over based on population and resources.
     * @return True if the game is over, false otherwise.
     */
    private boolean checkGameOver() {
        return colony.getPopulation() == 0 || 
        (colony.getWood() <= 0 && colony.getFood() <= 0 && colony.getStone() <= 0 && colony.getMetal() <= 0);
    }

    /**
     * Main entry point for the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        App app = new App(false);
        app.ui.start();
    }

    /**
     * Exports the current colony to an XML file at the specified path.
     * @param exportPath The directory to export the XML file to.
     */
    public void exportColonyToXML(String exportPath) {
        try {
            XMLParser.exportColonyToXML(colony, exportPath);
            logger.info("Colony exported to XML: " + exportPath);
        } catch (Exception e) {
            logger.error("Error exporting colony to XML: " + exportPath, e);
        }
    }
}

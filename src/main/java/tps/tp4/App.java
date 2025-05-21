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
import tps.tp4.errors.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final String SAVEPATH = "saves/";
    public static final int MAX_SAVES = 5;
    
    public Colony colony;
    public List<File> saveFiles;
    public boolean debug;
    public Logger logger;
    
    public UI ui;

    public App(boolean debug) {
        this.colony = null;
        this.saveFiles = new ArrayList<File>();
        this.debug = debug;
        this.logger = LogManager.getLogger(debug ? "debugLogger" : "defaultLogger");
        this.ui = new UI_Console(this);
        loadSaves();

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
            }
        }
        logger.info("Exiting application.");
    }

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

    public void deleteSaveFile(File saveFile) {
        saveFile.delete();
        saveFiles.remove(saveFile);
        logger.info("Save file deleted: " + saveFile.getName());
    }

    // TODO
    // public void loadXMLFile(File xmlFile) throws FileLoadException {
    //     try {
    //         Colony colony = XMLParser.parseColony(xmlFile);
    //         this.colony = colony;
    //         saveFiles.add(new File(SAVEPATH + colony.getColonyName() + ".save"));
    //         saveGame();
    //         logger.info("Colony loaded from XML file: " + xmlFile.getName());
    //     } catch (Exception e) {
    //         logger.error("Error loading XML file: " + xmlFile.getName(), e);
    //         throw new FileLoadException("Error loading XML file: " + xmlFile.getName(), e);
    //     }
    // }

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

    public void addStructureToColony(Structure structure) throws NotEnoughResourcesException {
        try {
            colony.addStructure(structure);
            logger.info("Structure added to colony: " + structure.getName());
        } catch (NotEnoughResourcesException e) {
            logger.error("Not enough resources to add structure: " + structure.getName(), e);
            throw e;
        }
    }

    public void nextDay() {
        colony.nextDay();
        // TODO: maybe add events here
    }

    public static void main(String[] args) {
        App app = new App(false);
        app.ui.start();
    }
}

package tps.tp4.settings;

import java.io.File;

public class Settings {

    public static final String VALIDATION_PATH = "./settings.dtd";
    
    public static int INITIAL_MAX_POPULATION;
    public static int INITIAL_WOOD;
    public static int INITIAL_FOOD;
    public static int INITIAL_STONE;
    public static int INITIAL_METAL;
    public static int INITIAL_ENTERTAINMENT;

    public static int INITIAL_WOOD_PRODUCTION;
    public static int INITIAL_FOOD_PRODUCTION;
    public static int INITIAL_STONE_PRODUCTION;
    public static int INITIAL_METAL_PRODUCTION;

    public static int[] HOUSE_COST;
    public static int[] HOUSE_PRODUCTION;
    public static int HOUSE_POPULATION_INCREASE;

    public static int[] FARM_COST;
    public static int[] FARM_PRODUCTION;

    public static int[] FACTORY_COST;
    public static int[] FACTORY_PRODUCTION;
    
    public static int[] HOSPITAL_COST;
    public static int[] HOSPITAL_PRODUCTION;

    public static int[] WOODCUTTER_LODGE_COST;
    public static int[] WOODCUTTER_LODGE_PRODUCTION;

    public static int[] MINE_COST;
    public static int[] MINE_PRODUCTION;

    public static int[] BARRACKS_COST;
    public static int[] BARRACKS_PRODUCTION;
    public static int BARRACKS_WARRIORS_INCREASE;

    public static void loadXMLSettings(File file) {

    }
}

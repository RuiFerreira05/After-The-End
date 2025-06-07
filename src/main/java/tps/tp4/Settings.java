package tps.tp4;

/**
 * Holds all configurable game settings, loaded from XML files.
 */
public class Settings {

    public static final String DEFAULT_SETTINGS_FILE = "src/main/resources/xml/default_settings.xml";
    public static final String USER_SETTINGS_FILE = "src/main/resources/xml/user_settings.xml";    
    
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

    /**
     * Prints all current settings values to the console for debugging purposes.
     */
    public static void debug() {
        System.out.println(INITIAL_MAX_POPULATION);
        System.out.println(INITIAL_WOOD);
        System.out.println(INITIAL_FOOD);
        System.out.println(INITIAL_STONE);
        System.out.println(INITIAL_METAL);
        System.out.println(INITIAL_ENTERTAINMENT);
        System.out.println(INITIAL_WOOD_PRODUCTION);
        System.out.println(INITIAL_FOOD_PRODUCTION);
        System.out.println(INITIAL_STONE_PRODUCTION);
        System.out.println(INITIAL_METAL_PRODUCTION);
        System.out.println("House Cost: " + java.util.Arrays.toString(HOUSE_COST));
        System.out.println("House Production: " + java.util.Arrays.toString(HOUSE_PRODUCTION));
        System.out.println("House Population Increase: " + HOUSE_POPULATION_INCREASE);
        System.out.println("Farm Cost: " + java.util.Arrays.toString(FARM_COST));
        System.out.println("Farm Production: " + java.util.Arrays.toString(FARM_PRODUCTION));
        System.out.println("Factory Cost: " + java.util.Arrays.toString(FACTORY_COST));
        System.out.println("Factory Production: " + java.util.Arrays.toString(FACTORY_PRODUCTION));
        System.out.println("Hospital Cost: " + java.util.Arrays.toString(HOSPITAL_COST));
        System.out.println("Hospital Production: " + java.util.Arrays.toString(HOSPITAL_PRODUCTION));
        System.out.println("Woodcutter Lodge Cost: " + java.util.Arrays.toString(WOODCUTTER_LODGE_COST));
        System.out.println("Woodcutter Lodge Production: " + java.util.Arrays.toString(WOODCUTTER_LODGE_PRODUCTION));
        System.out.println("Mine Cost: " + java.util.Arrays.toString(MINE_COST));
        System.out.println("Mine Production: " + java.util.Arrays.toString(MINE_PRODUCTION));
        System.out.println("Barracks Cost: " + java.util.Arrays.toString(BARRACKS_COST));
        System.out.println("Barracks Production: " + java.util.Arrays.toString(BARRACKS_PRODUCTION));
        System.out.println("Barracks Warriors Increase: " + BARRACKS_WARRIORS_INCREASE);
    }

    public static String outputString() {
        return "Initial Max Population: " + INITIAL_MAX_POPULATION + "\n" +
               "Initial Wood: " + INITIAL_WOOD + "\n" +
               "Initial Food: " + INITIAL_FOOD + "\n" +
               "Initial Stone: " + INITIAL_STONE + "\n" +
               "Initial Metal: " + INITIAL_METAL + "\n" +
               "Initial Entertainment: " + INITIAL_ENTERTAINMENT + "\n" +
               "Initial Wood Production: " + INITIAL_WOOD_PRODUCTION + "\n" +
               "Initial Food Production: " + INITIAL_FOOD_PRODUCTION + "\n" +
               "Initial Stone Production: " + INITIAL_STONE_PRODUCTION + "\n" +
               "Initial Metal Production: " + INITIAL_METAL_PRODUCTION + "\n\n" +
               "House Cost: " + java.util.Arrays.toString(HOUSE_COST) + "\n" +
               "House Production: " + java.util.Arrays.toString(HOUSE_PRODUCTION) + "\n" +
               "House Population Increase: " + HOUSE_POPULATION_INCREASE + "\n\n" +
               "Farm Cost: " + java.util.Arrays.toString(FARM_COST) + "\n" +
               "Farm Production: " + java.util.Arrays.toString(FARM_PRODUCTION) + "\n\n" +
               "Factory Cost: " + java.util.Arrays.toString(FACTORY_COST) + "\n" +
               "Factory Production: " + java.util.Arrays.toString(FACTORY_PRODUCTION) + "\n\n" +
               "Hospital Cost: " + java.util.Arrays.toString(HOSPITAL_COST) + "\n" +
               "Hospital Production: " + java.util.Arrays.toString(HOSPITAL_PRODUCTION) + "\n\n" +
               "Woodcutter Lodge Cost: " + java.util.Arrays.toString(WOODCUTTER_LODGE_COST) + "\n" +
               "Woodcutter Lodge Production: " + java.util.Arrays.toString(WOODCUTTER_LODGE_PRODUCTION) + "\n\n" +
               "Mine Cost: " + java.util.Arrays.toString(MINE_COST) + "\n" +
               "Mine Production: " + java.util.Arrays.toString(MINE_PRODUCTION) + "\n\n" +
               "Barracks Cost: " + java.util.Arrays.toString(BARRACKS_COST) + "\n" +
               "Barracks Production: " + java.util.Arrays.toString(BARRACKS_PRODUCTION) + "\n" +
               "Barracks Warriors Increase: " + BARRACKS_WARRIORS_INCREASE;
    }
}

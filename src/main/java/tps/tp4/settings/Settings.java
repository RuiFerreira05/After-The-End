package tps.tp4.settings;

public class Settings {
    
    public static final int INITIAL_MAX_POPULATION = 10;
    public static final int INITIAL_WOOD = 500;
    public static final int INITIAL_FOOD = 500;
    public static final int INITIAL_STONE = 500;
    public static final int INITIAL_METAL = 500;
    public static final int INITIAL_ENTERTAINMENT = 0;

    public static final int INITIAL_WOOD_PRODUCTION = 0;
    public static final int INITIAL_FOOD_PRODUCTION = 0;
    public static final int INITIAL_STONE_PRODUCTION = 0;
    public static final int INITIAL_METAL_PRODUCTION = 0;

    public static final int[] HOUSE_COST = {100, 10, 0}; // wood, stone, metal
    public static final int[] HOUSE_PRODUCTION = {0, 0, 0, 0, 0}; // wood, food, stone, metal, entertainments
    public static final int HOUSE_POPULATION_INCREASE = 3;

    public static final int[] FARM_COST = {50, 0, 0}; // wood, stone, metal
    public static final int[] FARM_PRODUCTION = {0, 1, 0, 0, 0}; // wood, food, stone, metal, entertainment

    public static final int[] FACTORY_COST = {200, 50, 0}; // wood, stone, metal
    public static final int[] FACTORY_PRODUCTION = {0, 0, 0, 1, 0}; // wood, food, stone, metal, entertainment
    
    public static final int[] HOSPITAL_COST = {100, 50, 0}; // wood, stone, metal
    public static final int[] HOSPITAL_PRODUCTION = {0, 0, 0, 0, 0}; // wood, food, stone, metal, entertainment

    public static final int[] WOODCUTTER_LODGE_COST = {50, 0, 0}; // wood, stone, metal
    public static final int[] WOODCUTTER_LODGE_PRODUCTION = {1, 0, 0, 0, 0}; // wood, food, stone, metal, entertainment

    public static final int[] MINE_COST = {100, 0, 0}; // wood, stone, metal
    public static final int[] MINE_PRODUCTION = {0, 0, 1, 0, 0}; // wood, food, stone, metal, entertainment

    public static final int[] BARRACKS_COST = {200, 100, 0}; // wood, stone, metal
    public static final int[] BARRACKS_PRODUCTION = {0, 0, 0, 0, 0}; // wood, food, stone, metal, entertainment
    public static final int BARRACKS_WARRIORS_INCREASE = 5; // max warriors increase
}   

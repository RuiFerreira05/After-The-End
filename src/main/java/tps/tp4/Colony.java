package tps.tp4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tps.tp4.structures.Structure;
import tps.tp4.errors.NotEnoughResourcesException;
import tps.tp4.errors.PopulationLimitException;
import tps.tp4.settings.Settings;
import tps.tp4.settlers.Settler;

/**
 * Represents a colony in the game, managing resources, settlers, and structures.
 */
public class Colony implements Serializable {

    private String colonyName;
    private int wood;
    private int food;
    private int stone;
    private int metal;
    private int entertainment;
    private int woodProduction;
    private int foodProduction;
    private int stoneProduction;
    private int metalProduction;
    private int population;
    private int maxPopulation;
    private int warriors;
    private int maxWarriors;
    private double overAllHappiness;
    private List<Settler> settlers;
    private List<Structure> structures;
    private int currDay;
    private boolean gameOver;

    /**
     * Constructs a new Colony with the specified name and initializes resources.
     * @param colonyName The name of the colony.
     */
    public Colony(String colonyName) {
        this.settlers = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.colonyName = colonyName;
        this.wood = Settings.INITIAL_WOOD;
        this.food = Settings.INITIAL_FOOD;
        this.stone = Settings.INITIAL_STONE;
        this.metal = Settings.INITIAL_METAL;
        this.entertainment = Settings.INITIAL_ENTERTAINMENT;
        this.woodProduction = Settings.INITIAL_WOOD_PRODUCTION;
        this.foodProduction = Settings.INITIAL_FOOD_PRODUCTION;
        this.stoneProduction = Settings.INITIAL_STONE_PRODUCTION;
        this.metalProduction = Settings.INITIAL_METAL_PRODUCTION;
        this.maxPopulation = Settings.INITIAL_MAX_POPULATION;
        this.population = settlers.size();
        this.warriors = 0;
        this.maxWarriors = 1;
        updateOverallHappiness();
        this.currDay = 1;
        this.gameOver = false;
    }

    /**
     * Updates and returns the overall happiness of the colony based on settlers.
     * @return The updated overall happiness.
     */
    public double updateOverallHappiness() {
        double happiness = 0;
        for (Settler settler : settlers) {
            happiness += settler.updateHappiness();
        }
        this.overAllHappiness = happiness / population;
        return overAllHappiness;
    }

    /**
     * Advances the colony by one day, updating resources and happiness.
     */
    public void nextDay() {
        this.wood += woodProduction;
        this.food += foodProduction;
        this.stone += stoneProduction;
        this.metal += metalProduction;
        currDay++;
        updateOverallHappiness();
    }

    /**
     * Adds the specified resources to the colony.
     * @param resources Array of resources to add (wood, food, stone, metal).
     */
    public void addResources(int[] resources) {
        this.wood += resources[0];
        this.food += resources[1];
        this.stone += resources[2];
        this.metal += resources[3];
    }

    /**
     * Adds a settler to the colony if population limit is not reached.
     * @param settler The settler to add.
     * @throws PopulationLimitException If the population limit is reached.
     */
    public void addSettler(Settler settler) throws PopulationLimitException {
        if (population < maxPopulation) {
            settlers.add(settler);
            population++;
            updateOverallHappiness();
        } else {
            throw new PopulationLimitException();
        }
    }

    /**
     * Removes a settler from the colony.
     * @param settler The settler to remove.
     */
    public void removeSettler(Settler settler) {
        settlers.remove(settler);
        population--;
        updateOverallHappiness();
    }

    /**
     * Adds a structure to the colony if resources are sufficient.
     * @param structure The structure to add.
     * @throws NotEnoughResourcesException If resources are insufficient.
     */
    public void addStructure(Structure structure) throws NotEnoughResourcesException {
        if (checkCost(structure)) {
            structures.add(structure);
            wood -= structure.getCost()[0];
            stone -= structure.getCost()[1];
            metal -= structure.getCost()[2];

            structure.impact(this);
        } else {
            throw new NotEnoughResourcesException(structure);
        }
    }

    /**
     * Checks if the colony has enough resources to build the given structure.
     * @param structure The structure to check.
     * @return True if the cost can be paid, false otherwise.
     */
    private boolean checkCost(Structure structure) {
        if (structure.getCost()[0] > wood) {
            return false;
        } else if (structure.getCost()[1] > stone) {
            return false;
        } else if (structure.getCost()[2] > metal) {
            return false;
        }
        return true;
    }

    // ----------------- Getters and Setters ----------------- //

    /**
     * Gets the current day in the colony.
     * @return The current day.
     */
    public int getCurrDay() {
        return currDay;
    }

    /**
     * Gets the wood production per day.
     * @return The wood production.
     */
    public int getWoodProduction() {
        return woodProduction;
    }

    /**
     * Sets the wood production per day.
     * @param woodProduction The new wood production value.
     */
    public void setWoodProduction(int woodProduction) {
        this.woodProduction = woodProduction;
    }

    /**
     * Gets the food production per day.
     * @return The food production.
     */
    public int getFoodProduction() {
        return foodProduction;
    }

    /**
     * Sets the food production per day.
     * @param foodProduction The new food production value.
     */
    public void setFoodProduction(int foodProduction) {
        this.foodProduction = foodProduction;
    }

    /**
     * Gets the stone production per day.
     * @return The stone production.
     */
    public int getStoneProduction() {
        return stoneProduction;
    }

    /**
     * Sets the stone production per day.
     * @param stoneProduction The new stone production value.
     */
    public void setStoneProduction(int stoneProduction) {
        this.stoneProduction = stoneProduction;
    }

    /**
     * Gets the metal production per day.
     * @return The metal production.
     */
    public int getMetalProduction() {
        return metalProduction;
    }

    /**
     * Sets the metal production per day.
     * @param metalProduction The new metal production value.
     */
    public void setMetalProduction(int metalProduction) {
        this.metalProduction = metalProduction;
    }

    /**
     * Gets the name of the colony.
     * @return The colony name.
     */
    public String getColonyName() {
        return colonyName;
    }

    /**
     * Sets the name of the colony.
     * @param colonyName The new colony name.
     */
    public void setColonyName(String colonyName) {
        this.colonyName = colonyName;
    }

    /**
     * Gets the current wood resource.
     * @return The amount of wood.
     */
    public int getWood() {
        return wood;
    }

    /**
     * Sets the wood resource.
     * @param wood The new wood amount.
     */
    public void setWood(int wood) {
        this.wood = wood;
    }

    /**
     * Gets the current food resource.
     * @return The amount of food.
     */
    public int getFood() {
        return food;
    }

    /**
     * Sets the food resource.
     * @param food The new food amount.
     */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     * Gets the current stone resource.
     * @return The amount of stone.
     */
    public int getStone() {
        return stone;
    }

    /**
     * Sets the stone resource.
     * @param stone The new stone amount.
     */
    public void setStone(int stone) {
        this.stone = stone;
    }

    /**
     * Gets the current metal resource.
     * @return The amount of metal.
     */
    public int getMetal() {
        return metal;
    }

    /**
     * Sets the metal resource.
     * @param metal The new metal amount.
     */
    public void setMetal(int metal) {
        this.metal = metal;
    }

    /**
     * Gets the current entertainment value.
     * @return The entertainment value.
     */
    public int getEntertainment() {
        return entertainment;
    }

    /**
     * Sets the entertainment value.
     * @param entertainment The new entertainment value.
     */
    public void setEntertainment(int entertainment) {
        this.entertainment = entertainment;
    }

    /**
     * Gets the current population.
     * @return The population.
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Gets the maximum population allowed.
     * @return The maximum population.
     */
    public int getMaxPopulation() {
        return maxPopulation;
    }

    /**
     * Sets the maximum population allowed.
     * @param maxPopulation The new maximum population.
     */
    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    /**
     * Gets the number of warriors in the colony.
     * @return The number of warriors.
     */
    public int getWarriors() {
        return warriors;
    }

    /**
     * Sets the number of warriors in the colony.
     * @param warriors The new number of warriors.
     */
    public void setWarriors(int warriors) {
        this.warriors = warriors;
    }

    /**
     * Gets the maximum number of warriors allowed.
     * @return The maximum number of warriors.
     */
    public int getMaxWarriors() {
        return maxWarriors;
    }

    /**
     * Sets the maximum number of warriors allowed.
     * @param maxWarriors The new maximum number of warriors.
     */
    public void setMaxWarriors(int maxWarriors) {
        this.maxWarriors = maxWarriors;
    }

    /**
     * Gets the overall happiness of the colony.
     * @return The overall happiness.
     */
    public double getOverAllHappiness() {
        return overAllHappiness;
    }

    /**
     * Gets the list of settlers in the colony.
     * @return The list of settlers.
     */
    public List<Settler> getSettlers() {
        return settlers;
    }

    /**
     * Gets the list of structures in the colony.
     * @return The list of structures.
     */
    public List<Structure> getStructures() {
        return structures;
    }

    /**
     * Sets the current day in the colony.
     * @param currDay The new current day.
     */
    public void setCurrDay(int currDay) {
        this.currDay = currDay;
    }

    /**
     * Checks if the game is over for this colony.
     * @return True if game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets the game over state for this colony.
     * @param gameOver True if game is over, false otherwise.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
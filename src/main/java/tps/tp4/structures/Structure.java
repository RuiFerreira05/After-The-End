package tps.tp4.structures;

import java.io.Serializable;

import tps.tp4.Colony;
import tps.tp4.Interfaces.Impactable;

/**
 * Abstract base class for all structures in the colony.
 * Handles resource costs, production, and impact on the colony.
 */
public abstract class Structure implements Serializable, Impactable {
    
    protected String name;
    protected int[] cost;
    protected int[] production;
    protected String description;

    /**
     * Constructs a structure with the specified name, cost, and production values.
     * @param name The name of the structure.
     * @param cost The resource cost array.
     * @param entertainment Entertainment production.
     * @param woodProduction Wood production.
     * @param foodProduction Food production.
     * @param stoneProduction Stone production.
     * @param metalProduction Metal production.
     */
    public Structure(String name, int[] cost, int entertainment, int woodProduction, int foodProduction, int stoneProduction, int metalProduction) {
        this.name = name;
        this.cost = cost;
        this.production = new int[]{
            woodProduction, 
            foodProduction, 
            stoneProduction, 
            metalProduction,
            entertainment
        };
        this.description = null;
    }

    /**
     * Constructs a structure with the specified name and cost, with zero production.
     * @param name The name of the structure.
     * @param cost The resource cost array.
     */
    public Structure(String name, int[] cost) {
        this(name, cost, 0, 0, 0, 0, 0);
    }

    /**
     * Applies the structure's production impact to the given colony.
     * @param colony The colony to impact.
     */
    @Override
    public void impact(Colony colony) {
        colony.setWoodProduction(production[0] + colony.getWoodProduction());
        colony.setFoodProduction(production[1] + colony.getFoodProduction());
        colony.setStoneProduction(production[2] + colony.getStoneProduction());
        colony.setMetalProduction(production[3] + colony.getMetalProduction());
        colony.setEntertainment(production[4] + colony.getEntertainment());
    }

    /**
     * Returns a string describing the structure's production and cost.
     * @param detailed Whether to include detailed cost and entertainment info.
     * @return The structure info string.
     */
    public String getStructureInfo(boolean detailed) {
        if (!detailed) {
            return name + " - Production /day: " + production[0] + " wood, " + production[1] + " food, " + production[2] + " stone, " + production[3] + " metal"
                    + "\n\n" + this.description + "\n";
        } else {
            return name + " - Cost: " + cost[0] + " wood, " + cost[1] + " stone, " + cost[2] + " metal" +
                    "\nProduction /day: " + production[0] + " wood, " + production[1] + " food, " + production[2] + " stone, " + production[3] + " metal" +
                    "\nEntertainment: " + production[4] + "\n\n" + this.description + "\n";
        }
    }

    /**
     * Gets the name of the structure.
     * @return The structure name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the resource cost array for the structure.
     * @return The cost array.
     */
    public int[] getCost() {
        return cost;
    }
    
    /**
     * Gets the wood production value.
     * @return The wood production.
     */
    public int getWoodProduction() {
        return production[0];
    }
    
    /**
     * Gets the food production value.
     * @return The food production.
     */
    public int getFoodProduction() {
        return production[1];
    }
    
    /**
     * Gets the stone production value.
     * @return The stone production.
     */
    public int getStoneProduction() {
        return production[2];
    }
    
    /**
     * Gets the metal production value.
     * @return The metal production.
     */
    public int getMetalProduction() {
        return production[3];
    }
    
    /**
     * Gets the entertainment production value.
     * @return The entertainment production.
     */
    public int getEntertainment() {
        return production[4];
    }

    /**
     * Gets the description of the structure.
     * @return The description string.
     */
    public String getDescription() {
        return description;
    }
}
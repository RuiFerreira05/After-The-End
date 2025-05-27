package tps.tp4.structures;

import java.io.Serializable;

import tps.tp4.Colony;
import tps.tp4.Interfaces.Impactable;

public abstract class Structure implements Serializable, Impactable {
    
    protected String name;
    protected int[] cost;
    protected int[] production;
    protected String description;

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

    public Structure(String name, int[] cost) {
        this(name, cost, 0, 0, 0, 0, 0);
    }

    @Override
    public void impact(Colony colony) {
        colony.setWoodProduction(production[0] + colony.getWoodProduction());
        colony.setFoodProduction(production[1] + colony.getFoodProduction());
        colony.setStoneProduction(production[2] + colony.getStoneProduction());
        colony.setMetalProduction(production[3] + colony.getMetalProduction());
        colony.setEntertainment(production[4] + colony.getEntertainment());
    }

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

    public String getName() {
        return name;
    }

    public int[] getCost() {
        return cost;
    }
    
    public int getWoodProduction() {
        return production[0];
    }
    
    public int getFoodProduction() {
        return production[1];
    }
    
    public int getStoneProduction() {
        return production[2];
    }
    
    public int getMetalProduction() {
        return production[3];
    }
    
    public int getEntertainment() {
        return production[4];
    }

    public String getDescription() {
        return description;
    }
}
package tps.tp4.structures;

import tps.tp4.Colony;

public abstract class Structure {
    
    protected String name;
    protected int[] cost;
    protected int entertainment;
    protected int woodProduction;
    protected int foodProduction;
    protected int stoneProduction;
    protected int metalProduction;
    protected String description;

    public Structure(String name, int[] cost, int entertainment, int woodProduction, int foodProduction, int stoneProduction, int metalProduction) {
        this.name = name;
        this.cost = cost;
        this.woodProduction = woodProduction;
        this.foodProduction = foodProduction;
        this.stoneProduction = stoneProduction;
        this.metalProduction = metalProduction;
        this.entertainment = entertainment;
        this.description = null;
    }

    public Structure(String name, int[] cost) {
        this(name, cost, 0, 0, 0, 0, 0);
    }

    public void impact(Colony colony) {
        colony.setWoodProduction(woodProduction + colony.getWoodProduction());
        colony.setFoodProduction(foodProduction + colony.getFoodProduction());
        colony.setStoneProduction(stoneProduction + colony.getStoneProduction());
        colony.setMetalProduction(metalProduction + colony.getMetalProduction());
        colony.setEntertainment(entertainment + colony.getEntertainment());
    }

    public String getStructureInfo(boolean detailed) {
        if (!detailed) {
            return name + " - Production /day: " + this.woodProduction + " wood, " + this.foodProduction + " food, " + this.stoneProduction + " stone, " + this.metalProduction + " metal"
                    + "\n\n" + this.description + "\n";
        } else {
            return name + " - Cost: " + cost[0] + " wood, " + cost[1] + " stone, " + cost[2] + " metal" +
                    "\nProduction /day: " + this.woodProduction + " wood, " + this.foodProduction + " food, " + this.stoneProduction + " stone, " + this.metalProduction + " metal" +
                    "\nEntertainment: " + this.entertainment + "\n\n" + this.description + "\n";
        }
    }

    public String getName() {
        return name;
    }

    public int[] getCost() {
        return cost;
    }

    public int getEntertainment() {
        return entertainment;
    }

    public int getWoodProduction() {
        return woodProduction;
    }

    public int getFoodProduction() {
        return foodProduction;
    }

    public int getStoneProduction() {
        return stoneProduction;
    }

    public int getMetalProduction() {
        return metalProduction;
    }

    public String getDescription() {
        return description;
    }
}
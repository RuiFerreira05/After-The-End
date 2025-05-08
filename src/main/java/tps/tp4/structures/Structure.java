package tps.tp4.structures;

import tps.tp4.Colony;

public abstract class Structure {
    
    private String name;
    private int[] cost;
    private double entertainment;
    private double woodProduction;
    private double foodProduction;
    private double stoneProduction;
    private double metalProduction;

    public Structure(String name, int[] cost, double entertainment, double woodProduction, double foodProduction, double stoneProduction, double metalProduction) {
        this.name = name;
        this.cost = cost;
        this.entertainment = entertainment;
        this.woodProduction = woodProduction;
        this.foodProduction = foodProduction;
        this.stoneProduction = stoneProduction;
        this.metalProduction = metalProduction;
    }

    public Structure(String name, int[] cost) {
        this(name, cost, 0, 0, 0, 0, 0);
    }

    public abstract void impact(Colony colony);

    public String getName() {
        return name;
    }

    public int[] getCost() {
        return cost;
    }

    public double getEntertainment() {
        return entertainment;
    }

    public double getFoodProduction() {
        return foodProduction;
    }

    public double getMetalProduction() {
        return metalProduction;
    }

    public double getStoneProduction() {
        return stoneProduction;
    }

    public double getWoodProduction() {
        return woodProduction;
    }

    public String getStructureInfo(boolean detailed) {
        if (!detailed) {
            return name + " - Production /day: " + getWoodProduction() + " wood, " + getFoodProduction() + " food, " + getStoneProduction() + " stone, " + getMetalProduction() + " metal";
        } else {
            return name + " - Cost: " + cost[0] + " wood, " + cost[1] + " stone, " + cost[2] + " metal" +
                    "\nProduction /day: " + getWoodProduction() + " wood, " + getFoodProduction() + " food, " + getStoneProduction() + " stone, " + getMetalProduction() + " metal" +
                    "\nEntertainment: " + getEntertainment();
        }
    }

}
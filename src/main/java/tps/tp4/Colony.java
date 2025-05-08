package tps.tp4;

import java.util.ArrayList;
import java.util.List;

import tps.tp4.structures.Structure;
import tps.tp4.errors.NotEnoughResourcesException;
import tps.tp4.errors.PopulationLimitException;
import tps.tp4.settlers.Settler;

public class Colony {

    private static final int MAX_INITIAL_POPULATION = 3;
    private static final int MAX_INITIAL_WOOD = 500;
    private static final int MAX_INITIAL_FOOD = 500;
    private static final int MAX_INITIAL_STONE = 500;
    private static final int MAX_INITIAL_METAL = 500;
    private static final int MAX_INITIAL_ENTERTAINMENT = 0;

    private App app;
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
    private int[] resources;
    private int population;
    private int maxPopulation;
    private double overAllHappiness;
    private List<Settler> settlers;
    private List<Structure> structures;
    private int currDay;

    public Colony(String colonyName, App app) {
        this.app = app;
        this.settlers = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.colonyName = colonyName;
        this.wood = MAX_INITIAL_WOOD;
        this.food = MAX_INITIAL_FOOD;
        this.stone = MAX_INITIAL_STONE;
        this.metal = MAX_INITIAL_METAL;
        this.entertainment = MAX_INITIAL_ENTERTAINMENT;
        this.woodProduction = 0;
        this.foodProduction = 0;
        this.stoneProduction = 0;
        this.metalProduction = 0;
        this.resources = new int[] {wood, food, stone, metal};
        this.maxPopulation = MAX_INITIAL_POPULATION;
        this.population = settlers.size();
        updateOverallHappiness();
        this.currDay = 1;
    }

    public double updateOverallHappiness() {
        double happiness = 0;
        for (Settler settler : settlers) {
            happiness += settler.getHappiness();
        }
        this.overAllHappiness = happiness / population;
        return overAllHappiness;
    }

    public void nextDay() {
        this.wood += woodProduction;
        this.food += foodProduction;
        this.stone += stoneProduction;
        this.metal += metalProduction;
        runEvent();
        currDay++;
    }

    private void runEvent() {
        // TODO
    }

    public void addResources(int[] resources) {
        this.wood += resources[0];
        this.food += resources[1];
        this.stone += resources[2];
        this.metal += resources[3];
    }

    public void addSettler(Settler settler) throws PopulationLimitException {
        if (population < maxPopulation) {
            settlers.add(settler);
            population++;
            updateOverallHappiness();
        } else {
            PopulationLimitException e = new PopulationLimitException();
            app.getLogger().error(e.getMessage(), e);
            throw e;
        }
    }

    public void removeSettler(Settler settler) {
        settlers.remove(settler);
        population--;
        updateOverallHappiness();
    }

    public void addStructure(Structure structure) throws NotEnoughResourcesException {
        if (checkCost(structure)) {
            structures.add(structure);
            wood -= structure.getCost()[0];
            stone -= structure.getCost()[1];
            metal -= structure.getCost()[2];

            structure.impact(this);
        } else {
            NotEnoughResourcesException e = new NotEnoughResourcesException(structure);
            app.getLogger().error(e.getMessage(), e);
            throw e;
        }
    }

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

    public int getCurrDay() {
        return currDay;
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

    public String getColonyName() {
        return colonyName;
    }

    public void setColonyName(String colonyName) {
        this.colonyName = colonyName;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getStone() {
        return stone;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(int entertainment) {
        this.entertainment = entertainment;
    }

    public int[] getResources() {
        return resources;
    }

    public int getPopulation() {
        return population;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public double getOverAllHappiness() {
        return overAllHappiness;
    }

    public List<Settler> getSettlers() {
        return settlers;
    }

    public List<Structure> getStructures() {
        return structures;
    }
}
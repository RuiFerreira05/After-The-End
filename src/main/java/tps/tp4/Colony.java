package tps.tp4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tps.tp4.structures.Structure;
import tps.tp4.errors.NotEnoughResourcesException;
import tps.tp4.errors.PopulationLimitException;
import tps.tp4.settings.Settings;
import tps.tp4.settlers.Settler;

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
            throw new PopulationLimitException();
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
            throw new NotEnoughResourcesException(structure);
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

    public void setWoodProduction(int woodProduction) {
        this.woodProduction = woodProduction;
    }

    public int getFoodProduction() {
        return foodProduction;
    }

    public void setFoodProduction(int foodProduction) {
        this.foodProduction = foodProduction;
    }

    public int getStoneProduction() {
        return stoneProduction;
    }

    public void setStoneProduction(int stoneProduction) {
        this.stoneProduction = stoneProduction;
    }

    public int getMetalProduction() {
        return metalProduction;
    }

    public void setMetalProduction(int metalProduction) {
        this.metalProduction = metalProduction;
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

    public int getPopulation() {
        return population;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public int getWarriors() {
        return warriors;
    }

    public void setWarriors(int warriors) {
        this.warriors = warriors;
    }

    public int getMaxWarriors() {
        return maxWarriors;
    }

    public void setMaxWarriors(int maxWarriors) {
        this.maxWarriors = maxWarriors;
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

    public void setCurrDay(int currDay) {
        this.currDay = currDay;
    }
}
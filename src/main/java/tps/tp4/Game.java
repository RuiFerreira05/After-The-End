package tps.tp4;

import java.util.ArrayList;
import java.util.List;

import tps.tp4.Errors.PopulationLimitException;
import tps.tp4.Errors.NotEnoughResourcesException;

public class Game {

    private App app;
    private String colonyName;
    private int wood;
    private int food;
    private int stone;
    private int metal;
    private int[] resources;
    private int population;
    private int maxPopulation;
    private double overAllHappiness;
    private List<Settler> settlers;
    private List<Building> buildings;

    public Game(String colonyName, List<Settler> initial_Settlers, App app) {~
        this.app = app;
        this.settlers = new ArrayList<>(settlers);
        this.buildings = new ArrayList<>();
        this.colonyName = colonyName;
        this.wood = 0;
        this.food = 0;
        this.stone = 0;
        this.metal = 0;
        this.resources = new int[] {wood, food, stone, metal};
        this.maxPopulation = 10;
        this.population = settlers.size();
        updateOverallHappiness();
    }

    public double updateOverallHappiness() {
        double happiness = 0.0;
        for (Settler settler : settlers) {
            happiness += settler.updateHappiness();
        }
        this.overAllHappiness = (happiness / population);
        return overAllHappiness;
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

    public void addBuilding(Building building) throws NotEnoughResourcesException {
        if (checkCost(building)) {
            buildings.add(building);
            wood -= building.getCost()[0];
            food -= building.getCost()[1];
            stone -= building.getCost()[2];
            metal -= building.getCost()[3];
        } else {
            NotEnoughResourcesException e = new NotEnoughResourcesException(building);
            app.getLogger().error(e.getMessage(), e);
            throw e;
        }
    }

    private boolean checkCost(Building building) {
        if (building.getCost()[0] > wood) {
            return false;
        } else if (building.getCost()[1] > food) {
            return false;
        } else if (building.getCost()[2] > stone) {
            return false;
        } else if (building.getCost()[3] > metal) {
            return false;
        }
        return true;
    }

    // ----------------- Getters and Setters ----------------- //
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

    public List<Building> getBuildings() {
        return buildings;
    }
}
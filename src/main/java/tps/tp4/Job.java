package tps.tp4;

import java.util.ArrayList;
import java.util.List;

import tps.tp4.buildings.Building;
import tps.tp4.errors.MaxHabitantLimitException;
import tps.tp4.settlers.Settler;

public class Job {

    private String name;
    private int maxPositions;
    private List<Building> buildings;
    private List<Settler> settlers;

    public Job(String name) {
        this.name = name;
        this.maxPositions = 0;
        this.buildings = new ArrayList<Building>();
        this.settlers = new ArrayList<Settler>();
    }

    public String getName() {
        return name;
    }

    public int getMaxPositions() {
        return maxPositions;
    }

    public void setMaxPositions(int maxPositions) {
        this.maxPositions = maxPositions;
    }

    public void addPositions(int positions) {
        this.maxPositions += positions;
    }

    public void removePositions(int positions) {
        this.maxPositions -= positions;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public void removeBuilding(Building building) {
        this.buildings.remove(building);
    }

    public List<Settler> getSettlers() {
        return settlers;
    }

    public void addSettler(Settler settler) throws MaxHabitantLimitException {
        if (this.settlers.size() < this.maxPositions) {
            this.settlers.add(settler);
        } else {
            throw new MaxHabitantLimitException();
        }
    }

    public void removeSettler(Settler settler) {
        this.settlers.remove(settler);
    }

}

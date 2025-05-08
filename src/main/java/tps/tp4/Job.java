package tps.tp4;

import java.util.ArrayList;
import java.util.List;

import tps.tp4.structures.Structure;
import tps.tp4.errors.MaxHabitantLimitException;
import tps.tp4.settlers.Settler;

public class Job {

    private String name;
    private int maxPositions;
    private List<Structure> structures;
    private List<Settler> settlers;

    public Job(String name) {
        this.name = name;
        this.maxPositions = 0;
        this.structures = new ArrayList<Structure>();
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

    public List<Structure> getStructures() {
        return structures;
    }

    public void addStructure(Structure structure) {
        this.structures.add(structure);
    }

    public void removeStructure(Structure structure) {
        this.structures.remove(structure);
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

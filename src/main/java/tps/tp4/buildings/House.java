package tps.tp4.buildings;

import java.util.List;

import tps.tp4.errors.MaxHabitantLimitException;
import tps.tp4.settlers.Settler;

public class House extends Building {
    private static final int[] COST = {100, 10, 0}; // wood, stone, metal
    private static final double ENTERTAINMENT = 1;
    private static final double WOOD_PRODUCTION = 0;
    private static final double FOOD_PRODUCTION = 0;
    private static final double STONE_PRODUCTION = 0;
    private static final double METAL_PRODUCTION = 0;

    private List<Settler> habitants;
    private int maxHabitants = 3;

    public House() {
        super("house", COST, ENTERTAINMENT, WOOD_PRODUCTION, FOOD_PRODUCTION, STONE_PRODUCTION, METAL_PRODUCTION);
    }

    public void addHabitant(Settler settler) throws MaxHabitantLimitException {
        if (habitants.size() < maxHabitants) {
            habitants.add(settler);
        } else {
            throw new MaxHabitantLimitException();
        }
    }

    public void removeHabitant(Settler settler) {
        habitants.remove(settler);
    }

    public List<Settler> getHabitants() {
        return habitants;
    }

    public int getMaxHabitants() {
        return maxHabitants;
    }

    public void setMaxHabitants(int maxHabitants) {
        this.maxHabitants = maxHabitants;
    }
}

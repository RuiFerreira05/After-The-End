package tps.tp4.structures;

import tps.tp4.Colony;

public class House extends Structure {
    private static final int[] COST = {100, 10, 0}; // wood, stone, metal

    private int maxHabitants = 3;

    public House() {
        super("house", COST);
    }

    @Override
    public void impact(Colony colony) {
        colony.setMaxPopulation(maxHabitants + colony.getMaxPopulation());
    }

    @Override
    public String getStructureInfo(boolean detailed) {
        StringBuilder sb = new StringBuilder(super.getStructureInfo(detailed));
        sb.append("\n\nIncreases max population by ").append(maxHabitants).append(" settlers.\n");
        return sb.toString();
    }
}

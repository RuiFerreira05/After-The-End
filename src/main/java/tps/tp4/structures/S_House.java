package tps.tp4.structures;

import tps.tp4.Colony;
import tps.tp4.settings.Settings;

public class S_House extends Structure {
    private static final int[] COST = Settings.HOUSE_COST; // wood, stone, metal

    private int maxHabitants = Settings.HOUSE_POPULATION_INCREASE;

    public S_House() {
        super("house", COST);
        this.description = "Increases max population by " + maxHabitants + " colonists.";
    }

    @Override
    public void impact(Colony colony) {
        colony.setMaxPopulation(maxHabitants + colony.getMaxPopulation());
    }
}

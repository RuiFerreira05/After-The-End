package tps.tp4.structures;

import tps.tp4.Settings;

public class S_woodCuttersLodge extends Structure {
    private static final int[] COST = Settings.WOODCUTTER_LODGE_COST; // wood, stone, metal

    public S_woodCuttersLodge() {
        super("woodcutters_lodge", COST);
        this.production = Settings.WOODCUTTER_LODGE_PRODUCTION;
        this.description = "Produces " + getWoodProduction() + " wood per day.";
    }
}

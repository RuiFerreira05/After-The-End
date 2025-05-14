package tps.tp4.structures;

import tps.tp4.Settings;

public class Farm extends Structure {
    
    private static final int[] COST = Settings.FARM_COST; // wood, stone, metal

    public Farm() {
        super("farm", COST);
        this.foodProduction = Settings.FARM_PRODUCTION[1];
        this.description = "Produces " + foodProduction + " food per day.";
    }
}

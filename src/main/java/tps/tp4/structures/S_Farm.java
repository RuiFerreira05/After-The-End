package tps.tp4.structures;

import tps.tp4.Settings;

public class S_Farm extends Structure {
    
    private static final int[] COST = Settings.FARM_COST; // wood, stone, metal

    public S_Farm() {
        super("farm", COST);
        this.production = Settings.FARM_PRODUCTION;
        this.description = "Produces " + getFoodProduction() + " food per day.";
    }
}

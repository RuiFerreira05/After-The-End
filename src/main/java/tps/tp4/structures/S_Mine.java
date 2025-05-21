package tps.tp4.structures;

import tps.tp4.settings.Settings;

public class S_Mine extends Structure {
    private static final int[] COST = Settings.MINE_COST; // wood, stone, metal

    public S_Mine() {
        super("mine", COST);
        this.production = Settings.MINE_PRODUCTION;
        this.description = "Produces " + getStoneProduction() + " stone per day.";
    }
    
}

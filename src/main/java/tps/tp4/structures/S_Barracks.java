package tps.tp4.structures;

import tps.tp4.Colony;
import tps.tp4.Settings;

public class S_Barracks extends Structure {
    private static final int[] COST = Settings.BARRACKS_COST; // wood, stone, metal
    private static final int MAX_WARRIORS_INCREASE = Settings.BARRACKS_WARRIORS_INCREASE;

    public S_Barracks() {
        super("barracks", COST);
        this.production = Settings.BARRACKS_PRODUCTION;
        this.description = "The Barracks adds " + MAX_WARRIORS_INCREASE + " beds for warriors to sleep";
    }

    @Override
    public void impact(Colony colony) {
        colony.setMaxWarriors(colony.getMaxWarriors() + MAX_WARRIORS_INCREASE);
    }
    
}

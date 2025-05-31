package tps.tp4.structures;

import tps.tp4.Colony;
import tps.tp4.Settings;

public class S_Hospital extends Structure {
    private static final int[] COST = Settings.HOSPITAL_COST; // wood, stone, metal

    public S_Hospital() {
        super("hospital", COST);
        this.description = "A place to heal injured colonists.";
    }

    @Override
    public void impact(Colony colony) {
        super.impact(colony);
        //TODO
    }
}

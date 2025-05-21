package tps.tp4.structures;
import tps.tp4.settings.Settings;

public class S_Factory extends Structure {
    
    private static final int[] COST = Settings.FACTORY_COST; // wood, stone, metal

    public S_Factory() {
        super("factory", COST);
        this.production = Settings.FACTORY_PRODUCTION;
        this.description = "Produces " + getMetalProduction() + " metal per day.";
    }
}
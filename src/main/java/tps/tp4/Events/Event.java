package tps.tp4.Events;

import java.util.ArrayList;
import java.util.List;

import tps.tp4.Colony;
import tps.tp4.Interfaces.ImpactableChoice;

public abstract class Event implements ImpactableChoice {
    
    protected String name;
    protected String description;
    protected List<String> options = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getOptions() {
        return options.toArray(new String[0]);
    }

    @Override
    public String impact(Colony colony, int choice) {
        return "";
    }
}

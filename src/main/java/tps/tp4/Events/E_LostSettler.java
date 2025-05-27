package tps.tp4.Events;

import tps.tp4.Colony;
import tps.tp4.Names;
import tps.tp4.errors.PopulationLimitException;
import tps.tp4.settlers.Settler;

public class E_LostSettler extends Event {
    
    public E_LostSettler() {
        this.name = "Lost Settler";
        this.description = "A lost wanderer has stumbled into the colony, do you want to help them?";
        options.add("Help the lost settler");
        options.add("Ignore the lost settler");
    }

    @Override
    public String impact(Colony colony, int choice) {
        if (choice == 0) {
            try {
                colony.addSettler(createRandomSettler(colony));
                return "You helped the lost settler, they have joined your colony.";
            } catch (PopulationLimitException e) {
                // If the population limit is reached, we simply don't add the settler
                return "You helped the lost settler, but your colony is at maximum capacity. They cannot join.";
            }
        } else {
            return "You ignored the lost settler, they wandered off into the wilderness.";
        }
    }

    private Settler createRandomSettler(Colony colony) {
        this.name = Names.pickRandom();
        Settler settler = new Settler(name, colony);
        settler.setAge(18 + (int) (Math.random() * 40)); // Random age between 18 and 58
        return settler;
    }
}

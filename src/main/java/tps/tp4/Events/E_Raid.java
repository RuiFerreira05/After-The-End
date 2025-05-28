package tps.tp4.Events;

import tps.tp4.Colony;
import tps.tp4.settlers.Settler;

/**
 * Event representing a raid on the colony by hostile forces.
 */
public class E_Raid extends Event {

    /**
     * Constructs the Raid event with its options and description.
     */
    public E_Raid() {
        this.name = "Raid";
        this.description = "A group of hostile raiders has attacked your colony, stealing resources and causing chaos. You must decide how to respond.";
        options.add("Defend the colony and fight back against the raiders.");
        options.add("Negotiate with the raiders to minimize damage.");
        options.add("Surrender.");
    }

    /**
     * Applies the impact of the raid event based on the player's choice.
     * @param colony The colony affected.
     * @param choice The player's choice index.
     * @return A string describing the outcome.
     */
    @Override
    public String impact(Colony colony, int choice) {
        switch (choice) {
            case 0:
                // Defend the colony
                if (colony.getWarriors() < (int) Math.random() * 3) {
                    colony.setWood(colony.getWood() - ((int) Math.random() * 40 + 10));
                    colony.setStone(colony.getStone() - ((int) Math.random() * 40 + 10));
                    colony.setMetal(colony.getMetal() - ((int) Math.random() * 40 + 10));
                    for (Settler settler : colony.getSettlers()) {
                        settler.setHealth(settler.getHealth() - ((int) Math.random() * 20 + 5));
                    }
                    return "You fought bravely, but the raiders were too strong. You lost some resources in the battle.";
                } else {
                    return "You successfully defended the colony, the raiders were driven off!";
                }
            case 1:
                if (Math.random() < 0.5) {
                    colony.setWood(colony.getWood() - ((int) Math.random() * 20 + 5));
                    colony.setStone(colony.getStone() - ((int) Math.random() * 20 + 5));
                    colony.setMetal(colony.getMetal() - ((int) Math.random() * 20 + 5));
                    return "You negotiated with the raiders, they took some resources but left without causing further damage.";
                } else {
                    // Negotiation failed
                    colony.setWood(colony.getWood() - ((int) Math.random() * 40 + 5));
                    colony.setStone(colony.getStone() - ((int) Math.random() * 40 + 5));
                    colony.setMetal(colony.getMetal() - ((int) Math.random() * 40 + 5));
                    return "You attempted to negotiate, but the raiders were not interested. They took more resources than expected.";
                }
            case 2:
                colony.setWood(colony.getWood() - ((int) Math.random() * 50 + 20));
                colony.setStone(colony.getStone() - ((int) Math.random() * 50 + 20));
                colony.setMetal(colony.getMetal() - ((int) Math.random() * 50 + 20));
                for (Settler settler : colony.getSettlers()) {
                    settler.setHealth(settler.getHealth() - ((int) Math.random() * 30 + 10));
                }
                return "You surrendered to the raiders. They took a significant amount of resources and harmed your settlers.";
            default:
                throw new IllegalArgumentException("Invalid choice for raid event.");
        }
    }
    
}

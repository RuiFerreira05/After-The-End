package tps.tp4.Interfaces;

import tps.tp4.Colony;

/**
 * Interface for classes that can impact a colony production.
 * Mainly used for structures.
 */
public interface Impactable {
    
    /**
     * Method that affects production in the colony.
     * @param colony
     */
    public void impact(Colony colony);

}

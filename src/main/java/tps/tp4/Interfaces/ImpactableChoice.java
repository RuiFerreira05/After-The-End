package tps.tp4.Interfaces;

import tps.tp4.Colony;

/**
 * Interface for classes that can impact a colony production based on a choice.
 * Mainly used for Events
 */
public interface ImpactableChoice{

    /**
     * Method that affects production in the colony based on a choice.
     * @param colony 
     * @param choice choice made
     * @return String message representing the consequence
     */
    public String impact(Colony colony, int choice);
    
}

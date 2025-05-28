package tps.tp4.Events;

/**
 * Enum representing the different types of events that can occur in the game.
 */
public enum EventTypes {

    NOEVENT,
    LOSTSETTLER,
    RAID;

    /**
     * Picks a random event type from the available types.
     * @return A randomly selected EventTypes value.
     */
    public static EventTypes pickRandom() {
        EventTypes[] values = EventTypes.values();
        int randomIndex = (int) (Math.random() * values.length);
        return values[randomIndex];
    }
}

package tps.tp4.Events;

/**
 * Factory class for creating Event instances based on EventTypes.
 */
public class EventFactory {
    
    /**
     * Creates a new Event instance of the specified type.
     * @param type The type of event to create.
     * @return The created Event, or null for NOEVENT.
     * @throws IllegalArgumentException If the type is unknown.
     */
    public static Event createEvent(EventTypes type) {
        switch (type) {
            case NOEVENT:
                return null;
            case LOSTSETTLER:
                return new E_LostSettler();
            case RAID:
                return new E_Raid();
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }

}

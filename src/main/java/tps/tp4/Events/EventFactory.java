package tps.tp4.Events;

public class EventFactory {
    
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

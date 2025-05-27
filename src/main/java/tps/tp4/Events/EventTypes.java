package tps.tp4.Events;

public enum EventTypes {

    NOEVENT,
    LOSTSETTLER,
    RAID;

    public static EventTypes pickRandom() {
        EventTypes[] values = EventTypes.values();
        int randomIndex = (int) (Math.random() * values.length);
        return values[randomIndex];
    }
}

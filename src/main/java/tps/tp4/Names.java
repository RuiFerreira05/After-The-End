package tps.tp4;

public enum Names {
    John,
    Jane,
    Alice,
    Bob,
    Charlie,
    David,
    Eve,
    Frank,
    Grace,
    Hannah,
    Ian,
    Jack,
    Karen,
    Liam,
    Mia,
    Noah,
    Olivia,
    Paul,
    Quinn,
    Rachel,
    Sam,
    Tom,
    Uma,
    Victor,
    Wendy,
    Xavier,
    Yara;

    public static String pickRandom() {
        Names[] values = Names.values();
        int randomIndex = (int) (Math.random() * values.length);
        return values[randomIndex].toString();
    }
}

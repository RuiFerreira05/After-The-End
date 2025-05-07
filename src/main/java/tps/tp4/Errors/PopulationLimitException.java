package tps.tp4.errors;

public class PopulationLimitException extends Exception {
    public PopulationLimitException() {
        super("Population limit reached. Cannot add more settlers.");
    }

    public PopulationLimitException(String message) {
        super(message);
    }

    public PopulationLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public PopulationLimitException(Throwable cause) {
        super(cause);
    }

}

package tps.tp4.errors;

public class MaxHabitantLimitException extends Exception {
    public MaxHabitantLimitException() {
        super("Maximum number of habitants reached.");
    }

    public MaxHabitantLimitException(String message) {
        super(message);
    }

    public MaxHabitantLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxHabitantLimitException(Throwable cause) {
        super(cause);
    }

}

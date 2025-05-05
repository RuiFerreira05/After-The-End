package tps.tp4.Errors;

public class InvalidAppStateException extends Exception {
    public InvalidAppStateException(int state) {
        super("Invalid application state: " + state);
    }
    public InvalidAppStateException(int state, Throwable cause) {
        super("Invalid app state" + state, cause);
    }
    public InvalidAppStateException(Throwable cause) {
        super(cause);
    }
    public InvalidAppStateException(String message) {
        super(message);
    }
    public InvalidAppStateException(String message, Throwable cause) {
        super(message, cause);
    }
}

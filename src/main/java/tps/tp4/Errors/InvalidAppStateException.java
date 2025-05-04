package tps.tp4.Errors;

public class InvalidAppStateException extends Exception {
    public InvalidAppStateException(String message) {
        super(message);
    }
    public InvalidAppStateException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidAppStateException(Throwable cause) {
        super(cause);
    }
}

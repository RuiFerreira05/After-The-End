package tps.tp4.errors;

import tps.tp4.structures.Structure;

public class NotEnoughResourcesException extends Exception {
    public NotEnoughResourcesException(Structure structure) {
        super("Not enough resources to build " + structure.getName() + ". Required: " + structure.getCost());
    }

    public NotEnoughResourcesException(String message) {
        super(message);
    }

    public NotEnoughResourcesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughResourcesException(Throwable cause) {
        super(cause);
    }

}

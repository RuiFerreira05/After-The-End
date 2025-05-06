package tps.tp4.Errors;

import tps.tp4.Building;

public class NotEnoughResourcesException extends Exception {
    public NotEnoughResourcesException(Building building) {
        super("Not enough resources to build " + building.getName() + ". Required: " + building.getCost());
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

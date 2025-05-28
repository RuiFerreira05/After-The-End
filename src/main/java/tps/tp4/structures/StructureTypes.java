package tps.tp4.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum representing the different types of structures available in the game.
 */
public enum StructureTypes {
    HOUSE,
    FARM,
    FACTORY,
    HOSPITAL,
    WOODCUTTER_LODGE,
    MINE,
    BARRACKS;

    /**
     * Returns a list of all structure types.
     * @return ArrayList of StructureTypes.
     */
    public static ArrayList<StructureTypes> listTypes() {
        return new ArrayList<>(List.of(values()));
    }

    /**
     * Returns a list of all structure type names as strings.
     * @return ArrayList of structure type names.
     */
    public static ArrayList<String> listNames() {
        return new ArrayList<>(List.of(values()).stream().map(StructureTypes::name).toList());
    }
}

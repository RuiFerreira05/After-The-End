package tps.tp4.structures;

import java.util.ArrayList;
import java.util.List;

public enum StructureTypes {
    HOUSE,
    FARM,
    FACTORY,
    HOSPITAL,
    WOODCUTTER_LODGE,
    MINE,
    BARRACKS;

    public static ArrayList<StructureTypes> listTypes() {
        return new ArrayList<>(List.of(values()));
    }

    public static ArrayList<String> listNames() {
        return new ArrayList<>(List.of(values()).stream().map(StructureTypes::name).toList());
    }
}

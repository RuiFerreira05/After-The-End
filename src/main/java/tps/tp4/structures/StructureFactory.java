package tps.tp4.structures;

public class StructureFactory {
    
    public static Structure createStructure(StructureTypes type) {
        switch (type) {
            case HOUSE:
                return new House();
            // case FARM:
            //     return new Farm();
            // case FACTORY:
            //     return new Factory();
            // case HOSPITAL:
            //     return new Hospital();
            // case WOODCUTTER_LODGE:
            //     return new WoodcutterLodge();
            // case MINE:
            //     return new Mine();
            // case BARRACKS:
            //     return new Barracks();
            default:
                throw new IllegalArgumentException("Unknown structure type: " + type);
        }
    }

}

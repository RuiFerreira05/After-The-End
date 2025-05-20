package tps.tp4.structures;

public class StructureFactory {
    
    public static Structure createStructure(StructureTypes type) throws IllegalArgumentException {
        switch (type) {
            case HOUSE:
                return new S_House();
            case FARM:
                return new S_Farm();
            case FACTORY:
                return new S_Factory();
            case HOSPITAL:
                return new S_Hospital();
            case WOODCUTTER_LODGE:
                return new S_WoodCuttersLodge();
            case MINE:
                return new S_Mine();
            case BARRACKS:
                return new S_Barracks();
            default:
                throw new IllegalArgumentException("Unknown structure type: " + type);
        }
    }

}

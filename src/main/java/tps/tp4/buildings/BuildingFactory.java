package tps.tp4.buildings;

public class BuildingFactory {
    
    public static Building createBuilding(BuildingTypes type) {
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
                throw new IllegalArgumentException("Unknown building type: " + type);
        }
    }

}

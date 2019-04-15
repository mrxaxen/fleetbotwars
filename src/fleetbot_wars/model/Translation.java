package fleetbot_wars.model;

import java.util.HashMap;

//Separate thread
class Translation {

    private static Translation instance;

    static Translation getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return instance = new Translation();
        }
    }

    void getMap() {
        //Get Ground array from engine
        try {
            //send request for map
            //read map from stream
        } catch (Exception e) {
        }

    }

    void getResources() {

    }

    void getUnits() {

    }

    void createUnit(UnitType unitToCreate, Tile unitToCreateWith, Tile unitCreationPoint) {
        //Send createUnit command to engine (unitType,x,y)
        //Units/buildings
    }

    void checkResources() {

    }

}

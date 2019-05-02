package fleetbot_wars.model;

import fleetbot_wars.Main;
import visual.ground.Ground;
import visual.unit.Controllable;
import visual.unit.Unit;

import java.awt.*;


//Separate thread
class Translation {

    private static Translation instance;
    private Engine engine = Main.getEngine();

    static Translation getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return instance = new Translation();
        }
    }

    Ground[][] getMap() {
        //Get Ground array from engine

//        try {
//            send request for map
//            read map from stream
//        } catch (Exception e) {
//        }
        Ground[][] engineGround = engine.getMap().getGround();
        System.out.println(engineGround == null);

        return engine.getMap().getGround();
    }

    void move(Point unitAt, Point moveTo) {
        Unit unit = engine.getMap().groundAt(unitAt).getOwnerReference();
        if(unit instanceof Controllable)
            engine.startMove((Controllable) unit, moveTo);
//        GameSpace.getInstance().move();
    }

    void getResources() {

    }

    void getUnits() {

    }

    void createUnit(UnitType unitToCreate, Tile unitToCreateWith, Tile unitCreationPoint) {
        //Send createUnit command to engine (unitType,x,y)
        //Units/buildingsc

    }

    void checkResources() {

    }

}
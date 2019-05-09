package fleetbot_wars.model;

import fleetbot_wars.Main;
import fleetbot_wars.model.enums.ResourceType;
import visual.ground.Ground;
import visual.unit.Controllable;
import visual.unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


//Separate thread
class Translation {

    private static Translation instance;
    private Engine engine = Main.getEngine();
    private StatusBar statusBar;

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

    void passStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    boolean move(Point unitAt, Point moveTo) {
        Controllable unitToMove = (Controllable) engine.getMap().groundAt(unitAt).getOwnerReference();
        return engine.startMove(unitToMove, moveTo);
    }

    void repaintOnMove(Point tileAt, Unit unitToPlace, boolean isGoingTo) {
        SwingUtilities.invokeLater(() -> GameSpace.getInstance().repaintTile(tileAt,unitToPlace,isGoingTo));
        System.out.println("CALLED");
    }

    void build() {

    }

    void createUnit(UnitType unitToCreate, Tile unitToCreateWith, Tile unitCreationPoint) {
        //Send createUnit command to engine (unitType,x,y)
        //Units/buildingsc

    }

    void updateResources(HashMap<ResourceType, Integer> resources) {
        SwingUtilities.invokeLater(() -> {
            statusBar.updateResources(resources);
        });
    }

    int getResource(ResourceType type) {
        return engine.getPlayers()[0].getResourceByName(type);
    }
}

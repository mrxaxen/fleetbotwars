package fleetbot_wars.model;

import fleetbot_wars.Main;
import fleetbot_wars.model.enums.ResourceType;
import fleetbot_wars.model.enums.VisualType;
import visual.ground.Ground;
import visual.unit.Controllable;
import visual.unit.Unit;

import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


//Separate thread
class Translation {

    private static Translation instance;
    private static final Color BLINK_WRONG_MOVE = new Color(255, 20, 20);
    private static final Color BLINK_ATTACK = new Color(110, 50, 220);
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

    void attack(Tile from, Tile to) {
        Unit unitAttacking;
        if((unitAttacking = engine.getMap().groundAt(new Point(from.getCoordX(),from.getCoordY())).getOwnerReference()) instanceof Controllable) {
            System.out.println("Is Controllable");
            if(engine.startAttack((Controllable) unitAttacking, new Point(to.getCoordX(),to.getCoordY()))) {
                blinkBorder(to,BLINK_ATTACK);
                return;
            }
        }
        blinkBorder(from,BLINK_WRONG_MOVE);
    }

    void repaint(Point tileAt, Unit unitToPlace, boolean isGoingTo) {
        SwingUtilities.invokeLater(() -> GameSpace.getInstance().repaintTile(tileAt,unitToPlace,isGoingTo));
    }

    void build(Tile buildersTile, Tile buildingPoint, VisualType building) {
        int xTo = buildingPoint.getCoordX();
        int yTo = buildingPoint.getCoordY();
        int xBuilder = buildersTile.getCoordX();
        int yBuilder = buildersTile.getCoordY();

        Controllable builder = (Controllable) engine.getMap().groundAt(new Point(xBuilder,yBuilder)).getOwnerReference();

        if(!engine.startBuild(builder,new Point(xTo,yTo),building)) {
            System.out.println("Building failed");
            blinkBorder(buildingPoint,BLINK_WRONG_MOVE);
        }
    }

    void stopBuild(Controllable builder) {
        engine.stopBuild(builder);
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

    private void blinkBorder(Tile tile, Color color) {
        tile.setBorder(new LineBorder(color,5,true));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                tile.setBorder(null);
            }
        },50);
    }
}

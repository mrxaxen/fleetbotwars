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
    private int currPlayer = 0;

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

    void changePlayer(int playerNum) {
        currPlayer = playerNum;
    }

    int getCurrPlayer() {
        return currPlayer;
    }

    boolean move(Point unitAt, Point moveTo) {
        Controllable unitToMove = (Controllable) engine.getMap().groundAt(unitAt).getOwnerReference();
        return engine.startMove(unitToMove, moveTo);
    }

    boolean select(Tile tile) {
        try {
            Controllable unit = (Controllable) engine.getMap().groundAt(new Point(tile.getCoordX(), tile.getCoordY())).getOwnerReference();
            if (isPlayersUnit(unit)) {
                return true;
            }
        } catch (RuntimeException e) {
            System.err.println("Not the player's unit");
        }
        blinkBorder(tile,BLINK_WRONG_MOVE);
        return false;
    }

    void attack(Tile from, Tile to) {
        Controllable unitAttacking;
        if((unitAttacking = (Controllable) engine.getMap().groundAt(new Point(from.getCoordX(),from.getCoordY())).getOwnerReference()) instanceof Controllable) {
            if(isPlayersUnit(unitAttacking)) {
                System.out.println("Is Controllable");
                if(engine.startAttack(unitAttacking, new Point(to.getCoordX(),to.getCoordY()))) {
                    blinkBorder(to,BLINK_ATTACK);
                    return;
                }
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
        if(isPlayersUnit(builder)) {
            if(!engine.startBuild(builder,new Point(xTo,yTo),building)) {
                System.out.println("Building failed");
                blinkBorder(buildingPoint,BLINK_WRONG_MOVE);
            }
        } else {

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

    private boolean isPlayersUnit(Controllable cont) {
        return cont.getTeam() == currPlayer;
    }

    int getResource(ResourceType type) {
        return engine.getPlayers()[currPlayer].getResourceByName(type);
    }

    boolean enoughResource(HashMap<ResourceType, Integer> resNeeded){
        System.out.println(resNeeded);
        System.out.println(engine.getPlayers()[currPlayer].getResourceMap());
        for(HashMap.Entry<ResourceType, Integer> entry : engine.getPlayers()[currPlayer].getResourceMap().entrySet()){
            ResourceType currKey = entry.getKey();
            if(entry.getValue() < resNeeded.get(currKey)){
                return false;
            }
        }
        return true;

    }

    private void blinkBorder(Tile tile, Color color) {
        SelectionController.unitSelection(tile.getUnit(), false, color);
        //tile.setBorder(new LineBorder(color,5,true));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SelectionController.unitSelection(tile.getUnit(), true);
                tile.setBorder(null);
            }
        },500);
    }


}

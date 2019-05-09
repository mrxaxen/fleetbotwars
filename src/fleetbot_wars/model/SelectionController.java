package fleetbot_wars.model;

import visual.unit.Controllable;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

class SelectionController {

    private static SelectionController instance;

    private Translation serverComm = Translation.getInstance();
    private ActionBar actionBar = ActionBar.getInstance();
    private Tile selectedTile;
    private UnitType buildingToBuild;
    private boolean tileSelected;
    private boolean buildMode;
    private boolean attackMode;

    //TODO: move on right click
    private SelectionController() {
    }

    static SelectionController getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new SelectionController();
            return instance;
        }
//        return instance;
    }

    Tile getSelectedTile() {
        return selectedTile;
    }

    void select(Tile tile) {
        if (!tileSelected) {
            selectedTile = tile;
            tileSelected = true;
            selectedTile.setBorder(new LineBorder(new Color(20, 149, 255),5,true));
            actionBar.changeActionBar(selectedTile.getUnitType());
        } else {
            if(buildMode) {
//                serverComm.createUnit(buildingToBuild,selectedTile,tile);
                tile.setUnitType(buildingToBuild);
                buildMode = false;
            } else if(attackMode){
                //do attack
            }
            selectedTile.setBorder(null);
            tileSelected = false;
            selectedTile = null;
            actionBar.changeToDefault();
        }
    }

    void move(Tile toTile) {
        int yTo = toTile.getCoordY();
        int xTo = toTile.getCoordX();
        int xFrom = selectedTile.getCoordX();
        int yFrom = selectedTile.getCoordY();


        if(!serverComm.move(new Point(xFrom,yFrom),new Point(xTo,yTo))) {
            toTile.setBorder(new LineBorder(new Color(255, 20, 20),5,true));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    toTile.setBorder(null);
                }
            },50);
        }
        select(selectedTile);
    }

    void setBuildingToBuild(UnitType unitType) {
        buildingToBuild = unitType;
    }

    void setBuildMode(boolean b) {
        buildMode = b;
    }
}

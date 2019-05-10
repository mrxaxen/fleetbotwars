package fleetbot_wars.model;

import fleetbot_wars.model.enums.VisualType;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

class SelectionController {

    private static SelectionController instance;

    private Translation serverComm = Translation.getInstance();
    private ActionBar actionBar = ActionBar.getInstance();
    private Tile selectedTile;
    private static VisualType buildingToBuild;
    static boolean buildMode;
    private static boolean attackMode;

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
    }

    Tile getSelectedTile() {
        return selectedTile;
    }

    void select(Tile tile) {

        if(!isSelected()) {
            selectedTile = tile;
            selectedTile.setBorder(new LineBorder(new Color(20,149,255),5,true));
            actionBar.changeActionBar(tile.getUnitType());
            return;
        }

        if(isSelected()) {
            System.out.println(buildMode);
            if(buildMode) {
                System.out.println("Build request sent");
                serverComm.build(selectedTile,tile,buildingToBuild);
                buildMode = false;
            }
            selectedTile.setBorder(null);
            selectedTile = null;
            actionBar.changeToDefault();
        }

//        if (!isSelected()) {
//            selectedTile = tile;
//            selectedTile.setBorder(new LineBorder(new Color(20, 149, 255),5,true));
//            actionBar.changeActionBar(tile.getUnitType());
//        } else {
//            System.out.println(buildMode);
//            if(buildMode) {
//                System.out.println("Build request sent");
//                serverComm.build(selectedTile,tile,buildingToBuild);
//                buildMode = false;
//            } else if(attackMode){
//                do attack
//            }
//            selectedTile.setBorder(null);
//            selectedTile = null;
//            setBuildMode(false);
//            actionBar.changeToDefault();
//        }
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

    void setBuildingToBuild(VisualType unitType) {
        buildingToBuild = unitType;
    }

    boolean isSelected() {
        return selectedTile != null;
    }
}

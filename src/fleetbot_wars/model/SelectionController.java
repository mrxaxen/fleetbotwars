package fleetbot_wars.model;

import javax.swing.border.LineBorder;
import java.awt.*;

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

    void select(Tile tile) {
        if (!tileSelected) {
            selectedTile = tile;
            tileSelected = true;
            selectedTile.setBorder(new LineBorder(new Color(20, 149, 255),5,true));
            actionBar.changeActionBar(selectedTile.getUnitType());

        } else {
            if(buildMode) {
                serverComm.createUnit(buildingToBuild,selectedTile,tile);
            } else if(attackMode){
                //do attack
            }
            selectedTile.setBorder(null);
            tileSelected = false;
            selectedTile = null;
            actionBar.changeToDefault();
        }
    }

    void setBuildingToBuild(UnitType unitType) {
        buildingToBuild = unitType;
    }

    void setBuildMode(boolean b) {
        buildMode = b;
    }
}

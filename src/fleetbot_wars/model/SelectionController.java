package fleetbot_wars.model;

import fleetbot_wars.model.enums.VisualType;
import visual.unit.Unit;

import javax.swing.*;
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
        System.out.println("Select: " + isSelected());
        if(!isSelected()) {
            if(serverComm.select(tile)) {
                selectedTile = tile;
                unitSelection(selectedTile.getUnit(), false, new Color(0, 255, 19));
                actionBar.changeActionBar(tile.getUnitType());
                return;
            }
        }

        if(isSelected()) {
            System.out.println(buildMode);
            if(buildMode) {
                System.out.println("Build request sent");
                serverComm.build(selectedTile,tile,buildingToBuild);
                buildMode = false;
            }
            unitSelection(selectedTile.getUnit(), true);
            //selectedTile.setBorder(null);
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

            //toTile.setBorder(new LineBorder(new Color(255, 20, 20),5,true));

            for(int i = 0; i < 3; i++) {
                Timer timer = new Timer();
                unitSelection(toTile.getUnit(), false, new Color(255, 0, 0));
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        unitSelection(toTile.getUnit(), true);
                        toTile.setBorder(null);
                    }
                }, 500);
            }
        }
        select(selectedTile);
    }

    void attack(Tile to) {
        serverComm.attack(selectedTile,to);
        setAttackMode(false);
        select(selectedTile);
    }

    void createUnit(VisualType unitToSpawn) {
        serverComm.createUnit(selectedTile,unitToSpawn);
    }

    void setAttackMode(boolean b) {
        attackMode = b;
    }

    boolean isAttacking() {
        return attackMode;
    }

    void setBuildingToBuild(VisualType unitType) {
        buildingToBuild = unitType;
    }

    boolean isSelected() {
        return selectedTile != null;
    }

    private void unitSelection(Unit selectedUnit, boolean deSelect, Color color){
        if(selectedUnit != null && selectedUnit.getCoordsArray().size() > 1) {
            Point refCoords = selectedUnit.getReferenceCoords();
            int width = selectedUnit.getWidth();
            int height = selectedUnit.getHeight();
            Point topLeft = refCoords;
            Point topRight = new Point(refCoords.x, refCoords.y + width - 1);
            Point bottomLeft = new Point(refCoords.x + height - 1, refCoords.y);
            Point bottomRight = new Point(refCoords.x + height - 1, refCoords.y + width - 1);
            if (!deSelect) {
                GameSpace.tiles[topLeft.x][topLeft.y].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, (color)));
                GameSpace.tiles[topRight.x][topRight.y].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, (color)));
                GameSpace.tiles[bottomLeft.x][bottomLeft.y].setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, (color)));
                GameSpace.tiles[bottomRight.x][bottomRight.y].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, (color)));
            } else {
                GameSpace.tiles[topLeft.x][topLeft.y].setBorder(null);
                GameSpace.tiles[topRight.x][topRight.y].setBorder(null);
                GameSpace.tiles[bottomLeft.x][bottomLeft.y].setBorder(null);
                GameSpace.tiles[bottomRight.x][bottomRight.y].setBorder(null);
            }
        }else if (selectedUnit != null && selectedUnit.getCoordsArray().size() == 1){
            Point refCoords = selectedUnit.getReferenceCoords();
            if (!deSelect) {
                GameSpace.tiles[refCoords.x][refCoords.y].setBorder(new LineBorder(color,1,false));
            } else {
                GameSpace.tiles[refCoords.x][refCoords.y].setBorder(null);

            }
        }
    }
    private void unitSelection(Unit selectedUnit, boolean deSelect){
        unitSelection(selectedUnit, deSelect, Color.green);
    }



}

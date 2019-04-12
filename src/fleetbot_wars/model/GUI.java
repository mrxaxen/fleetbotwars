/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import visual.ground.Ground;
import visual.unit.Barricade;
import visual.unit.Builder;
import visual.unit.Controllable;
import visual.unit.Unit;

/**
 *
 * @author WB
 */
public class GUI
{
    private Engine engine;
    
    //CONSTR
    
    /**
     * 
     * @return clicked location on Map
     */
    private Point selectLocation() {
        //PLACEHOLDER
        return null;
    }
    
    //UNUSED
    /*private Unit selectUnit(Point locationClicked) { //assumes existence of method that returns a Point based on mouse location (on click)
        Ground groundClicked = engine.getMap().groundAt(locationClicked);
        if (groundClicked.isOccupied()) {
            return groundClicked.getOwnerReference();
        }
        return null;
    }*/
    
    
    private void inspectOnClick() {
        Point location = selectLocation();
        engine.inspectUnit(location);
    }    
    
    ///// unified update for actions
    
    private void actionUpdate() {
        engine.actionIteration();
    }
    
    ///// Controllable Actions
        
    /// MOVEMENT: display buttons only for mobile units
    
    private void startMoveButtonClick() {
        Point tarLoc = selectLocation();
        engine.startMove(((Controllable)engine.getInspectedUnit()), tarLoc); //will only ever be called this way while Controllable is selected
    }
    
    private void stopMoveButtonClick() {
        engine.stopMove((Controllable)engine.getInspectedUnit()); //will only ever be called this way while Controllable is selected
    }
    
    /// COMBAT: display buttons only for units capable of attacking
    
    private void startAttackButtonClick() {
        Point tarLoc = selectLocation();
        engine.startAttack(((Controllable)engine.getInspectedUnit()), tarLoc); //will only ever be called this way while Controllable is selected
    }
    
    private void stopAttackButtonClick() {
        engine.stopAttack((Controllable)engine.getInspectedUnit()); //will only ever be called this way while Controllable is selected
    }
    
    /// BUILDING: display buttons only for Builders
    
    private void UNSPECIFIED_startBuildButtonClick(String buildingType) {
        Point buildingRefCoords = selectLocation();
        engine.startBuild((Controllable)engine.getInspectedUnit(), buildingRefCoords, buildingType);
    }
    
    private void stopBuildButtonClick() {
        engine.stopBuild((Controllable)engine.getInspectedUnit());
    }
    
    /*
    private void rotateBarricareOnClick() {
        engine.rotateGhostBarricade();
    }*/
    
}

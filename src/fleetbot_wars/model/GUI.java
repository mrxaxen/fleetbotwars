/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import java.util.LinkedList;
import visual.ground.Ground;
import visual.unit.Controllable;
import visual.unit.Turret;
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
    
    private Unit selectUnit(Point locationClicked) { //assumes existence of method that returns a Point based on mouse location (on click)
        Ground groundClicked = engine.getMap().groundAt(locationClicked);
        if (groundClicked.isOccupied()) {
            return groundClicked.getOwnerReference();
        }
        return null;
    }
    
    
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
    
    private void TEMP_attackOnclick(Controllable callerCont) {
        Controllable atkr = callerCont;
        Point tarLoc = selectLocation();
        Unit tar = selectUnit(tarLoc);
        if (atkHelp(atkr, tar)) {
            callerCont.setAttacking(true);
            if (callerCont.isAttacking()) {
                TEMP_attack(atkr, tar);
            }        
        }
    }
    
    private void TEMP_attack(Controllable atkr, Unit tar) {
        if (engine.inRange(atkr, tar)) { //target is in range
            if (engine.losCheck(atkr, tar)) { //target is in line of sight
                atkr.hit(tar);
                if (tar.getCurrHp() > 0) { //tar alive after being hit
                    if (tar instanceof Controllable) {
                        Controllable tarCont = (Controllable)tar;
                        //MIGHT *LOCK* TAR IN COMBAT WITH ATKR
                        if (tarCont.getDmg() > 0 && !tarCont.isAttacking()) {     
                            tarCont.setAttacking(true);
                            tarCont.hit(atkr);
                        }
                        if (atkr.getCurrHp() <= 0) { //atkr dead after defense
                            tarCont.setAttacking(false);                    
                            engine.deathEvent(tar);                            
                        }
                    }                    
                } else { //target died
                    atkr.setAttacking(false);                    
                    engine.deathEvent(tar);
                }
            } else { //blocked view: stop
                atkr.setAttacking(false);
            }
        } else {
            if (!(atkr instanceof Turret)) {
                engine.step(atkr, engine.path(atkr.getReferenceCoords(), tar.getReferenceCoords()));
            }
        }
    }
    
    private boolean atkHelp(Controllable atkr, Unit tar) {
        return atkr.isValidTarget(tar);
    }
    
    /// build
    
    //DISABLE BUTTON IF NOT ENOUGH RESOURCES
    //CREATE SPECIFIC BUTTONS
    private void TEMP_build(Controllable callerCont, String buildingType) {
        Point buildingRefCoords = selectLocation();
        callerCont.setBuilding(true);
        if (callerCont.isBuilding()) {
            engine.build(callerCont, buildingRefCoords, buildingType);            
        }
    }
    
}

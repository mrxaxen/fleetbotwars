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
    
    private void inspectUnit(Unit u) {
        //DISPLAY UNIT INFO? ACTIONS
        u.inspect();
    }
    
    ///// Controllable actions
    
    /// move
    
    //DONT SHOW BUTTON IF UNABLE TO MOVE
    private void TEMP_moveOnclick(Controllable callerCont) { //who was inspected
        //Controllable cont = (Controllable)selectedUnit;
        Point dest = selectLocation();
        LinkedList<Point> pathPoints = moveHelp_Path(callerCont, dest);
        callerCont.setMoving(true);
        if (callerCont.isMoving()) {
            TEMP_move(callerCont, pathPoints);
        }
    }
    
    /**
     * given Controllable attempts to move to given destination
     * @param cont: moving Controllable
     * @param pathPoints
     */
    private void TEMP_move(Controllable cont, LinkedList<Point> pathPoints) { //speed factor removed
        if (!pathPoints.isEmpty()) {
            engine.step(cont, pathPoints);
            //INCLUDE DROWNING
        } else {
            cont.setMoving(false);
        }
    }
    
    /**
     * 
     * @param cont
     * @param dest
     * @return empty list if destination is invalid, theoretical path else
     */
    private LinkedList<Point> moveHelp_Path(Controllable cont, Point dest) {
        LinkedList<Point> pathPoints = new LinkedList<>();
        Ground destGround = engine.getMap().groundAt(dest);
        if (!destGround.isOccupied()) { //availability check (valid target location)
            pathPoints = engine.path(cont.getReferenceCoords(), dest);
            pathPoints.add(dest);
        }
        return pathPoints;
    }
    
    /// attack
    
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

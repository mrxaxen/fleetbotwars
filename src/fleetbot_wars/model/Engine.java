/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import visual.unit.Controllable;
import visual.unit.Unit;

/**
 *
 * @author WB
 */
public class Engine
{
    private Map map;
    private Player[] players;

    /**
     * create Engine
     * @param map
     * @param players 
     */
    public Engine(Map map, Player[] players) {
        this.map = map;
        this.players = players;
    }
    
    /**
     * Conrollable attacks target Unit
     * @param attacker
     * @param target 
     */
    public void attack(Controllable attacker, Unit target) {
        //VALIDATE TARGET HIGHER UP
        if (inRange(attacker, target)) {
            //move atker to range
            attacker.offHit(target);
        }
        
    }  
    
    /*
    public void move(Controllable cont, Point dest) {
        
    }
    
    public void move(Controllable cont, Unit target) {
        
    }*/

    private boolean inRange(Controllable attacker, Unit target) {
        return attacker.getRngRect().intersects(target.getRect());      
    }
    
    ///// getters, setters

    public Map getMap() {
        return map;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class MilitarySpawn extends Controllable {
    
    /**
     * create MilitarySpawn at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public MilitarySpawn(Point coords, int team) {
        super(coords, "militaryspawn", null, 500, 0, 0, 0, 1, 1, 0, team);
    }
    
    /**
     * create 'amt' amount of 'type' Controllable
     * @param type
     * @param amt 
     */
    public void createUnit(String type, int amt) {
        //
    }
    
}

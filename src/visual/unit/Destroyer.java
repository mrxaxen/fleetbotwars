/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;

/**
 *
 * @author WB
 */
public class Destroyer extends Controllable {
    
    /**
     * create Destroyer at (x,y) coordinates, for 'team' team
     * @param team 
     */
    public Destroyer(Point coords, int team)
    {
        super(coords, "destroyer", null, 100, 1, 1, 10, 5, 1, 1, team);
    }
    
    private boolean isValidTarget(Unit tar) { 
        return tar instanceof Controllable && ((Controllable)tar).team != this.team   //building
                && true/*BUILDING CHECK!!*/;
    }
    
}

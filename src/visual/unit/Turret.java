/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Turret extends Controllable {

    /**
     * create Turret at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public Turret(Point coords, int team) {
        super(coords, "turret", null, 500, 0, 1, 10, 5, 1, 3, team);
        this.width = 2;
        this.height = 2;
    }
    
    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable
               && this.team != ((Controllable)target).team;
    }
    
}

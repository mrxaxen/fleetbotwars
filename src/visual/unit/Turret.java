/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;

/**
 *
 * @author asjf86
 */
public class Turret extends Controllable {

    /**
     * create Turret at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Turret(int x, int y, int team) {
        super(x, y, "turret", null, 500, 0, 1, 10, 5, 1, 3, team);
    }
    
}

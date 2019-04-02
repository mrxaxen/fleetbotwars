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
public class Barricade extends Controllable {

    /**
     * create Barricade at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Barricade(int x, int y, int team) {
        super(x, y, "barricade", /*barricade_model*/, /*barricade_hp*/, 0, 0, 0, 1, 0, team);
    }
    
}

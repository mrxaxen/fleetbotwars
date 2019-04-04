/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

/**
 *
 * @author WB
 */
public class Cavalry extends Controllable {
    
    /**
     * create Cavalry at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Cavalry(int x, int y, int team)
    {
        super(x, y, "cavalry", null, 100, 1, 1, 10, 5, 1, 1, team);
    }
    
}

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
public class Medic extends Controllable {
    
    /**
     * create Medic at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Medic(int x, int y, int team)
    {
        super(x, y, "medic"
                + "", /**/, /**/, /**/, /**/, /**/, 1, /**/, team);
    }
    
}

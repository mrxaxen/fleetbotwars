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
public class Destroyer extends Controllable {
    
    /**
     * create Destroyer at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Destroyer(int x, int y, int team)
    {
        super(x, y, "destroyer", /**/, /**/, /**/, /**/, /**/, 1, /**/, team);
    }
    
}

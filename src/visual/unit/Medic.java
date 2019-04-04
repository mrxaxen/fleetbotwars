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
        super(x, y, "medic", null, 100, 1, 1, -10, 5, 1, 1, team);
    }
    
    //check if overrides the one in Controllable!!!!
    private boolean isValidTarget(Unit tar) { 
        return tar instanceof Controllable && ((Controllable)tar).team == this.team; //friendly
    }
    
}

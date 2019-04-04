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
public class Miner extends Controllable {
  
    //paired with Mine
    private boolean busy = false;
    
    /**
     * create Miner at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Miner(int x, int y, int team)
    {
        super(x, y, "miner", null, 100, 1, 0, 0, 1, 1, 0, team);
    }
    
}

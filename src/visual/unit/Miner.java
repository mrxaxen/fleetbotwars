/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author WB
 */
public class Miner extends Controllable {
  
    public static HashMap<String, Integer> price = new HashMap<>();
    
    //paired with Mine
    private boolean busy = false;
    
    /**
     * create Miner at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public Miner(Point coords, int team)
    {
        super(coords, "miner", null, 100, 1, 0, 0, 1, 1, 0, team);
    }
    
}

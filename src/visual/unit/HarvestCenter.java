/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class HarvestCenter extends Controllable {
    
    /**
     * create HarvestCenter at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public HarvestCenter(Point coords, int team) {
        super(coords, "harvestcenter", null, 500, 0, 0, 0, 1, 1, 0, team);
    }
    
    /**
     * increase Player Wood resource
     */
    public void incrWood() {
        //
    }
    
}

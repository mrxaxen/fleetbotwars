/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;
import java.util.HashMap;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author asjf86
 */
public class HarvestCenter extends Controllable {
    
    public static HashMap<String, Integer> price = new HashMap<>();    
    
    /**
     * create HarvestCenter at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public HarvestCenter(Point coords, int team) {
        super(coords, VisualType.harvestcenter, null, 500, 0, 0, 0, 1, 1, 0, team);
        this.width = 3;
        this.height = 2;
    }

    /**
     * increase Player Wood resource
     */
    public void incrWood() {
        //
    }

}

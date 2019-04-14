/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.enums.ResourceType;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author asjf86
 */
public class Farm extends Controllable {

    public static HashMap<ResourceType, Integer> price = new HashMap<>();
    
    /**
     * create Farm at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Farm(Point coords, int team) {
        super(coords, VisualType.farm, null, 500, 0, 0, 0, 5, 1, 0, team);
        this.width = 3;
        this.height = 2;
    }

    /**
     * increase Player Food resource
     */
    public void incrFood() {
        // should be based on num stored in object??
    }

}

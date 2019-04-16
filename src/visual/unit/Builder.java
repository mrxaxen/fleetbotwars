/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.enums.ResourceType;
import java.awt.Point;
import java.util.HashMap;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author WB
 */
public class Builder extends Controllable {
    
    /**
     *
     */
    public static HashMap<ResourceType, Integer> price = new HashMap<>();
    
    /**
     * create Builder at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Builder(Point coords, int team) {
        super(coords, VisualType.BUILDER, null, 100, 1, 0, 0, 1, 1, 1, team);
    }

    /**
     * create 'type' building at 'x,y' coordinates
     * 
     * @param type
     * @param x
     * @param y
     */
    public void build(String type, int x, int y) {
        //
    }
}

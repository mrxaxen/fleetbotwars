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
    
    public static HashMap<ResourceType, Integer> price = Builder.initPrice();
    
    //REVISIT
    private static HashMap<ResourceType, Integer> initPrice() {
        HashMap<ResourceType, Integer> new_price = new HashMap<>();
        new_price.put(ResourceType.food, 0);
        new_price.put(ResourceType.wood, 0);
        new_price.put(ResourceType.gold, 10);
        new_price.put(ResourceType.stone, 0);
        new_price.put(ResourceType.upgrade, 0);
        return new_price;
    }
    
    /**
     * create Builder at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Builder(Point coords, int team) {
        super(coords, VisualType.BUILDER, null, 100, 1, 0, 0, 1, 1, 1, team);
    }

}

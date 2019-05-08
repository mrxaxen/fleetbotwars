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
public class Cavalry extends Controllable {
    
    public static HashMap<ResourceType, Integer> price = Cavalry.initPrice();
    
    //REVISIT
    private static HashMap<ResourceType, Integer> initPrice() {
        HashMap<ResourceType, Integer> new_price = new HashMap<>();
        new_price.put(ResourceType.food, 0);
        new_price.put(ResourceType.wood, 0);
        new_price.put(ResourceType.gold, 20);
        new_price.put(ResourceType.stone, 0);
        new_price.put(ResourceType.upgrade, 0);
        return new_price;
    }
    
    /**
     * create Cavalry at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Cavalry(Point coords, int team) {
        super(coords, VisualType.CAVALRY, null, 100, 1, 1, 10, 5, 1, 1, team);
        this.upPrice = 10;
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team != ((Controllable) target).team;
    }

}

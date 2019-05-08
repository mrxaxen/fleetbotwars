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
 * @author asjf86
 */
public class StoneMine extends Mine {
        
    public static HashMap<ResourceType, Integer> price = StoneMine.initPrice();
    
    //REVISIT
    private static HashMap<ResourceType, Integer> initPrice() {
        HashMap<ResourceType, Integer> new_price = new HashMap<>();
        new_price.put(ResourceType.food, 0);
        new_price.put(ResourceType.wood, 30);
        new_price.put(ResourceType.gold, 0);
        new_price.put(ResourceType.stone, 30);
        new_price.put(ResourceType.upgrade, 0);
        return new_price;
    }
    
    /**
     * create StoneMine at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public StoneMine(Point coords, int team) {
        super(coords, VisualType.STONEMINE, null, 500, team);
        this.width = 3;
        this.height = 2;
    }

    /**
     * increase Player Stone resource
     */
    @Override
    public void incrRes() {
        //
    }
}

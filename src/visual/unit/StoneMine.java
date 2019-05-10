/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.Player;
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
        new_price.put(ResourceType.food, 1);
        new_price.put(ResourceType.wood, 1);
        new_price.put(ResourceType.gold, 1);
        new_price.put(ResourceType.stone, 1);
        new_price.put(ResourceType.upgrade, 1);
        return new_price;
    }
    
    /**
     * create StoneMine at (x,y) coordinates, for 'team' team
     *  @param coords
     * @param player
     */
    public StoneMine(Point coords, Player player) {
        super(coords, VisualType.STONEMINE, null, 500, player);
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

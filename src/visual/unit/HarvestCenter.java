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
public class HarvestCenter extends Controllable {
    
    public static HashMap<ResourceType, Integer> price = HarvestCenter.initPrice();

    //REVISIT
    private static HashMap<ResourceType, Integer> initPrice() {
        HashMap<ResourceType, Integer> new_price = new HashMap<>();
        new_price.put(ResourceType.food, 0);
        new_price.put(ResourceType.wood, 20);
        new_price.put(ResourceType.gold, 0);
        new_price.put(ResourceType.stone, 20);
        new_price.put(ResourceType.upgrade, 0);
        return new_price;
    }
    
    /**
     * create HarvestCenter at (x,y) coordinates, for 'team' team
     *  @param coords
     * @param player
     */
    public HarvestCenter(Point coords, Player player) {
        super(coords, VisualType.HARVESTCENTER, null, 500, 0, 0, 0, 1, 1, 0, player);
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

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
public class Farm extends Controllable {

    public static HashMap<ResourceType, Integer> price = Farm.initPrice();

    private static HashMap<ResourceType, Integer> initPrice() {
        HashMap<ResourceType, Integer> new_price = new HashMap<>();
        new_price.put(ResourceType.food, 15);
        new_price.put(ResourceType.wood, 100);
        new_price.put(ResourceType.gold, 110);
        new_price.put(ResourceType.stone, 20);
        new_price.put(ResourceType.upgrade, 0);
        return new_price;
    }

    /**
     * create Farm at (x,y) coordinates, for 'team' team
     *  @param coords
     * @param player
     */
    public Farm(Point coords, Player player) {
        super(coords, VisualType.FARM, null, 500, 0, 0, 0, 5, 1, 0, player);
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

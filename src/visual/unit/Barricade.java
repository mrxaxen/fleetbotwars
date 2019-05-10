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
public class Barricade extends Controllable {

    public static HashMap<ResourceType, Integer> price = Barricade.initPrice();
    
    //REVISIT
    private static HashMap<ResourceType, Integer> initPrice() {
        HashMap<ResourceType, Integer> new_price = new HashMap<>();
        new_price.put(ResourceType.food, 0);
        new_price.put(ResourceType.wood, 10);
        new_price.put(ResourceType.gold, 0);
        new_price.put(ResourceType.stone, 10);
        new_price.put(ResourceType.upgrade, 0);
        return new_price;
    }
    
    /**
     * create Barricade at (x,y) coordinates, for 'team' team
     *  @param coords
     * @param player
     */
    public Barricade(Point coords, Player player) {
        super(coords, VisualType.BARRICADE, null, 500, 0, 0, 0, 5, 1, 0, player);
        this.upPrice = 10;
    }

    /**
     * rotates Barricade by 90° by swapping its width and height
     */
    //UNUSED
    /*
    public void rotate() {
        int currWidth = this.width;
        int currHeight = this.height;
        this.width = currHeight;
        this.height = currWidth;
    }*/
}

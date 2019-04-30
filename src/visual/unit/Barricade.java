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
public class Barricade extends Controllable {

    public static HashMap<ResourceType, Integer> price = Barricade.initPrice();
    
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
     * create Barricade at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Barricade(Point coords, int team) {
        super(coords, VisualType.BARRICADE, null, 500, 0, 0, 0, 5, 1, 0, team);

        this.width = 1;
        this.height = 1;
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

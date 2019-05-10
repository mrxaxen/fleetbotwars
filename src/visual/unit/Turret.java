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
public class Turret extends Controllable {

    public static HashMap<ResourceType, Integer> price = initPrice();

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
     * create Turret at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Turret(Point coords, int team) {
        super(coords, VisualType.TURRET, null, 500, 0, 1, 10, 5, 1, 2, team);
        this.width = 2;
        this.height = 2;
        this.upPrice = 10;
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team != ((Controllable) target).team;
    }

}

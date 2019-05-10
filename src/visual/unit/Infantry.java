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
 * @author WB
 */
public class Infantry extends Controllable {

    public static HashMap<ResourceType, Integer> price = new HashMap<>();
    
    /**
     * create Infantry at (x,y) coordinates, for 'team' team
     *  @param coords
     * @param player
     */
    public Infantry(Point coords, Player player) {
        super(coords, VisualType.INFANTRY, null, 100, 1, 1, 10, 5, 1, 1, player);
        this.upPrice = 10;
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team != ((Controllable) target).team;
    }

}

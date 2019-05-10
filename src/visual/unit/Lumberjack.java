/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.Player;
import fleetbot_wars.model.enums.ResourceType;
import fleetbot_wars.model.enums.VisualType;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author WB
 */
public class Lumberjack extends Controllable {

    public static HashMap<ResourceType, Integer> price = new HashMap<>();
    
    /**
     * create Lumberjack at (x,y) coordinates, for 'team' team
     *  @param coords
     * @param player
     */
    public Lumberjack(Point coords, Player player) {
        super(coords, VisualType.LUMBERJACK, null, 100, 1, 1, 10, 1, 1, 1, player);
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Tree;
    }

}

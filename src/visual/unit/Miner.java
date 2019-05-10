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
public class Miner extends Controllable {

    // paired with Mine
  
    public static HashMap<ResourceType, Integer> price = new HashMap<>();
    
    //paired with Mine
    private boolean busy = false;

    /**
     * create Miner at (x,y) coordinates, for 'team' team
     *  @param coords
     * @param player
     */
    public Miner(Point coords, Player player) {
        super(coords, VisualType.MINER, null, 100, 1, 0, 0, 1, 1, 0, player);
    }

}

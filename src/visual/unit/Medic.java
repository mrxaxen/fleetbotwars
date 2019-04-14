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
 * @author WB
 */
public class Medic extends Controllable {
    
    public static HashMap<ResourceType, Integer> price = new HashMap<>();
    
    /**
     * create Medic at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Medic(Point coords, int team) {
        super(coords, VisualType.medic, null, 100, 1, 1, -10, 5, 1, 1, team);
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team == ((Controllable) target).team;
        // ADDITIONAL RESTRICTION? (exclude buildings)
    }

}

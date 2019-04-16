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

    public static HashMap<ResourceType, Integer> price = new HashMap<>();         
    
    /**
     * create Turret at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Turret(Point coords, int team) {
<<<<<<< src/visual/unit/Turret.java
        super(coords, VisualType.turret, null, 500, 0, 1, 10, 5, 1, 2, team);
=======
        super(coords, VisualType.TURRET, null, 500, 0, 1, 10, 5, 1, 3, team);
>>>>>>> src/visual/unit/Turret.java
        this.width = 2;
        this.height = 2;
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team != ((Controllable) target).team;
    }

}

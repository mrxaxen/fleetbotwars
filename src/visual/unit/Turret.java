/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author asjf86
 */
public class Turret extends Controllable {

    public static HashMap<Enum, Integer> price = new HashMap<>();
    
    /**
     * create Turret at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Turret(Point coords, int team) {
        super(coords, VisualType.turret, null, 500, 0, 1, 10, 5, 1, 3, team);
        this.width = 2;
        this.height = 2;
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team != ((Controllable) target).team;
    }

}

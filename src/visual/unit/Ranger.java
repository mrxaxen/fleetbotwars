/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author WB
 */
public class Ranger extends Controllable {

    /**
     * create Ranger at (x,y) coordinates, for 'team' team
     * 
     * @param x
     * @param y
     * @param team
     */
    public Ranger(Point coords, int team) {
        super(coords, VisualType.ranger, null, 100, 1, 1, 10, 5, 1, 2, team);
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team != ((Controllable) target).team;
    }

}

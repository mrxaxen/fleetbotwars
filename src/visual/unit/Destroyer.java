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
public class Destroyer extends Controllable {

    /**
     * create Destroyer at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Destroyer(Point coords, int team) {
        super(coords, VisualType.destroyer, null, 100, 1, 1, 50, 5, 1, 1, team);
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Controllable && this.team != ((Controllable) target).team
                && ((Controllable) target).isBuildingType();
    }

}

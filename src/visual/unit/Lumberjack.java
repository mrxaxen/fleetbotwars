/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.enums.VisualType;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author WB
 */
public class Lumberjack extends Controllable {

    /**
     * create Lumberjack at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Lumberjack(Point coords, int team) {
        super(coords, VisualType.lumberjack, null, 100, 1, 1, 10, 1, 1, 1, team);
    }

    @Override
    public boolean isValidTarget(Unit target) {
        return target instanceof Tree;
    }

}

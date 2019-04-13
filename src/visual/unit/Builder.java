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
public class Builder extends Controllable {
    /**
     * create Builder at (x,y) coordinates, for 'team' team
     * 
     * @param coords
     * @param team
     */
    public Builder(Point coords, int team) {
        super(coords, VisualType.builder, null, 100, 1, 0, 0, 1, 1, 1, team);
    }

    /**
     * create 'type' building at 'x,y' coordinates
     * 
     * @param type
     * @param x
     * @param y
     */
    public void build(String type, int x, int y) {
        //
    }
}

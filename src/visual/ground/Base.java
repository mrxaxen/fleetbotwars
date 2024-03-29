/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import fleetbot_wars.model.enums.VisualType;

import java.awt.Point;

import visual.unit.Unit;

/**
 *
 * @author asjf86
 */
public class Base extends Ground {

    /**
     * Creating a Base element. This is a non-colliding element, ie. not an
     * obsiticle. In other words it's a representation of a simple plain ground.
     * 
     * @param coords It's coords.
     */
    public Base(Point coords) {
        super(coords, VisualType.BASE, null, null);
    }

    public Base(Point coords, Unit owner) {
        super(coords, VisualType.BASE, null, owner);
    }

}

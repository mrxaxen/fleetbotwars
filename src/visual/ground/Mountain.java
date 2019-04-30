/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import java.awt.Point;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author asjf86
 */
public class Mountain extends Ground {

    /**
     * Creating a Mountain element. This element can be collided, ie. an obsiticle.
     * 
     * @param coords It's coords.
     */
    public Mountain(Point coords) {
        super(coords, VisualType.MOUNTAIN, null, null);
        this.occupied = true;
    }

}

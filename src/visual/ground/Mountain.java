/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Mountain extends Ground {

    /**
     * create Mountain
     * @param x
     * @param y 
     */
    public Mountain(Point coords) {
        super(coords, "mountain", null, null);
        this.occupied = true;
    }
    
}

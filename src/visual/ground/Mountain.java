/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import java.awt.Image;

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
    public Mountain(int x, int y) {
        super(x, y, "mountain", /*mountain_model*/);
        this.occupied = true;
    }
    
}

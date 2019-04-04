/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import visual.ground.Ground;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Base extends Ground {

    /**
     * create Base
     * @param x
     * @param y 
     */
    public Base(Point coords) {
        super(coords, "base", null, null);
    }   
    
}

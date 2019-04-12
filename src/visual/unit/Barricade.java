/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Barricade extends Controllable {

    /**
     * create Barricade at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public Barricade(Point coords, int team) {
        super(coords, "barricade", null, 500, 0, 0, 0, 1, 1, 0, team);
        this.width = 5;
        this.height = 1;
    }
   
    /**
     * rotates Barricade by 90° by swapping its width and height
     */
    //UNUSED
    /*
    public void rotate() {
        int currWidth = this.width;
        int currHeight = this.height;
        this.width = currHeight;
        this.height = currWidth;
    }*/
}

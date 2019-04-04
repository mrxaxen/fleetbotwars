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
public class Tree extends Unit {

    /**
     * create Tree at (x,y) coordinates
     * @param x
     * @param y
     * @param type
     * @param model
     * @param hp 
     */
    public Tree(Point coords) {
        super(coords, "tree", null, 50);
    }
    
}

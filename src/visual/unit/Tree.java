/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author asjf86
 */
public class Tree extends Unit {

    /**
     * create Tree at (x,y) coordinates
     * 
     * @param x
     * @param y
     * @param type
     * @param model
     * @param hp
     */
    public Tree(Point coords) {
        super(coords, VisualType.tree, null, 50);
    }

}

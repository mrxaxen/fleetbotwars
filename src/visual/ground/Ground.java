/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import java.awt.Image;
import visual.Visual;

/**
 *
 * @author asjf86
 */
public class Ground extends Visual {
    
    protected boolean occupied = false;

    /**
     * create Ground
     * @param x
     * @param y
     * @param type
     * @param model 
     */
    public Ground(int x, int y, String type, Image model) {
        super(x, y, type, model);
    }  
    
}

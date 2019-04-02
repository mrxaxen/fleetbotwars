/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import visual.Visual;

/**
 *
 * @author asjf86
 */
public class Unit extends Visual {
    
    protected int hp;

    /**
     * create Unit
     * @param x
     * @param y
     * @param type
     * @param model
     * @param hp 
     */
    public Unit(int x, int y, String type, Image model, int hp) {
        super(x, y, type, model);
        this.hp = hp;
    }
    
    /**
     * events upon death of Unit
     */
    public void deathEvent() {
        //
    }
    
    /**
     * display properties and actions of Unit
     */
    public void inspect() {
        //
    }
}

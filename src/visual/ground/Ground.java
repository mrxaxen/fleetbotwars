/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import java.awt.Image;
import java.awt.Point;
import visual.Visual;
import visual.unit.Unit;

/**
 *
 * @author asjf86
 */
public class Ground extends Visual {
    
    protected boolean occupied = false;
    private Unit ownerRefernce;

    /**
     * create Ground
     * @param coords 
     * @param type
     * @param model 
     * @param ownerRefernce
     */
    public Ground(Point coords, String type, Image model, Unit ownerRefernce) {
        super(coords, type, model);
        this.ownerRefernce = ownerRefernce;
    }  

    /**
     * 
     * @return true if occupied, false else
     */
    public boolean isOccupied(){
        return ownerRefernce.equals(null);
    }

    /**
     * 
     * @return the reference to the Player Object, 
     * who owns/occupies the current ground
     */
    public Unit getOwnerReference(){
        return ownerRefernce;
    }
}

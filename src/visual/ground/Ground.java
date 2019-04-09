/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.ground;

import java.awt.Image;
import visual.Visual;
import visual.unit.Unit;
import visual.Visual;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Ground extends Visual {
    
    protected boolean occupied = false;
    private Unit ownerReference;

    /**
     * create Ground
     * @param coords 
     * @param type
     * @param model 
     * @param ownerReference
     */
    public Ground(Point coords, String type, Image model, Unit ownerReference) {
        super(coords, type, model);
        this.ownerReference = ownerReference;
    }  

    /**
     * 
     * @return true if occupied, false else
     */
    public boolean isOccupied(){
        return ownerReference != null;
    }

    /**
     * 
     * @return the reference to the Unit Object, 
     * who owns/occupies the current ground
     */
    public Unit getOwnerReference(){
        return ownerReference;
    }

    public void setOwnerReference(Unit ownerReference) {
        this.ownerReference = ownerReference;
    }
    
}

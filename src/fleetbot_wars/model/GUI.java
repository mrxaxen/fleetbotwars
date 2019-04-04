/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import visual.ground.Ground;
import visual.unit.Unit;

/**
 *
 * @author WB
 */
public class GUI
{
    private Engine engine;
    
    //CONSTR
    
    public Unit select(Point locationClicked) { //assumes existence of method that returns a Point based on mouse location (on click)
        Ground groundClicked = engine.getMap().groundAt(locationClicked);
        //isOccupied, getOwnerReference
        if (groundClicked.isOccupied()) {
            return groundClicked.getOwnerReference();
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import visual.ground.Ground;

/**
 *
 * @author WB
 */
public class Map
{
    private Ground[][] ground;
    
    //CONSTR
    
    
    /**
     * returns the Ground object at given location(x, y specified by Point)
     * @param location
     * @return 
     */
    public Ground groundAt(Point location) {
        return ground[location.x][location.y];
    }
    
    public void remTree(Point refCoords) {
        ground[refCoords.x][refCoords.y] = null;
    }
}

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
    
    /**
     * used to help building
     * @param c
     * @param minableType
     * @return true if at least 1 surrounding Ground is stone (check 8 grid points)
     */
    //REVISIT
    public boolean adjMineralCheck(Point c, String minableType) {
        int x = c.x;
        int y = c.y;
        //also checks ground at c (redundant cuz cannot be occupied)
        for (int i = x - 1; i < x + 2; ++i) {
            for (int j = y - 1; j < y + 2; ++j) {
                if (ground[i][j].getType().equals("minableType")) {
                    return true;
                }
            }
        }
        return false;
    }
}

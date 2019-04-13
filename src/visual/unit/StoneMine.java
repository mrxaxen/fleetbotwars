/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author asjf86
 */
public class StoneMine extends Mine {
        
    public static HashMap<String, Integer> price = new HashMap<>();
    
    /**
     * create StoneMine at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public StoneMine(Point coords, int team) {
        super(coords, "stonemine", null, 500, team);
        this.width = 3;
        this.height = 2;
    }
    
    /**
     * increase Player Stone resource
     */
    @Override
    public void incrRes() {
        //
    }
}

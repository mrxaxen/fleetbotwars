/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class GoldMine extends Mine {

    /**
     * create GoldMine at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public GoldMine(Point coords, int team) {
        super(coords, "goldmine", null, 500, team);
        this.width = 3;
        this.height = 2;
    }
    
    /**
     * increase Player Gold resource
     */
    @Override
    public void incrRes() {
        //
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

/**
 *
 * @author asjf86
 */
public class StoneMine extends Mine {
        
    /**
     * create StoneMine at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public StoneMine(int x, int y, int team) {
        super(x, y, "stonemine", null, 500, team);
    }
    
    /**
     * increase Player Stone resource
     */
    @Override
    public void incrRes() {
        //
    }
}

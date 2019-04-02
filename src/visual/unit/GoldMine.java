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
public class GoldMine extends Mine {

    /**
     * create GoldMine at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public GoldMine(int x, int y, int team) {
        super(x, y, "goldmine", /*gm_model*/, /*gm_hp*/, team);
    }
    
    /**
     * increase Player Gold resource
     */
    @Override
    public void incrRes() {
        //
    }
    
}

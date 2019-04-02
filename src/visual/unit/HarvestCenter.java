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
public class HarvestCenter extends Controllable {
    
    /**
     * create HarvestCenter at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public HarvestCenter(int x, int y, int team) {
        super(x, y, "harvestcenter", /*hc_model*/, /*hc_hp*/, 0, 0, 0, 1, 0, team);
    }
    
    /**
     * increase Player Wood resource
     */
    public void incrWood() {
        //
    }
    
}

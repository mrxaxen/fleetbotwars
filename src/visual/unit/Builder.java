/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

/**
 *
 * @author WB
 */
public class Builder extends Controllable {
    /**
     * create Builder at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Builder(int x, int y, int team)
    {
        super(x, y, "builder", /*builder_model*/, /*builder_hp*/, /*builder_mvmtSpd*/, 0, 0, 1, /*lj_rng*/, team);
    }
    
    /**
     * create 'type' building at 'x,y' coordinates
     * @param type
     * @param x
     * @param y 
     */
    public void build(String type, int x, int y) {
        //
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;

/**
 *
 * @author WB
 */
public class Infantry extends Controllable {

    /**
     * create Infantry at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public Infantry(int x, int y, int team)
    {
        super(x, y, "infantry", /**/, /**/, /**/, /**/, /**/, 1, /**/, team);
    }
    
}

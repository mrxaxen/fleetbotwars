/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;

/**
 *
 * @author asjf86
 */
public class WorkerSpawn extends Controllable {

    /**
     * create WorkerSpawn at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param team 
     */
    public WorkerSpawn(int x, int y, int team) {
        super(x, y, "workerspawn", null, 500, 0, 0, 0, 1, 1, 0, team);
    }
    
    /**
     * create 'amt' amount of 'type' Controllable
     * @param type
     * @param amt 
     */
    public void createUnit(String type, int amt) {
        //
    }
    
}

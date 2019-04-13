/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author asjf86
 */
public class WorkerSpawn extends Controllable {

    public static HashMap<String, Integer> price = new HashMap<>();
    
    /**
     * create WorkerSpawn at (x,y) coordinates, for 'team' team
     * @param coords
     * @param team 
     */
    public WorkerSpawn(Point coords, int team) {
        super(coords, "workerspawn", null, 500, 0, 0, 0, 1, 1, 0, team);
        this.width = 3;
        this.height = 2;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.Player;
import java.awt.Image;
import java.awt.Point;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author WB
 */
abstract public class Mine extends Controllable {

    // paired with Miner
    protected Miner miner;
    
    /**
     * called when specific Mine created
     * 
     * @param coords
     * @param type
     * @param model
     * @param hp
     * @param team
     */
    public Mine(Point coords, VisualType type, Image model, int hp, int team) {
        super(coords, type, model, hp, 0, 0, 0, 1, 1, 0, team);
        this.miner = null;        
    }

    abstract public void incrRes(Player p);
    
    public boolean isActive() {
        return miner != null;
    }
    
    ///// getters, setters

    public Miner getMiner() {
        return miner;
    }

    public void setMiner(Miner miner) {
        this.miner = miner;
    }
    
}

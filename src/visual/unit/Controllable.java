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
public class Controllable extends Unit{
    
    private int mvmtSpd, atkSpd, dmg, lvl, rng, team;

    /**
     * create Controllable at (x,y) coordinates, for 'team' team
     * @param x
     * @param y
     * @param type
     * @param model
     * @param hp
     * @param mvmtSpd
     * @param atkSpd
     * @param dmg
     * @param lvl
     * @param rng
     * @param team 
     */
    public Controllable(int x, int y, String type, Image model, int hp,
                int mvmtSpd, int atkSpd, int dmg, int lvl, int rng, int team) {
        super(x, y, type, model, hp);
        this.mvmtSpd = mvmtSpd;
        this.atkSpd = atkSpd;
        this.dmg = dmg;
        this.lvl = lvl;
        this.rng = rng;
        this.team = team;
    }
    
    /**
     * attack target Unit
     * @param tar: target Unit
     */
    public void attack(Unit tar) {
        //
    }
    
    /**
     * automatically react to being attacked
     */
    public void defend() {
        //
    }
    
    /**
     * move to target location
     * @param x
     * @param y 
     */
    public void move(int x, int y) {
        //
    }
    
    /**
     * increade Unit level
     */
    public void upgrade() {
        //
    }
}

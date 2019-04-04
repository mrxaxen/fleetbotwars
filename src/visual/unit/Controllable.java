/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author asjf86
 */
public class Controllable extends Unit {

    private int mvmtSpd, atkSpd, dmg, maxLvl, currLvl, rng;
    protected int team;
    private Rectangle rngRect;

    /**
     * create Controllable at (x,y) coordinates, for 'team' team
     *
     * @param x
     * @param y
     * @param type
     * @param model
     * @param hp
     * @param mvmtSpd
     * @param atkSpd
     * @param dmg
     * @param maxLvl
     * @param lvl
     * @param rng
     * @param team
     */
    public Controllable(Point coords, String type, Image model, int hp,
            int mvmtSpd, int atkSpd, int dmg, int maxLvl, int lvl, int rng, int team) { //MAXLEVEL IN SPECIFIC CONSTRS
        super(coords, type, model, hp);
        this.mvmtSpd = mvmtSpd;
        this.atkSpd = atkSpd;
        this.dmg = dmg;
        this.maxLvl = maxLvl;
        this.currLvl = lvl;
        this.rng = rng;
        this.team = team;
        //always created as lvl 1
        this.rngRect = new Rectangle(this.rect.x - rng, this.rect.y - rng,
                                     this.rect.width + 2 * rng, this.rect.height + 2 * rng);
    }

    /**
     * hit target Unit (offensive). target will defend itself if able to
     *
     * @param tar: target Unit
     */
    public void offHit(Unit tar) {
        //validate target HIGHER UP(isValidTarget)
        this.hit(tar);
        if (tar instanceof Controllable && ((Controllable)tar).dmg > 0) {
            ((Controllable)tar).defHit(this);
        }
    }
    
    /**
     * hit target Controllable (defensive) after being hit (offensively) by target
     * @param tar 
     */
    public void defHit(Controllable tar) {
        this.hit(tar);
    }
    
    /**
     * returns whether the targeted Unit is valid target for attacking Controllable
     * @param tar
     * @return 
     */
    private boolean isValidTarget(Unit tar) { //most used version: mobile Controllable attacks
        return tar instanceof Controllable && ((Controllable)tar).team != this.team;
    }

    private void hit(Unit tar) {
        //MOVE ATKSPD MODIF HIGHER UP?????????????????
        tar.currHp -= this.dmg * atkSpd; 
        if (tar.currHp <= 0) {
            tar.deathEvent();
        }
    }

    /**
     * move to target location (steps in direction of greater distance)     *
     * @param x
     * @param y
     */
    public void step(int x, int y) {
        //loop, check ground availibility, add mvmtSpd factor (HIGHER UP)    
        int xdist = x - this.x; int xdir = (int) Math.signum(xdist);
        int ydist = y - this.y; int ydir = (int) Math.signum(ydist);
        
        if (xdist != 0 || ydist != 0) {
            if (xdist <= ydist) {
                this.x += xdir;
                xdist -= xdir;
            } else {
                this.y += ydir;
                ydist -= ydir;
            }
        }
    }

    /**
     * increase Unit level
     */
    public void upgrade() {
        //check resources HIGHER UP
        if (this.currLvl < this.maxLvl){
            ++this.currLvl;
            this.maxHp *= (1 + 1 / currLvl);
            //this.currHp += ??;    //wassup with these
            //this.mvmtSpd *= lvl;
            this.atkSpd *= (1 + 1 / currLvl);
            this.dmg *= (1 + 1 / currLvl);
            //this.rng *= currLvl;
        }
    }
    
    ///// getters, setters

    public Rectangle getRngRect() {
        return rngRect;
    }
    
}

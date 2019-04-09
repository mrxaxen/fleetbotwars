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
public abstract class Controllable extends Unit {

    private int mvmtSpd, atkSpd, dmg, maxLvl, currLvl, rng;
    protected int team;
    private Rectangle rngRect;

    /**
     * create Controllable at (x,y) coordinates, for 'team' team     *
     * @param coords
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
        this.rngRect = new Rectangle(this.referenceCoords.x - rng, this.referenceCoords.y - rng,
                                     this.width + 2 * rng, this.height + 2 * rng);
    }

    /**
     * hit target Unit (offensive). target will defend itself if able to     *
     * @param tar: target Unit
     */
    public void offHit(Unit tar) {
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
        if (this.isValidTarget(tar)) {            
            this.hit(tar);
        }
    }
    
    /**
     * returns whether the targeted Unit is valid target for attacking Controllable
     * @param target
     * @return 
     */
    //class made abstract, therefore this method can be overridden in specific Controllables
    //only necessary to do so where Controllable has to attack
    public boolean isValidTarget(Unit target) { return false; }
    
    private void hit(Unit tar) {
        tar.currHp -= this.dmg * atkSpd; 
        if (tar.currHp <= 0) {
            tar.deathEvent();
        }
    }

    //step moved up to Engine

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
    
    public boolean isBuilding() {
        String t = this.type;
        return t.equals("workerspawn") || t.equals("militaryspawn")
               || t.equals("farm") || t.equals("harvestcenter") 
               || t.equals("goldmine") || t.equals("stonemine")
               || t.equals("turret") || t.equals("barricade");
    }
    
    public boolean isHuman() {
        return !isBuilding();
    }
    
    ///// getters, setters

    public Rectangle getRngRect() {
        return rngRect;
    }

    public int getTeam() {
        return team;
    }
    
}

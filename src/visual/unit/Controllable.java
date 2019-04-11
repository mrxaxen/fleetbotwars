/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 *
 * @author asjf86
 */
public abstract class Controllable extends Unit {

    private int mvmtSpd, atkSpd, dmg, maxLvl, currLvl, rng;
    protected int team;
    private Rectangle rngRect;
    //action helpers:
    private boolean moving = false;
    private LinkedList<Point> currPath = new LinkedList<>();
    private boolean attacking = false;
    private Unit currTar = null;
    private boolean building = false;
    private Controllable ghostBuilding = null;
    private Point builderTarLoc = null;

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
     * clears planned path of Controllable
     */
    public void clearPath() {
        currPath.clear();
    }
    
    /**
     * hit target Unit (offensive). target will defend itself if able to     *
     * @param tar: target Unit
     */
    /*
    public void offHit(Unit tar) {
        this.hit(tar);
        if (tar instanceof Controllable && ((Controllable)tar).dmg > 0) {
            ((Controllable)tar).defHit(this);
        }
    }
    */
    
    /**
     * hit target Controllable (defensive) after being hit (offensively) by target
     * @param tar 
     */
    /*
    public void defHit(Controllable tar) {
        if (this.isValidTarget(tar)) {            
            this.hit(tar);
        }
    }
    */
    
    /**
     * returns whether the targeted Unit is valid target for attacking Controllable
     * @param target
     * @return 
     */
    //class made abstract, therefore this method can be overridden in specific Controllables
    //only necessary to do so where Controllable has to attack
    public boolean isValidTarget(Unit target) { return false; }
    
    public void hit(Unit tar) {
        tar.currHp -= this.dmg * atkSpd; 
        /*if (tar.currHp <= 0) {
            tar.deathEvent();
        }*/
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
    
    public boolean isBuildingType() {
        String t = this.type;
        return t.equals("workerspawn") || t.equals("militaryspawn")
               || t.equals("farm") || t.equals("harvestcenter") 
               || t.equals("goldmine") || t.equals("stonemine")
               || t.equals("turret") || t.equals("barricade");
    }
    
    public boolean isHuman() {
        return !isBuildingType();
    }
    
    ///// getters, setters

    public Rectangle getRngRect() {
        return rngRect;
    }

    public int getTeam() {
        return team;
    }

    public int getDmg() {
        return dmg;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public LinkedList<Point> getCurrPath() {
        return currPath;
    }

    public void setCurrPath(LinkedList<Point> currPath) {
        this.currPath = currPath;
    }

    public void setCurrTar(Unit currTar) {
        this.currTar = currTar;
    }

    public Unit getCurrTar() {
        return currTar;
    }

    public Controllable getGhostBuilding() {
        return ghostBuilding;
    }

    public void setGhostBuilding(Controllable ghostBuilding) {
        this.ghostBuilding = ghostBuilding;
    }

    public Point getBuilderTarLoc() {
        return builderTarLoc;
    }

    public void setBuilderTarLoc(Point builderTarLoc) {
        this.builderTarLoc = builderTarLoc;
    }
    
}

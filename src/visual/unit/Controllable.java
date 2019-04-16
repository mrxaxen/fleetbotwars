/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.enums.VisualType;
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
    //private Rectangle rngRect;
    // action helpers:
    // private boolean moving = false;
    private LinkedList<Point> currPath = new LinkedList<>();
    // private boolean attacking = false;
    private Unit currTar = null;
    // private boolean building = false;
    private Controllable ghostBuilding = null;
    // private Point builderTarLoc = null;

    /**
     * create Controllable at (x,y) coordinates, for 'team' team *
     * 
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
    public Controllable(Point coords, VisualType type, Image model, int hp, int mvmtSpd, int atkSpd, int dmg,
            int maxLvl, int lvl, int rng, int team) { // MAXLEVEL IN SPECIFIC CONSTRS
        super(coords, type, model, hp);
        this.mvmtSpd = mvmtSpd;
        this.atkSpd = atkSpd;
        this.dmg = dmg;
        this.maxLvl = maxLvl;
        this.currLvl = lvl;
        this.rng = rng;
        this.team = team;
    }

    /**
     * clears planned path of Controllable
     */
    public void clearPath() {
        currPath.clear();
    }

    /**
     * returns whether the targeted Unit is valid target for attacking Controllable
     * 
     * @param target
     * @return
     */
    // class made abstract, therefore this method can be overridden in specific
    // Controllables
    // only necessary to do so where Controllable has to attack
    public boolean isValidTarget(Unit target) {
        return false;
    }

    public void hit(Unit tar) {
        int resHp = tar.currHp - this.dmg * atkSpd;
        if (resHp < tar.maxHp) {
            tar.currHp = resHp;
        } else {
            tar.currHp = tar.maxHp;
        }
    }

    /**
     * increase Unit level
     */
    public void upgrade() {
        // check resources HIGHER UP
        if (this.currLvl < this.maxLvl) {
            ++this.currLvl;
            this.maxHp *= (1 + 1 / currLvl);
            // this.currHp += ??; //wassup with these
            // this.mvmtSpd *= lvl;
            this.atkSpd *= (1 + 1 / currLvl);
            this.dmg *= (1 + 1 / currLvl);
            // this.rng *= currLvl;
        }
    }

    public boolean isBuildingType() {
        String t = this.type.name();
        return t.equals("WORKERSPAWN") || t.equals("MILITARYSPAWN") || t.equals("FARM") || t.equals("HARVESTCENTER")
                || t.equals("GOLDMINE") || t.equals("STONEMINE") || t.equals("TURRET") || t.equals("BARRICADE");
    }

    public boolean isHumanType() {
        return !isBuildingType();
    }

    ///// getters, setters
    
    public int getTeam() {
        return team;
    }

    public int getDmg() {
        return dmg;
    }

    public boolean isMoving() {
        return !currPath.isEmpty();
    }

    public boolean isAttacking() {
        return currTar != null;
    }

    public boolean isBuilding() {
        return ghostBuilding != null;
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
        return new Point(ghostBuilding.getReferenceCoords().x - 1, ghostBuilding.getReferenceCoords().y);
    }

    public int getAtkSpd() {
        return atkSpd;
    }

    public int getRng() {
        return rng;
    }

}

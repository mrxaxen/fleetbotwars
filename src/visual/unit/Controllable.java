/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import fleetbot_wars.model.Player;
import fleetbot_wars.model.enums.VisualType;

import java.awt.*;
import java.util.LinkedList;

/**
 *
 * @author asjf86
 */
public abstract class Controllable extends Unit {

    private int mvmtSpd, atkSpd, dmg, maxLvl, currLvl, rng;
    // action helpers:
    private LinkedList<Point> currPath = new LinkedList<>();
    private Unit currTar = null;
    private Controllable ghostBuilding = null;
    protected int upPrice = 0;
    private Player player;
    protected final int team;
    private Color color;

    /**
     * create Controllable at (x,y) coordinates, for 'team' team *
<<<<<<< HEAD
     *  @param coords
=======
     *
     * @param coords
>>>>>>> refs/remotes/origin/wiring_engine_graph
     * @param type
     * @param model
     * @param hp
     * @param mvmtSpd
     * @param atkSpd
     * @param dmg
     * @param maxLvl
     * @param lvl
     * @param rng
     * @param player
     */
    public Controllable(Point coords, VisualType type, Image model, int hp, int mvmtSpd, int atkSpd, int dmg,
                        int maxLvl, int lvl, int rng, Player player) { // MAXLEVEL IN SPECIFIC CONSTRS
        super(coords, type, model, hp);
        this.mvmtSpd = mvmtSpd;
        this.atkSpd = atkSpd;
        this.dmg = dmg;
        this.maxLvl = maxLvl;
        this.currLvl = lvl;
        this.rng = rng;
        this.player = player;
        this.color = player.getColor();
        this.team = player.getPlayerNumber();
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
        int resHp = tar.currHp - this.dmg * this.atkSpd;
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
        //level check done in Engine
        //if (this.currLvl < this.maxLvl) {
            ++this.currLvl;
            //double to int conversion: floor (decimals lost)
            double modif = 1 + (double)1/currLvl;
            this.upPrice *= modif;
            this.maxHp *= modif;
            this.atkSpd *= modif;
            this.dmg *= modif;
        //}
    }

    public boolean isBuildingType() {
        String t = this.type.name();
        return t.equals("WORKERSPAWN") || t.equals("MILITARYSPAWN") || t.equals("FARM") || t.equals("HARVESTCENTER")
                || t.equals("GOLDMINE") || t.equals("STONEMINE") || t.equals("TURRET") || t.equals("BARRICADE");
    }

    public boolean isHumanType() {
        return !isBuildingType();
    }

    public boolean isUpgradeable() {
        String t = this.type.name();
        return t.equals("INFANTRY") || t.equals("CAVALRY") || t.equals("RANGER")
               || t.equals("DESTROYER") || t.equals("MEDIC") || t.equals("BARRICADE") || t.equals("TURRET");
    }
    
    public boolean isHarvesting() {
        String t = this.type.name();
        return t.equals("FARM") || t.equals("GOLDMINE") || t.equals("STONEMINE");
    }

    ///// getters, setters

    public int getTeam() {
        return player.getPlayerNumber();
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
        return new Point(ghostBuilding.getReferenceCoords().x, ghostBuilding.getReferenceCoords().y - 1);
    }

    public int getAtkSpd() {
        return atkSpd;
    }

    public int getRng() {
        return rng;
    }

    public int getUpPrice() {
        return upPrice;
    }

    public int getMaxLvl() {
        return maxLvl;
    }

    public int getCurrLvl() {
        return currLvl;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;
import visual.ground.Ground;
import visual.ground.Water;
import visual.unit.*;

/**
 *
 * @author WB
 */
public class Engine
{
    private Map map;
    private Player[] players;
    private Unit inspectedUnit;

    /**
     * create Engine
     * @param map
     * @param players 
     */
    public Engine(Map map, Player[] players) {
        this.map = map;
        this.players = players;
    }
    
    /**
     * inspects Unit at given location
     * @param location 
     */
    public void inspectUnit(Point location) {
        //DISPLAY UNIT INFO? ACTIONS
        inspectedUnit = map.groundAt(location).getOwnerReference();
        inspectedUnit.inspect();
    }
    
    ///// Controllable actions
    
    /**
     * one step of iteration on ALL ongoing actions
     */
    public void actionIteration() {
        for (Player p : players) 
        {
            for (Controllable cont : p.getPlayerUnits()) {
                if (cont.isMoving()) {
                    move(cont);                    
                }
                if (cont.isAttacking()) {
                    attack(cont, cont.getCurrTar());                    
                }
                if (cont.isBuilding()) {
                    build(cont);                    
                }
            }
        }
        cleanUp();
    }
    
    ///// MOVEMENT    
    
    /**
     * initilaizes given Controllable's movement to given location,
     * if given location is valid
     * @param cont
     * @param tarLoc 
     */
    public void startMove(Controllable cont, Point tarLoc) {
        if (!map.groundAt(tarLoc).isOccupied()) {
            LinkedList<Point> path = path(cont.getReferenceCoords(), tarLoc);
            path.add(tarLoc);
            cont.setCurrPath(path);
        } else {
            //DISPLAY FAILURE?
        }
    }
    
    /**
     * stops given Controllable's movement, as if it has reached its destination
     * (therefore the 'same' movement cannot be resumed)
     * @param cont 
     */
    public void stopMove(Controllable cont) {
        cont.clearPath();
    }
    
    /// movement helpers (private)
    
    /**
     * moves given Controllable along its current path
     * @param cont 
     */
    private void move(Controllable cont) {
        if (!cont.getCurrPath().isEmpty()) {
            step(cont, cont.getCurrPath());
            //INCLUDE DROWNING
        } else {
            stopMove(cont);
        }
    }
    
    /**
     * moves given Controllable along given path (just one step)
     * @param cont
     * @param path 
     */
    private void step(Controllable cont, LinkedList<Point> path) {
        Point c = path.pop();
        if (!map.groundAt(c).isOccupied()) { //collision check (blocked path)
            changeLoc(cont, c);
            if (map.groundAt(c) instanceof Water) { // stepped into water
                int playerIndex = cont.getTeam();
                players[playerIndex].addDeadControllable(cont);
                map.groundAt(cont.getReferenceCoords()).setOwnerReference(null);
            }
        } else {
            stopMove(cont); //hit an obstacle (in move())
            stopAttack(cont); //hit an obstacle (in attack())
            stopBuild(cont); //hit an obstacle (in build())
        }    
    }
    
    /**
     * returns the Points of a path bewteen Points a and b (a and b not included)
     * @param a: first point (start)
     * @param b: last point (end)
     * @return 
     */
    public LinkedList<Point> path(Point a, Point b) {
        int ax = a.x;   int ay = a.y;
        int bx = b.x;   int by = b.y;
        int xdist = bx - ax;    int xdir = (int) Math.signum(xdist);
        int ydist = by - ay;    int ydir = (int) Math.signum(ydist);
        
        LinkedList<Point> pathPoints = new LinkedList<>();
        while (xdist > 1 || ydist > 1) {
            if (xdist <= ydist) {
                ax += xdir;
                xdist -= xdir;
            } else {
                ay += ydir;
                ydist -= ydir;
            }
            pathPoints.add(new Point(ax, ay));
        }
        return pathPoints;
    }
    
    /**
     * changes given Controllables current location to given target location.
     * also handles ground ownerReferences in the map
     * @param cont: Controllable being moved
     * @param tarLoc: target location 
     */
    private void changeLoc(Controllable cont, Point tarLoc) {
        Point currLoc = cont.getReferenceCoords();
        cont.setReferenceCoords(tarLoc);
        map.groundAt(currLoc).setOwnerReference(null);
        map.groundAt(tarLoc).setOwnerReference(cont);
    }
    
    ///// COMBAT
    
    /**
     * initializes combat between attacker Controllable and Unit at target location,
     * if selected target exists and is valid
     * @param atkr 
     * @param tarLoc 
     */
    public void startAttack(Controllable atkr, Point tarLoc) {
        Unit tar = map.groundAt(tarLoc).getOwnerReference();
        if (atkr.isValidTarget(tar)) {
            atkr.setCurrTar(tar);
            if (!inRange(atkr, tar) && !(atkr instanceof Turret)) {
                startMove(atkr, tar.getReferenceCoords());
            }
        }        
    }
    
    /**
     * stops given Controllable's attacks
     * @param atkr 
     */
    public void stopAttack(Controllable atkr) {
        atkr.setCurrTar(null);
        stopMove(atkr);
    }
    
    /// combat helpers (private)
    
    /**
     * given Controllable attempts to attack given Unit
     * @param atkr: attacker
     * @param tar: target
     */
    private void attack(Controllable atkr, Unit tar) {
        if (inRange(atkr, tar)) { //in range
            stopMove(atkr);
            if (losCheck(atkr, tar)) { //in line of sight
                atkr.hit(tar);
                selfDef(atkr, tar);
            } else { //blocked view
                stopAttack(atkr);
            }
        }      
    }
    
    private void selfDef(Controllable atkr, Unit tar) {
        if (tar.getCurrHp() > 0) { //tar alive after being hit
            if (tar instanceof Controllable) {
                Controllable tarCont = (Controllable)tar;
                if (tarCont.getDmg() > 0 && !tarCont.isAttacking()) {     
                    startAttack(tarCont, atkr.getReferenceCoords());
                }
                if (atkr.getCurrHp() <= 0) { //atkr dead after defense
                    stopAttack(tarCont); 
                    deathEvent(atkr);
                }
            }                    
        } else { //target died
            stopAttack(atkr);                    
            deathEvent(tar);
        }
    }
    
    /**
     * returns whether target Unit is in attacker Controllable's line of sight.
     * incorrect in some cases (only checks reference coordinates)
     * @param atkr: attacker
     * @param tar: target
     * @return 
     */
    public boolean losCheck(Controllable atkr, Unit tar) {
        LinkedList<Point> pathPoints = path(atkr.getReferenceCoords(), tar.getReferenceCoords());
        for (Point p : pathPoints) {
            if (!seeThrough(atkr, map.groundAt(p))) {
                return false;
            }
        }
        return true;
    }   
    
    private boolean seeThrough(Controllable cont, Ground g) {
        if (!g.isOccupied()) {
            return true;
        }
        Unit u = g.getOwnerReference();
        return u instanceof Controllable 
               && ((Controllable)u).isHuman()
               && ((Controllable)u).getTeam() == cont.getTeam();
    }
    
    /**
     * returns whether target Unit is in attacker Controllable's range
     * @param attacker
     * @param target
     * @return 
     */
    public boolean inRange(Controllable attacker, Unit target) {
        Rectangle targetBodyRect = new Rectangle(target.getReferenceCoords().x, target.getReferenceCoords().y, target.getWidth(), target.getHeight());
        return attacker.getRngRect().intersects(targetBodyRect);      
    }
    
    // death
    
    public void deathEvent(Unit u) {
        //ADD DROPS
        if (u instanceof Controllable) {
            int playerIndex = ((Controllable) u).getTeam();
            players[playerIndex].addDeadControllable((Controllable) u);
            for (Point c : u.getCoordsArray()) { //delete unit from the map
                map.groundAt(c).setOwnerReference(null);
            }
        } else { // u was Tree
            map.remTree(u.getReferenceCoords());
        }
    }
    
    private void cleanUp() {
        for (Player p : players) {
            p.remDead();
        }
    }
    
    ///// BUILDING
    
    /**
     * sends given Builder to build given building, at given location,
     * if given location is valid
     * @param builder
     * @param buildingRefCoords
     * @param buildingType 
     */
    public void startBuild(Controllable builder, Point buildingRefCoords, String buildingType) {
        if (map.groundAt(buildingRefCoords).isFreeOrTree() && !(map.groundAt(buildingRefCoords).getType().equals("water")) //refCoords free, not water
            && areaAvailable(buildingRefCoords, buildingType, builder.getTeam())) { //area free, not water
            
            Point builderTarLoc = new Point(buildingRefCoords.x - 1, buildingRefCoords.y);
            if (!map.groundAt(builderTarLoc).isOccupied()){ //builder target position free
                builder.setGhostBuilding(ghostBuilding(buildingRefCoords, buildingType, builder.getTeam()));
                if (!builder.getReferenceCoords().equals(builderTarLoc)) {
                    startMove(builder, builderTarLoc);
                }
            }
        }
    }
    
    /**
     * stops given Builder's building process
     * @param builder 
     */
    public void stopBuild(Controllable builder) {
        builder.setGhostBuilding(null);
        stopMove(builder);
    }
    
    /**
     * rotates ghost Barricade if inspected unit is Builder,
     * with Barricade selected but not yet placed, 
     */
    //UNUSED
    /*
    public void rotateGhostBarricade() 
    {
        if (inspectedUnit instanceof Builder) {
            Builder b = (Builder)inspectedUnit;
            Controllable building = b.getGhostBuilding();
            //REVISIT: could be in the process of building (if its made not instant)
            if (building instanceof Barricade && b.isBuilding() && !b.isMoving()) {
                ((Barricade)building).rotate();
            }            
        }
    }*/
    
    /// building helpers (private)
    
    private void build(Controllable builder) {
        if (builder.getReferenceCoords().equals(builder.getBuilderTarLoc())) {
            for (Point c : builder.getGhostBuilding().getCoordsArray()) {
                map.groundAt(c).setOwnerReference(builder.getGhostBuilding());
                payForUnit(players[builder.getTeam()], builder.getGhostBuilding());
                stopBuild(builder);
            }
        }
    }
    
    //REVISIT
    private boolean areaAvailable(Point p, String type, int team) {
        boolean b = true;
        for (Point c : ghostBuilding(p, type, team).getCoordsArray()) {
            if (!map.groundAt(c).isFreeOrTree() || map.groundAt(c).getType().equals("water")) {
                b = false;
            }
        }
        return b || mineGroundCheck(p, type, team);
    }
    
    /**
     * additional ground condition check for Mines
     * @param refCoords
     * @param type
     * @param team
     * @return 
     */
    private boolean mineGroundCheck(Point refCoords, String type, int team) {
        if (type.equals("stonemine") || type.equals("goldmine")) {
            Mine mine = (Mine)ghostBuilding(refCoords, type, team);
            return mGC_helper(mine);
        }
        return true;
    }
    
    //REVISIT
    private boolean mGC_helper(Mine mine) {
        if (mine instanceof StoneMine) { //stone
            for (Point c : mine.getCoordsArray()) {
                if (map.adjMineralCheck(c, "stone")) {
                    return true;
                }
            }
        } else { //gold
            for (Point c : mine.getCoordsArray()) {
                if (map.adjMineralCheck(c, "gold")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * create building without binding it to the Map
     * (Ground ownerReferences remain unchanged)
     * @param p
     * @param type
     * @param team
     * @return 
     */
    private Controllable ghostBuilding(Point p, String type, int team) {
        Controllable cont = null;
        switch(type) {
            case "workerspawn":
                cont = new WorkerSpawn(p, team);
                break;
            case "militaryspawn":
                cont = new MilitarySpawn(p, team);
                break;
            case "farm":
                cont = new Farm(p, team);
                break;
            case "harvestcenter":
                cont = new HarvestCenter(p, team);
                break;
            case "goldmine":
                cont = new GoldMine(p, team);
                break;
            case "stonemine":
                cont = new StoneMine(p, team);
                break;
            case "turret":
                cont = new Turret(p, team);
                break;    
            case "barricade":
                cont = new Barricade(p, team);
                break;  
        }    
        return cont;
    }
    
    ///// CONTROLLABLE CREATION HELPER
    
    private void payForUnit(Player p, Controllable cont) {
        HashMap<String, Integer> contPrice = getPriceOfCont(cont);
        p.getResourceMap().replaceAll((key, value) -> value - contPrice.get(key));
    }   
    
    //dont look at this unless you like brute force YIKES
    private HashMap<String, Integer> getPriceOfCont(Controllable cont) {
        HashMap<String, Integer> price = null;
        String type = cont.getType();
        switch (type) { //buildings
            case "workerspawn":
                price = WorkerSpawn.price;
                break;
            case "militaryspawn":
                price = MilitarySpawn.price;
                break;
            case "farm":
                price = Farm.price;
                break;
            case "harvestcenter":
                price = HarvestCenter.price;
                break;
            case "goldmine":
                price = GoldMine.price;
                break;
            case "stonemine":
                price = StoneMine.price;
                break;
            case "turret":
                price = Turret.price;
                break;    
            case "barricade":
                price = Barricade.price;
                break;  
        }
        switch (type) { //mobiles
            case "lumberjack":
                price = Lumberjack.price;
                break;
            case "miner":
                price = Miner.price;
                break;
            case "builder":
                price = Builder.price;
                break;
            case "infantry":
                price = Infantry.price;
                break;
            case "cavalry":
                price = Cavalry.price;
                break;
            case "ranger":
                price = Ranger.price;
                break;
            case "destroyer":
                price = Destroyer.price;
                break;    
            case "medic":
                price = Medic.price;
                break;  
        }
        return price;
    }
            
    ///// getters, setters

    public Map getMap() {
        return map;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Unit getInspectedUnit() {
        return inspectedUnit;
    }
    
}

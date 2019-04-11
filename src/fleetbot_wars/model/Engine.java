/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import visual.ground.Ground;
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
                move(cont);
                attack(cont);
                build(cont);
            }
        }
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
            cont.setMoving(true);
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
        cont.setMoving(false);
        cont.clearPath();
    }
    
    /// movement helpers (private)
    
    /**
     * moves given Controllable along its current path
     * @param cont 
     */
    private void move(Controllable cont) {
        if (cont.isMoving()) {
            if (!cont.getCurrPath().isEmpty()) {
                step(cont, cont.getCurrPath());
                //INCLUDE DROWNING
            } else {
                cont.setMoving(false);
            }
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
            atkr.setAttacking(true);
            atkr.setCurrTar(tar);
        }        
    }
    
    /**
     * stops given Controllables attacks
     * @param atkr 
     */
    public void stopAttack(Controllable atkr) {
        atkr.setAttacking(false);
        atkr.setCurrTar(null);
        stopMove(atkr);
    }
    
    /**
     * given Controllable attempts to attack given Unit
     * @param atkr: attacker
     * @param tar: target
     */
    public void attack(Controllable atkr, Unit tar) {
        //REPEAT UNTIL DEATH/STOP COMMAND (in GUI?)
        //MOVE WITHIN: AUTO FOLLOW
        if(atkr.isValidTarget(tar)) { //target is valid
            if (inRange(atkr, tar)) { //target is in range
                if (losCheck(atkr, tar)) { //target is in line of sight
                    atkr.hit(tar);
                }
            } else {
                //GOES POINT BLANK, SHOULD ONLY GET IN RANGE
                if (!(atkr instanceof Turret)) {
                    step(atkr, path(atkr.getReferenceCoords(), tar.getReferenceCoords()));
                }
            }
        }
    }
    
    ///// BUILDING
    
    public void startBuild(Controllable cont) {
        
    }
    
    public void stopBuild(Controllable cont) {
        
    }
    
    private void build(Controllable cont) {
        
    }
        
    ///// main methods
            
    
       
    /**
     * attempts to build given building at given reference coordinates
     * @param builder
     * @param buildingRefCoords
     * @param buildingType 
     */
    public void build(Controllable builder, Point buildingRefCoords, String buildingType) {
        if (!map.groundAt(buildingRefCoords).isOccupied()) {
            //MOUSEOVER CHECK WOULD BE NICE
            if (areaAvailable(buildingRefCoords, buildingType, builder.getTeam())) {
                //HIGHLIGHT: VALID
                //move Builder to LEFT of target location
                //(in range to build, building won't appear on top of it)
                Point builderLoc = new Point(buildingRefCoords.x - 1, buildingRefCoords.y);
                if (!builder.getReferenceCoords().equals(builderLoc)) {
                    step(builder, path(builder.getReferenceCoords(), builderLoc));                    
                } else {
                    //BARRICADE SHOULD BE ABLE TO BE TOTATED 90°
                    Controllable cont = ghostBuilding(buildingRefCoords, buildingType, builder.getTeam());
                    for (Point c : cont.getCoordsArray()) {
                        map.groundAt(c).setOwnerReference(cont);
                    }
                    builder.setBuilding(false); //finished building
                }
            } else {
                //HIGHLIGHT: INVALID
            }
        } else {
            //SHOW FAIL
        }
    }
    
    ///// helper methods
    
    /// move
    
    
    
    /// attack

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
    
    public void deathEvent(Unit u) {
        //ADD DROPS
        cleanUp(u);
    }
    
    private void cleanUp(Unit u) {
        for (Point c : u.getCoordsArray()) { //delete unit from the map
            map.groundAt(c).setOwnerReference(null);
        }
        if (u instanceof Controllable) { //delete from Player (if Unit was COntrollable)
            Controllable uCont = (Controllable)u;
            int playerIndex = uCont.getTeam();
            players[playerIndex].remControllable(uCont);            
        }
    }
    
    ///build
    
    private boolean areaAvailable(Point p, String type, int team) {
        for (Point c : ghostBuilding(p, type, team).getCoordsArray()) {
            if (map.groundAt(c).isOccupied()) {
                return false;
            }
        }
        return true;
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

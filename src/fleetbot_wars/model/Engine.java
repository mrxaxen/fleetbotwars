/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import visual.ground.Ground;
import visual.unit.Controllable;
import visual.unit.Unit;

/**
 *
 * @author WB
 */
public class Engine
{
    private Map map;
    private Player[] players;

    /**
     * create Engine
     * @param map
     * @param players 
     */
    public Engine(Map map, Player[] players) {
        this.map = map;
        this.players = players;
    }
    
    ///// public methods
    
    /**
     * given Controllable attempts to move to given destination
     * @param cont: moving Controllable
     * @param dest: destination on Map
     */
    public void move(Controllable cont, Point dest) {
        //distance, availibility, blocked path, speed
        //stop on collision
        ArrayList<Point> pathPoints = path(cont.getReferenceCoords(), dest);
        //ITERATE ON PATH POINTS STEP BY STEP
    }
    
    /**
     * given Controllable attacks given Unit
     * @param atkr: attacker
     * @param tar: target
     */
    //LOOP (in GUI?)
    public void attack(Controllable atkr, Unit tar) {
        //REPEAT UNTIL DEATH/STOP COMMAND
        if(atkr.isValidTarget(tar)) { //target is valid
            if (inRange(atkr, tar)) { //target is in range
                if (losCheck(atkr, tar)) { //target is in line of sight
                    atkr.offHit(tar);
                }
            } else {
                move(atkr, tar.getReferenceCoords());
            }
        }
    }
    
    ///// private helper methods

    /**
     * returns whether target Unit is in attacker Controllable's range
     * @param attacker
     * @param target
     * @return 
     */
    private boolean inRange(Controllable attacker, Unit target) {
        //MODIFY IF VISUAL GETS BODYRECT COMPONENT
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
    private boolean losCheck(Controllable atkr, Unit tar) {
        ArrayList<Point> pathPoints = path(atkr.getReferenceCoords(), tar.getReferenceCoords());
        for (Point p : pathPoints) {
            if (!seeThrough(atkr, map.groundAt(p))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * returns the Points of a path bewteen Points a and b
     * @param a: first point (start)
     * @param b: last point (end)
     * @return 
     */
    private ArrayList<Point> path(Point a, Point b) {
        int ax = a.x;   int ay = a.y;
        int bx = b.x;   int by = b.y;
        int xdist = bx - ax;    int xdir = (int) Math.signum(xdist);
        int ydist = by - ay;    int ydir = (int) Math.signum(ydist);
        
        ArrayList<Point> pathPoints = new ArrayList<>();
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
    
    private boolean seeThrough(Controllable cont, Ground g) {
        if (!g.isOccupied()) {
            return true;
        }
        Unit u = g.getOwnerReference();
        return u instanceof Controllable 
               && ((Controllable)u).isHuman()
               && ((Controllable)u).getTeam() == cont.getTeam();
    }
    
    ///// getters, setters

    public Map getMap() {
        return map;
    }
    
}

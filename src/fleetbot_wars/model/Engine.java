/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import fleetbot_wars.model.enums.ResourceType;
import fleetbot_wars.model.enums.VisualType;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import visual.Visual;
import visual.ground.Ground;
import visual.ground.Water;
import visual.unit.*;

/**
 *
 * @author WB
 */
public class Engine {
    private Map map;
    private Player[] players;
    private Unit inspectedUnit;
    private static Engine instance;

    public static Engine getInstance(Map map, Player[] players) {
        if (instance == null) {
            instance = new Engine(map, players);
            return instance;
        } else {
            return instance;
        }
    }

    public static Engine getInstance() throws RuntimeException {
        if (instance != null) {
            return instance;
        } else {
            throw new RuntimeException("Engine not initialized!");
        }
    }

    /**
     * create Engine
     * @param map
     * @param players
     */
    private Engine(Map map, Player[] players) {
        this.map = map;
        this.players = players;
        map.placePlayersOnMap(players);
    }

    // used for testing - Bori
    public Engine(Map map, Player[] players, int mark) {
        this.map = map;
        this.players = players;
        //bind Controllables to map
        for (Player p : players) {
            for (Controllable cont : p.getPlayerUnits()) {
                for (Point c : cont.getCoordsArray()) {
                    map.groundAt(c).setOwnerReference(cont);
                }
            }
        }
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
        for (Player p : players) {
            for (Controllable cont : p.getPlayerUnits()) {
                if (cont.isHarvesting()) {
                    harvest(cont);
                }
                if (cont.isAttacking()) {
                    attack(cont, cont.getCurrTar());
                }

                if (cont.isBuilding()) {
                    Controllable ghostBuilding = cont.getGhostBuilding();
                    if (build(cont)) {
                        System.out.println("Building...");
                        for (int i = 0; i < ghostBuilding.getWidth(); i++) {
                            for (int j = 0; j < ghostBuilding.getHeight(); j++) {
                                Point point = new Point(ghostBuilding.getReferenceCoords().x + j, ghostBuilding.getReferenceCoords().y + i);
                                Translation.getInstance().repaint(point, ghostBuilding, true);
                            }
                        }
                    }

                }
                if (cont.isMoving()) {
                    Translation.getInstance().repaint(cont.getReferenceCoords(),cont,false);
                    move(cont);
                    Translation.getInstance().repaint(cont.getReferenceCoords(),cont,true);
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
    public boolean startMove(Controllable cont, Point tarLoc) {
        if (!map.groundAt(tarLoc).isOccupied()) {
            LinkedList<Point> path = path(cont.getReferenceCoords(), tarLoc);
            path.add(tarLoc);
            cont.setCurrPath(path);
            return true;
        } else {
            //DISPLAY FAILURE?
            return false;
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
        step(cont, cont.getCurrPath());
    }

    /**
     * moves given Controllable along given path (just one step)
     * @param cont
     * @param path
     */
    private void step(Controllable cont, LinkedList<Point> path) {
        Point c = path.removeFirst();
        if (!map.groundAt(c).isOccupied()) { //collision check (blocked path)
            changeLoc(cont, c);
            if (map.groundAt(c) instanceof Water) { // stepped into WATER
                killUnit(cont);
            }
            if (cont.getType().equals(VisualType.MINER)) {
                Miner mr = (Miner)cont;
                tryOccupyMine(mr);
            }
        } else {
            stopMove(cont); //hit an obstacle (in move())
            stopAttack(cont); //hit an obstacle (in attack())
            stopBuild(cont); //hit an obstacle (in build())
        }
        if (path.isEmpty()) { //reached destination
            stopMove(cont);
        }
    }

    /**
     * returns the Points of a path bewteen Points a and b (a and b not included)
     * @param a: first point (start)
     * @param b: last point (end)
     * @return
     */
    private LinkedList<Point> path(Point a, Point b) {
        int ax = a.x;
        int ay = a.y;
        int bx = b.x;
        int by = b.y;
        int xdist = bx - ax;
        int xdir = (int) Math.signum(xdist);
        int ydist = by - ay;
        int ydir = (int) Math.signum(ydist);
        xdist = Math.abs(xdist);
        ydist = Math.abs(ydist);

        LinkedList<Point> pathPoints = new LinkedList<>();
        while (!((xdist == 1 && ydist == 0) || (xdist == 0 && ydist == 1))) {
            if (xdist <= ydist) {
                ay += ydir;
                --ydist;
            } else {
                ax += xdir;
                --xdist;
            }
            pathPoints.add(new Point(ax, ay));
        }
        System.out.println(pathPoints);
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
    public boolean startAttack(Controllable atkr, Point tarLoc) {
        Unit tar = map.groundAt(tarLoc).getOwnerReference();
        if (atkr.isValidTarget(tar)) {
            atkr.setCurrTar(tar);
            if (!inRange(atkr, tar)) {
                if (atkr instanceof Turret) {
                    stopAttack(atkr);
                } else {
                    startMove(atkr, tar);
                }
            }
            return true;
        }
        return false;
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
     * same logic as startMove(Controllable, Unit),
     * without the initial occupation check (will always be occupied by terget)
     * @param cont
     * @param tar
     */
    private void startMove(Controllable cont, Unit tar) {
        LinkedList<Point> path = path(cont.getReferenceCoords(), tar.getReferenceCoords());
        path.add(tar.getReferenceCoords());
        cont.setCurrPath(path);
    }

    /**
     * given Controllable attempts to attack given Unit
     * @param atkr: attacker
     * @param tar: target
     */
    private void attack(Controllable atkr, Unit tar) {
        if (inRange(atkr, tar) && atkr.getCurrHp() > 0) { //in range, not dead (died but not yet removed)
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
                Controllable tarCont = (Controllable) tar;
                if (tarCont.getDmg() > 0 && !tarCont.isAttacking()) {
                    startAttack(tarCont, atkr.getReferenceCoords());
                }
                if (atkr.getCurrHp() <= 0) { //atkr dead after defense
                    stopAttack(tarCont);
                    deathEvent(atkr, ((Controllable) tar).getTeam());
                }
            }
        } else { //target died
            stopAttack(atkr);
            deathEvent(tar, atkr.getTeam());
        }
    }

    /**
     * returns whether target Unit is in attacker Controllable's line of sight.
     * incorrect in some cases (only checks reference coordinates)
     * @param atkr: attacker
     * @param tar: target
     * @return
     */
    private boolean losCheck(Controllable atkr, Unit tar) {
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
                && ((Controllable) u).isHumanType()
                && ((Controllable) u).getTeam() == cont.getTeam();
    }

    /**
     * returns whether target Unit is in attacker Controllable's range
     * @param attacker
     * @param target
     * @return
     */
    private boolean inRange(Controllable attacker, Unit target) {
        //return attacker.getRngRect().intersects(target.getBodyRect());
        //return attacker.getRngRect().contains(target.getReferenceCoords());
        return Math.abs(attacker.getReferenceCoords().x - target.getReferenceCoords().x) <= attacker.getRng()
                && Math.abs(attacker.getReferenceCoords().y - target.getReferenceCoords().y) <= attacker.getRng();
    }

    // death

    /**
     * happens when a Unit's death has additional events tied to it,
     * such as resource gain
     * @param u
     * @param teamBenefiting
     */
    private void deathEvent(Unit u, int teamBenefiting) {
        Player p = players[teamBenefiting];
        if (u instanceof Tree) {
            p.increaseResource(ResourceType.wood, 2);
        } else { //Unit was Controllable
            Controllable cont = (Controllable) u;
            if (cont.isBuildingType()) {
                p.increaseResource(ResourceType.upgrade, 5);
            }
            if (cont.isHumanType()) {
                p.increaseResource(ResourceType.upgrade, 1);
            }
        }
        killUnit(u);
    }

    /**
     * removes Unit from the Map, and owner Player if it was Controllable
     * @param u
     */
    private void killUnit(Unit u) {
        if (u instanceof Controllable) { // doesnt apply to trees
            int playerIndex = ((Controllable) u).getTeam();
            players[playerIndex].addDeadControllable((Controllable) u);
        }
        for (Point c : u.getCoordsArray()) { //delete unit from the map
            Translation.getInstance().repaint(c,null,false);
            map.groundAt(c).setOwnerReference(null);
        }
        if (u instanceof Mine) {
            Mine m = (Mine)u;
            dropMiner(m);
        }
    }

    private void cleanUp() {
        for (Player p : players) {
            p.remDead();
            p.addNew();
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

    public boolean startBuild(Controllable builder, Point buildingRefCoords, VisualType buildingType) {
        if (areaAvailable(buildingRefCoords, buildingType, builder.getPlayer())) { //area free/tree, not water
            System.out.println("Starting to build");
            Point builderTarLoc = new Point(buildingRefCoords.x, buildingRefCoords.y - 1);
            if (!map.groundAt(builderTarLoc).isOccupied()                            // builder target position free,
                    || map.groundAt(builderTarLoc).getOwnerReference().equals(builder)) { // or builder already there
                builder.setGhostBuilding(ghostBuilding(buildingRefCoords, buildingType, builder.getPlayer()));
                if (!builder.getReferenceCoords().equals(builderTarLoc)) {
                    startMove(builder, builderTarLoc);
                }
            }
            return true;
        }
        return false;
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
     * @param p
     * @param contType
     * @return true if given Player has enough resources to create given type Controllable
     */
    public boolean gotResForCont(Player p, VisualType contType) {
        HashMap price = getPriceOfCont(contType);
        for (Entry e : p.getResourceMap().entrySet()) {
            if ((int) price.get(e.getKey()) > (int) e.getValue()) {
                return false;
            }
        }
        return true;
    }

    /// building helpers (private)

    private boolean build(Controllable builder) {
        if (builder.getReferenceCoords().equals(builder.getBuilderTarLoc())) {
            for (Point c : builder.getGhostBuilding().getCoordsArray()) {
                map.groundAt(c).setOwnerReference(builder.getGhostBuilding());
            }
            payForUnit(players[builder.getTeam()], builder.getGhostBuilding());
            players[builder.getTeam()].addNewControllable(builder.getGhostBuilding());
            stopBuild(builder);
            stopMove(builder);
            return true;
        }
        return false;
    }


    private boolean areaAvailable(Point p, VisualType type, Player player) {
        boolean b = true;
        for (Point c : ghostBuilding(p, type, player).getCoordsArray()) {
            try {
                if (!map.groundAt(c).isFreeOrTree() || map.groundAt(c).getType().equals(VisualType.WATER)) {
                    b = false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Checked area extends off the map. (area check)");
                return false;
            }
        }


        if (type.equals(VisualType.STONEMINE) || type.equals(VisualType.GOLDMINE)) {
            b = b && mineGroundCheck(p, type, player);
            System.out.println("Minecheck: " + b);
        }
        return b;
    }

    /**
     * additional ground condition check for Mines
     * @param refCoords
     * @param type
     * @param player
     * @return
     */

    private boolean mineGroundCheck(Point refCoords, VisualType type, Player player) {
        Mine mine = (Mine) ghostBuilding(refCoords, type, player);

        return mGC_helper(mine);
    }

    private boolean mGC_helper(Mine mine) {
        if (mine instanceof StoneMine) { //STONE
            System.out.println("Mineral check success!");
            for (Point c : mine.getCoordsArray()) {
                if (map.adjMineralCheck(c, VisualType.STONE)) {
                    return true;
                }
                if (map.adjMineralCheck(c, VisualType.STONE_1)) {
                    return true;
                }
                if (map.adjMineralCheck(c, VisualType.STONE_2)) {
                    return true;
                }
            }
        } else { //GOLD
            for (Point c : mine.getCoordsArray()) {
                if (map.adjMineralCheck(c, VisualType.GOLD)) {
                    return true;
                }
                if (map.adjMineralCheck(c, VisualType.GOLD_1)) {
                    return true;
                }
                if (map.adjMineralCheck(c, VisualType.GOLD_2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update() {
        HashMap<ResourceType, Integer> resources = new HashMap<>();
        players[Translation.getInstance().getCurrPlayer()].getResourceMap().forEach((key, value) -> {
            resources.put(key, value);
        });
        Translation.getInstance().updateResources(resources);
    }

    /**
     * create building without binding it to the Map
     * (Ground ownerReferences remain unchanged)
     * @param p
     * @param type
     * @param player
     * @return
     */
    private Controllable ghostBuilding(Point p, Enum type, Player player) {
        Controllable cont = null;
        String typeString = type.name();
        switch (typeString) {
            case "WORKERSPAWN":
                cont = new WorkerSpawn(p, player);
                break;
            case "MILITARYSPAWN":
                cont = new MilitarySpawn(p, player);
                break;
            case "FARM":
                cont = new Farm(p, player);
                break;
            case "HARVESTCENTER":
                cont = new HarvestCenter(p, player);
                break;
            case "GOLDMINE":
                cont = new GoldMine(p, player);
                break;
            case "STONEMINE":
                cont = new StoneMine(p, player);
                break;
            case "TURRET":
                cont = new Turret(p, player);
                break;
            case "BARRICADE":
                cont = new Barricade(p, player);
                break;
        }
        return cont;
    }

    ///// SPAWNING

    /**
     * attempts to spawn given type Controllable below, and if unavailable,
     * above the middle of given spawn building
     * @param building
     * @param humanType
     */
    public void spawn(Controllable building, VisualType humanType) {
        //display buttons only for valid options in GUI
        Point hrc = spawnPlace(building);
        if (hrc != null) {
            Controllable cont = ghostHuman(hrc, humanType, building.getPlayer());
            map.groundAt(hrc).setOwnerReference(cont);
            payForUnit(players[building.getTeam()], cont);
            players[building.getTeam()].addNewControllable(cont);
        }
    }

    /// spawning helpers (private)

    private Point spawnPlace(Controllable building) {
        Point brc = building.getReferenceCoords();
        Point hrc = new Point(brc.x + 2, brc.y + 1); //below middle of 3x2 spawn
        try {
            if (!map.groundAt(hrc).isOccupied()) {
                return hrc;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        hrc = new Point (brc.x - 1, brc.y + 1); //above middle of 3x2 spawn
        try {
            if (!map.groundAt(hrc).isOccupied()) {
                return hrc;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        return null;
    }

    /**
     * create building without binding it to the Map
     * (Ground ownerReferences remain unchanged)
     * @param p
     * @param type
     * @param team
     * @return
     */
    private Controllable ghostHuman(Point p, Enum type, Player team) {
        Controllable cont = null;
        String typeString = type.name();
        switch(typeString) {
            case "BUILDER":
                cont = new Builder(p, team);
                break;
            case "LUMBERJACK":
                cont = new Lumberjack(p, team);
                break;
            case "MINER":
                cont = new Miner(p, team);
                break;
            case "INFANTRY":
                cont = new Infantry(p, team);
                break;
            case "CAVALRY":
                cont = new Cavalry(p, team);
                break;
            case "RANGER":
                cont = new Ranger(p, team);
                break;
            case "DESTROYER":
                cont = new Destroyer(p, team);
                break;
            case "MEDIC":
                cont = new Medic(p, team);
                break;
        }
        return cont;
    }

    ///// HARVESTING

    /// harvesting helpers (private)

    private void harvest(Controllable cont) {
        VisualType contType = cont.getType();
        if (contType.equals(VisualType.FARM)) {
            Farm f = (Farm)cont;
            f.incrFood(players[f.getTeam()]);
        }
        if (cont instanceof Mine) {
            Mine m = (Mine)cont;
            m.incrRes(players[m.getTeam()]);
        }
    }

    private void tryOccupyMine(Miner mr) {
        Mine m = map.adjMineCheck(mr);
        if (m != null) {
            stopMove(mr);
            m.setMiner(mr);
            //miner 'disappears' into the building:
            //won't be directly bound to the map, just the building.
            map.groundAt(mr.getReferenceCoords()).setOwnerReference(null);
        }
    }

    private void dropMiner(Mine m) {
        Point mrc = m.getReferenceCoords();
        Miner mr = m.getMiner();
        map.groundAt(mrc).setOwnerReference(mr);
    }

    ///// UPGRADE

    /**
     * increase the level of given Controllable by 1,
     * subsequently increasing its stats
     * @param cont
     */
    public void upgrade(Controllable cont) {
        if (gotResForUpgr(cont)) {
            Player p = players[cont.getTeam()];
            payForUpgr(p, cont);
            cont.upgrade();
        }
    }

    /**
     * tells whether given Controllable can be upgraded
     * @param cont
     * @return true if Controllable is upgradeable AND Player has enough resources,
     * false else
     */
    public boolean gotResForUpgr(Controllable cont) {
        if (cont.isUpgradeable() && cont.getMaxLvl() > cont.getCurrLvl()) {
            Player p = players[cont.getTeam()];
            int pUp = p.getResourceByName(ResourceType.upgrade);
            if (pUp >= cont.getUpPrice()) {
                return true;
            }
        }
        return false;
    }
    /// upgrade helpers (private)

    private void payForUpgr(Player p, Controllable cont) {
        int upPr = cont.getUpPrice();
        int pUpRes = p.getResourceByName(ResourceType.upgrade);
        p.getResourceMap().replace(ResourceType.upgrade, pUpRes - upPr);
    }

    ///// CONTROLLABLE CREATION HELPERS (private)

    private void payForUnit(Player p, Controllable cont) {
        HashMap<ResourceType, Integer> contPrice = getPriceOfCont(cont.getType());
        p.getResourceMap().replaceAll((key, value) -> value - contPrice.get(key));

    }

    //dont look at this unless you like brute force YIKES
    private HashMap<ResourceType, Integer> getPriceOfCont(VisualType type) {
        HashMap<ResourceType, Integer> price = null;
        switch (type) { //buildings
            case WORKERSPAWN:
                price = WorkerSpawn.price;
                break;
            case MILITARYSPAWN:
                price = MilitarySpawn.price;
                break;
            case FARM:
                price = Farm.price;
                break;
            case HARVESTCENTER:
                price = HarvestCenter.price;
                break;
            case GOLDMINE:
                price = GoldMine.price;
                break;
            case STONEMINE:
                price = StoneMine.price;
                break;
            case TURRET:
                price = Turret.price;
                break;
            case BARRICADE:
                price = Barricade.price;
                break;
            case LUMBERJACK:
                price = Lumberjack.price;
                break;
            case MINER:
                price = Miner.price;
                break;
            case BUILDER:
                price = Builder.price;
                break;
            case INFANTRY:
                price = Infantry.price;
                break;
            case CAVALRY:
                price = Cavalry.price;
                break;
            case RANGER:
                price = Ranger.price;
                break;
            case DESTROYER:
                price = Destroyer.price;
                break;
            case MEDIC:
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

}

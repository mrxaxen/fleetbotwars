/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import fleetbot_wars.model.enums.VisualType;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import visual.ground.*;
import visual.unit.*;

/**
 *
 * @author 3rd
 */
public class EngineTest {
    
    ///// ENGINE METHODS
    
    @Test
    public void testInspectUnit() {
        
    }

    @Test
    public void testActionIteration() {
        //tested in 3 parts:
        //testIteration_movement()
        //testIteration_combat()
        //testIteration_building()
    }

    /// movement   
    
    // B B B B
    // B B B W
    // B B B B
    // B S B i
    private Engine movement_engine = createMovementEngine();
    private Controllable movement_inf = movement_engine.getPlayers()[0].getPlayerUnits().get(0);
    
    @Test
    public void testStartMove_iterationMove() {
        // invalid target location: S(3,1)
        movement_engine.startMove(movement_inf, new Point(3, 1));
        assertFalse(movement_inf.isMoving()); //is moving == current path not empty
        
        // valid but will kill you: W(1, 3);
        movement_engine.startMove(movement_inf, new Point(1, 3));
        assertTrue(movement_inf.isMoving()); 
        
        movement_engine.stopMove(movement_inf);
        // valid
        movement_engine.startMove(movement_inf, new Point(0, 1));
        assertTrue(movement_inf.isMoving()); 
        
        // reset state of movement_engine
        movement_engine.stopMove(movement_inf);
    }

    @Test
    public void testStopMove() {
        //only a clearPath() call,
        //clearPath() tested
    }
    
    @Test
    public void testIteration_movement() {
        movement_engine.startMove(movement_inf, new Point(0, 1));
        assertTrue(movement_inf.isMoving()); 
        
        // check path
        LinkedList<Point> path = movement_inf.getCurrPath();
        assertEquals(new Point(2, 3), path.removeFirst());
        assertEquals(new Point(2, 2), path.removeFirst());
        assertEquals(new Point(1, 2), path.removeFirst());
        assertEquals(new Point(1, 1), path.removeFirst());
        assertEquals(new Point(0, 1), path.removeFirst());
        // reset path
        path.add(new Point(2, 3));
        path.add(new Point(2, 2));
        path.add(new Point(1, 2));
        path.add(new Point(1, 1));
        path.add(new Point(0, 1));
        
        // check one (1) iteration step    
        // default player units bound to map (Engine(Map,Players[],int))
        assertTrue(movement_engine.getMap().groundAt(new Point(3, 3)).isOccupied());        
        assertEquals(movement_inf, movement_engine.getMap().groundAt(new Point(3, 3)).getOwnerReference());
        // move it
        movement_engine.actionIteration();
        Point p = new Point(2, 3);
        assertEquals(p, movement_inf.getReferenceCoords());
        assertEquals(movement_inf, movement_engine.getMap().groundAt(p).getOwnerReference());
        assertFalse(movement_engine.getMap().groundAt(new Point(3, 3)).isOccupied()); //this works
        assertTrue(movement_inf.isMoving());
        // reset position
        // also checks reaching destination
        movement_engine.stopMove(movement_inf);
        movement_engine.startMove(movement_inf, new Point(3, 3));
        assertTrue(movement_inf.isMoving());
        movement_engine.actionIteration();
        assertFalse(movement_inf.isMoving());
        
        // blocked path
        movement_engine.startMove(movement_inf, new Point(3, 0));
        movement_engine.actionIteration();
        assertTrue(movement_inf.isMoving());
        movement_engine.actionIteration();
        assertEquals(new Point(3, 2), movement_inf.getReferenceCoords());
        assertFalse(movement_inf.isMoving());
        // reset position
        movement_engine.startMove(movement_inf, new Point(3, 3));        
        movement_engine.actionIteration();
        
        // walked into water
        movement_engine.startMove(movement_inf, new Point(0, 3));                
        movement_engine.actionIteration();        
        movement_engine.actionIteration();
        assertTrue(movement_engine.getPlayers()[0].getPlayerUnits().isEmpty());
        assertFalse(movement_engine.getMap().groundAt(new Point(1, 3)).isOccupied());
    }
    
    /// combat

    @Test
    public void testStartAttack() {
        
    }

    @Test
    public void testStopAttack() {
        
    }

    /// building
    
    @Test
    public void testStartBuild() {
        
    }

    @Test
    public void testStopBuild() {
        
    }

    @Test
    public void testGotResForCont() {
        
    }

    ///// ENGINE HELPERS
    
    /// Ground:
    
    @Test
    public void testIsOccupied() {
        Ground base = new Base(new Point(1, 1));
        Ground treeBase = new Base(new Point(1, 1));
        treeBase.setOwnerReference(new Tree(new Point(1, 1)));
        Ground water = new Water(new Point(1, 1));
        Ground gold = new Gold(new Point(1, 1), 1);
        Ground stone = new Stone(new Point(1, 1), 1);
        Ground mountain = new Mountain(new Point(1, 1));
        
        assertFalse(base.isOccupied());
        assertTrue(treeBase.isOccupied());
        assertFalse(water.isOccupied());
        assertTrue(gold.isOccupied());
        assertTrue(stone.isOccupied());
        assertTrue(mountain.isOccupied());
    }
    
    @Test
    public void testIsFreeOrTree() {
        Ground base = new Base(new Point(1, 1));
        Ground treeBase = new Base(new Point(1, 1));
        treeBase.setOwnerReference(new Tree(new Point(1, 1)));
        Ground water = new Water(new Point(1, 1));
        Ground gold = new Gold(new Point(1, 1), 1);
        Ground stone = new Stone(new Point(1, 1), 1);
        Ground mountain = new Mountain(new Point(1, 1));
        
        assertTrue(base.isFreeOrTree());
        assertTrue(treeBase.isFreeOrTree());
        assertTrue(water.isFreeOrTree());
        assertFalse(gold.isFreeOrTree());
        assertFalse(stone.isFreeOrTree());
        assertFalse(mountain.isFreeOrTree());
    }
    
    /// Map:
        
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    
    @Test
    public void testAdjMineralCheck() {
        Ground[][] ground = new Ground[3][6];
        // B B G B B B
        // B B B B B B
        // B B S B B B
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 6; ++j) {
                ground[i][j] = new Base(new Point(i, j));
            }
        }
        ground[0][2] = new Gold(new Point(0, 2), 1);
        ground[2][2] = new Stone(new Point (2, 2), 1);
        Map map = new Map(ground);
        
        assertTrue(map.adjMineralCheck(new Point(1, 1), VisualType.stone));
        assertTrue(map.adjMineralCheck(new Point(1, 1), VisualType.gold));
        assertFalse(map.adjMineralCheck(new Point(1, 4), VisualType.stone));
        assertFalse(map.adjMineralCheck(new Point(1, 4), VisualType.gold));
        //doesnt like println :S
        map.adjMineralCheck(new Point(1, 5), VisualType.gold);
        assertEquals("Checked area extends off the map.", outContent.toString());
    }
    
    // Controllable:
    
    // isHumanType() is its negation: if this works, that works too
    @Test
    public void testIsBuildingType() {
        Controllable barricade = new Barricade(new Point(1, 1), 1);
        Controllable farm = new Farm(new Point(1, 1), 1);
        Controllable goldmine = new GoldMine(new Point(1, 1), 1);
        Controllable harvestcenter = new HarvestCenter(new Point(1, 1), 1);
        Controllable militaryspawn = new MilitarySpawn(new Point(1, 1), 1);
        Controllable stonemine = new StoneMine(new Point(1, 1), 1);
        Controllable turret = new Turret(new Point(1, 1), 1);
        Controllable workerspawn = new WorkerSpawn(new Point(1, 1), 1);
        
        Controllable builder = new Builder(new Point(1, 1), 1);
        Controllable cavalry = new Cavalry(new Point(1, 1), 1);
        Controllable destroyer = new Destroyer(new Point(1, 1), 1);
        Controllable infantry = new Infantry(new Point(1, 1), 1);
        Controllable lumberjack = new Lumberjack(new Point(1, 1), 1);
        Controllable medic = new Medic(new Point(1, 1), 1);
        Controllable miner = new Miner(new Point(1, 1), 1);
        Controllable ranger = new Ranger(new Point(1, 1), 1);
        
        assertTrue(barricade.isBuildingType());
        assertTrue(farm.isBuildingType());
        assertTrue(goldmine.isBuildingType());
        assertTrue(harvestcenter.isBuildingType());
        assertTrue(militaryspawn.isBuildingType());
        assertTrue(stonemine.isBuildingType());
        assertTrue(turret.isBuildingType());
        assertTrue(workerspawn.isBuildingType());
        
        assertFalse(builder.isBuildingType());
        assertFalse(cavalry.isBuildingType());
        assertFalse(destroyer.isBuildingType());
        assertFalse(infantry.isBuildingType());
        assertFalse(lumberjack.isBuildingType());
        assertFalse(medic.isBuildingType());
        assertFalse(miner.isBuildingType());
        assertFalse(ranger.isBuildingType());        
    }
    
    @Test
    public void testIsValidTarget() {
        //isValidTarget has 4 versions implemented,
        //the default false wont be called
        Controllable turret = new Turret(new Point(1, 1), 1);
        Controllable destroyer = new Destroyer(new Point(1, 1), 1);
        Controllable infantry = new Infantry(new Point(1, 1), 1);
        Controllable lumberjack = new Lumberjack(new Point(1, 1), 1);
        Controllable medic = new Medic(new Point(1, 1), 1);
        
        Controllable e_turret = new Turret(new Point(1, 1), 2);
        Controllable e_infantry = new Infantry(new Point(1, 1), 2);
        Unit tree = new Tree(new Point(1, 1));
        
        assertTrue(destroyer.isValidTarget(e_turret)); //enemy building
        assertFalse(destroyer.isValidTarget(turret)); //friendly building
        assertFalse(destroyer.isValidTarget(e_infantry)); //enemy human
        assertFalse(destroyer.isValidTarget(infantry)); //friendly human
        assertFalse(destroyer.isValidTarget(tree)); //tree

        assertTrue(infantry.isValidTarget(e_turret));
        assertFalse(infantry.isValidTarget(turret));
        assertTrue(infantry.isValidTarget(e_infantry));
        assertFalse(infantry.isValidTarget(medic));
        assertFalse(infantry.isValidTarget(tree));
        
        assertFalse(lumberjack.isValidTarget(e_turret));
        assertFalse(lumberjack.isValidTarget(turret));
        assertFalse(lumberjack.isValidTarget(e_infantry));
        assertFalse(lumberjack.isValidTarget(infantry));
        assertTrue(lumberjack.isValidTarget(tree));
        
        assertFalse(medic.isValidTarget(e_turret));
        assertTrue(medic.isValidTarget(turret));
        assertFalse(medic.isValidTarget(e_infantry));
        assertTrue(medic.isValidTarget(infantry));
        assertFalse(medic.isValidTarget(tree));
    }
    
    @Test
    public void testHit() {
        Controllable infantry1 = new Infantry(new Point(1, 1), 1);
        Controllable infantry2 = new Infantry(new Point(1, 1), 2);
        int hp2 = infantry2.getCurrHp(); //100
        int dmg1 = infantry1.getDmg(); //10
        int atkSpd1 = infantry1.getAtkSpd(); //1
        
        int res = hp2 - dmg1 * atkSpd1; //90
        infantry1.hit(infantry2);
        assertEquals(res, infantry2.getCurrHp());        
        assertEquals(90, infantry2.getCurrHp());
        
        Controllable medic2 = new Medic(new Point(1, 1), 2);
        int heal2 = medic2.getDmg(); //-10
        int healSpd2 = medic2.getAtkSpd(); //1
        
        infantry1.hit(infantry2);        
        hp2 = infantry2.getCurrHp(); //80
        medic2.hit(infantry2);
        res = hp2 - heal2 * healSpd2; //90
        assertEquals(res, infantry2.getCurrHp());
        assertEquals(90, infantry2.getCurrHp());
        
        medic2.hit(infantry2); //100
        medic2.hit(infantry2); //110 -> 100        
        assertEquals(100, infantry2.getCurrHp());
    }
    
    @Test
    public void testClearPath() {
        Controllable inf = new Infantry(new Point(1, 1), 1);
        LinkedList<Point> path = new LinkedList<>();
        path.add(new Point(1, 2));
        inf.setCurrPath(path);
        inf.clearPath();
        assertTrue(inf.getCurrPath().isEmpty());
    }
    
    ///// TEST HELPER ENGINES
    
    public Engine createMovementEngine() {
        Ground[][] movement_ground = new Ground[4][4];
        // B B B B
        // B B B W
        // B B B B
        // B S B B
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                movement_ground[i][j] = new Base(new Point(i, j));
            }
        }
        movement_ground[1][3] = new Water(new Point(1, 3));
        movement_ground[3][1] = new Stone(new Point(3, 1), 1);
        Map movement_map = new Map(movement_ground);
        
        Player[] players = new Player[1];
        Player jane = new Player("jane_doe", 0);
        jane.addControllable(new Infantry(new Point(3, 3), jane.getPlayerNumber()));
        players[0] = jane;
        
        Engine movement_engine = new Engine(movement_map, players, 420);
        
        return movement_engine;
    }
    
}

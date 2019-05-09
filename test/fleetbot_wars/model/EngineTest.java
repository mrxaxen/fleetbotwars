/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import fleetbot_wars.model.enums.VisualType;
import fleetbot_wars.model.enums.ResourceType;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import visual.Visual;
import visual.ground.*;
import visual.unit.*;

/**
 *
 * @author 3rd
 */
public class EngineTest {

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

    ///// ENGINE METHODS
    // General Info:
    // action starters only initialize actions, the first 'real' action will happen in the next iteration
    // units can only perform one of the following per iteration: attack, build, move
    // the first one in this order will always be chosen
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
    @Test
    public void testStartMove_iterationMove() {
        // B B B B
        // B B B W
        // B B B B
        // B S B i
        Engine movement_engine = createMovementEngine();
        Controllable movement_inf = movement_engine.getPlayers()[0].getPlayerUnits().get(0);

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
        // B B B B
        // B B B W
        // B B B B
        // B S B i
        Engine movement_engine = createMovementEngine();
        Controllable movement_inf = movement_engine.getPlayers()[0].getPlayerUnits().get(0);

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
        // t0 t0 i0 B r0
        // t0 t0 B  B B 
        // B  B  B  B B
        // t1 t1 i1 B r1
        // t1 t1 B  B B
        Engine attack_engine = createAttackEngine();
        Controllable tur0 = attack_engine.getPlayers()[0].getPlayerUnits().get(0);
        Controllable inf0 = attack_engine.getPlayers()[0].getPlayerUnits().get(1);
        Controllable ran0 = attack_engine.getPlayers()[0].getPlayerUnits().get(2);
        Controllable tur1 = attack_engine.getPlayers()[1].getPlayerUnits().get(0);
        Controllable inf1 = attack_engine.getPlayers()[1].getPlayerUnits().get(1);
        Controllable ran1 = attack_engine.getPlayers()[1].getPlayerUnits().get(2);

        // empty target 
        Point tarLoc = new Point(0, 1);
        attack_engine.startAttack(inf0, tarLoc);
        assertFalse(inf0.isAttacking());

        // valid target, out of range
        //isValidTarget() tested separately
        tarLoc = new Point(3, 4);
        attack_engine.startAttack(tur0, tarLoc);
        attack_engine.startAttack(inf0, tarLoc);
        attack_engine.startAttack(ran0, tarLoc);
        assertFalse(tur0.isAttacking());
        assertFalse(tur0.isMoving());
        assertTrue(inf0.isAttacking());
        assertTrue(inf0.isMoving());
        assertEquals(ran1, inf0.getCurrTar());
        assertTrue(ran0.isAttacking());
        assertTrue(ran0.isMoving());
        assertEquals(ran1, ran0.getCurrTar());
        // reset: tested stopAttack() at this point
        attack_engine.stopAttack(inf0);
        attack_engine.stopAttack(ran0);

    }

    @Test
    public void testStopAttack() {
        // t0 t0 i0 B r0
        // t0 t0 B  B B 
        // B  B  B  B B
        // t1 t1 i1 B r1
        // t1 t1 B  B B
        Engine attack_engine = createAttackEngine();
        Controllable inf0 = attack_engine.getPlayers()[0].getPlayerUnits().get(1);
        Controllable inf1 = attack_engine.getPlayers()[1].getPlayerUnits().get(1);

        attack_engine.startAttack(inf0, inf1.getReferenceCoords());
        attack_engine.stopAttack(inf0);
        assertFalse(inf0.isAttacking());
        assertFalse(inf0.isMoving());
        assertEquals(null, inf0.getCurrTar());
    }

    @Test
    public void testIteration_combat() {
        // t0 t0 i0 B r0
        // t0 t0 B  B B 
        // B  B  B  B B
        // t1 t1 i1 B r1
        // t1 t1 B  B B
        Engine attack_engine = createAttackEngine();
        Controllable inf0 = attack_engine.getPlayers()[0].getPlayerUnits().get(1);
        Controllable inf1 = attack_engine.getPlayers()[1].getPlayerUnits().get(1);
        Controllable ran0 = attack_engine.getPlayers()[0].getPlayerUnits().get(2);
        Controllable ran1 = attack_engine.getPlayers()[1].getPlayerUnits().get(2);

        attack_engine.startAttack(inf0, ran1.getReferenceCoords());
        attack_engine.startAttack(ran0, ran1.getReferenceCoords());
        attack_engine.actionIteration();
        assertEquals(new Point(1, 2), inf0.getReferenceCoords());
        assertEquals(new Point(1, 4), ran0.getReferenceCoords());
        // ran0 got into range, doesnt hit yet
        attack_engine.actionIteration();
        assertFalse(ran0.isMoving());
        assertEquals(90, ran1.getCurrHp());
        assertEquals(90, ran0.getCurrHp());
        // reset (only units used for further testing)
        attack_engine = createAttackEngine();
        inf0 = attack_engine.getPlayers()[0].getPlayerUnits().get(1);
        inf1 = attack_engine.getPlayers()[1].getPlayerUnits().get(1);

        // death test
        attack_engine.startAttack(inf0, inf1.getReferenceCoords());
        assertEquals(new Point(0, 2), inf0.getReferenceCoords());
        attack_engine.actionIteration();
        assertEquals(new Point(1, 2), inf0.getReferenceCoords());
        attack_engine.actionIteration();
        assertEquals(new Point(2, 2), inf0.getReferenceCoords());
        attack_engine.actionIteration();
        assertEquals(90, inf1.getCurrHp());
        assertEquals(90, inf0.getCurrHp());
        // hit 9 more times
        for (int i = 0; i < 9; ++i) {
            attack_engine.actionIteration();
        }
        assertEquals(10, inf0.getCurrHp());
        assertEquals(null, attack_engine.getMap().groundAt(new Point(3, 2)).getOwnerReference());
        assertEquals(2, attack_engine.getPlayers()[1].getPlayerUnits().size());
        // reset

        // LoS check
        // r01 S i11
        //  B  B  B 
        // i12 B  B 
        attack_engine = createAttackEngine_LoS();
        Controllable ran01 = attack_engine.getPlayers()[0].getPlayerUnits().get(0);
        Controllable inf11 = attack_engine.getPlayers()[1].getPlayerUnits().get(0);
        Controllable inf12 = attack_engine.getPlayers()[1].getPlayerUnits().get(1);
        // try to attack target not within LoS
        attack_engine.startAttack(ran01, inf11.getReferenceCoords());
        attack_engine.actionIteration();
        assertEquals(100, inf11.getCurrHp());
        assertFalse(ran01.isAttacking());
        // try to attack target within LoS
        attack_engine.startAttack(ran01, inf12.getReferenceCoords());
        attack_engine.actionIteration();
        assertEquals(90, inf12.getCurrHp());
        assertEquals(100, ran01.getCurrHp());
        assertTrue(ran01.isAttacking());
    }

    @Test
    public void testDeathEvent() {
        // lj0 Tr
        // i0 i1
        // ba1 B
        Engine attack_engine = createAttackEngine_deathEvent();
        Controllable lj0 = attack_engine.getPlayers()[0].getPlayerUnits().get(0);
        Controllable i0 = attack_engine.getPlayers()[0].getPlayerUnits().get(1);
        Controllable i1 = attack_engine.getPlayers()[1].getPlayerUnits().get(0);
        Controllable ba1 = attack_engine.getPlayers()[1].getPlayerUnits().get(1);
        Tree tr = (Tree)attack_engine.getMap().groundAt(new Point(0,1)).getOwnerReference();
        Player p0 = attack_engine.getPlayers()[0];
        
        //Tree: 5 hits, Inf: 10 hits, Barri: 50 hits
        attack_engine.startAttack(lj0, tr.getReferenceCoords());
        attack_engine.startAttack(i0, i1.getReferenceCoords());
        for (int i = 0; i < 5; ++i) {
            attack_engine.actionIteration();
        }
        assertEquals(2, p0.getResourceByName(ResourceType.wood));
        for (int i = 0; i < 5; ++i) {
            attack_engine.actionIteration();
        }
        assertEquals(1, p0.getResourceByName(ResourceType.upgrade));
        attack_engine.startAttack(i0, ba1.getReferenceCoords());
        for (int i = 0; i < 50; ++i) {
            attack_engine.actionIteration();
        }
        assertEquals(6, p0.getResourceByName(ResourceType.upgrade));
    }

    /// building
    @Test
    public void testStartBuild() {
        // b B B B Tr B B
        // B B W B B B B
        // B B B S B B B
        // B B B B B B B
        Engine building_engine = createBuildingEngine();
        Controllable bui = building_engine.getPlayers()[0].getPlayerUnits().get(0);

        // occupied target location: now covered by area availability
        building_engine.startBuild(bui, new Point(3, 3), VisualType.STONEMINE);
        assertFalse(bui.isBuilding());
        assertFalse(bui.isMoving());
        assertEquals("Checked area extends off the map. (area check)" + System.lineSeparator(), outContent.toString());

        // failed area availability (covered area condition)
        building_engine.startBuild(bui, new Point(0, 1), VisualType.STONEMINE);
        assertFalse(bui.isBuilding());
        assertFalse(bui.isMoving());
        assertEquals("Checked area extends off the map. (area check)" + System.lineSeparator(), outContent.toString());
        building_engine.startBuild(bui, new Point(1, 1), VisualType.STONEMINE);
        assertFalse(bui.isBuilding());
        assertFalse(bui.isMoving());
        assertEquals("Checked area extends off the map. (area check)" + System.lineSeparator(), outContent.toString());

        //occupied builder target location
        building_engine.startBuild(bui, new Point(4, 2), VisualType.STONEMINE);
        assertFalse(bui.isBuilding());
        assertFalse(bui.isMoving());

        //builder at loc
        building_engine.startBuild(bui, new Point(0, 1), VisualType.BARRICADE);
        Controllable ghostBuilding = bui.getGhostBuilding();
        assertTrue(bui.isBuilding());
        assertFalse(bui.isMoving());
        assertEquals(new Point(0, 1), ghostBuilding.getReferenceCoords());
        assertEquals(new Point(0, 0), bui.getBuilderTarLoc());
        assertEquals(VisualType.BARRICADE, bui.getGhostBuilding().getType());
        //created building
        building_engine.actionIteration();
        assertFalse(bui.isBuilding());
        assertFalse(bui.isMoving());
        assertEquals(VisualType.BARRICADE, ((Visual) building_engine.getMap().groundAt(new Point(0, 1)).getOwnerReference()).getType());
        assertTrue(building_engine.getPlayers()[0].getPlayerUnits().contains(ghostBuilding));
        //reset
        building_engine = createBuildingEngine();
        bui = building_engine.getPlayers()[0].getPlayerUnits().get(0);

        //builder further away, locations valid
        building_engine.startBuild(bui, new Point(0, 3), VisualType.BARRICADE);
        assertEquals(new Point(0, 3), bui.getGhostBuilding().getReferenceCoords());
        assertEquals(new Point(0, 2), bui.getBuilderTarLoc());
        assertTrue(bui.isBuilding());
        assertTrue(bui.isMoving());
    }

    @Test
    public void testStopBuild() {
        // b B B B Tr B B
        // B B W B B B B
        // B B B S B B B
        // B B B B B B B
        Engine building_engine = createBuildingEngine();
        Controllable bui = building_engine.getPlayers()[0].getPlayerUnits().get(0);

        building_engine.startBuild(bui, new Point(0, 3), VisualType.BARRICADE);
        building_engine.stopBuild(bui);
        assertFalse(bui.isBuilding());
        assertFalse(bui.isMoving());
    }

    @Test
    public void testIteration_building() {
        // b B B B Tr B B
        // B B W B B B B
        // B B B S B B B
        // B B B B B B B
        Engine building_engine = createBuildingEngine();
        Controllable bui = building_engine.getPlayers()[0].getPlayerUnits().get(0);

        building_engine.startBuild(bui, new Point(0, 2), VisualType.BARRICADE);
        assertTrue(bui.isBuilding());
        assertTrue(bui.isMoving());
        building_engine.actionIteration();
        assertEquals(new Point(0, 1), bui.getReferenceCoords());
        assertTrue(bui.isBuilding());
        assertFalse(bui.isMoving());
        building_engine.actionIteration();
        assertEquals(VisualType.BARRICADE, ((Visual) building_engine.getMap().groundAt(new Point(0, 2)).getOwnerReference()).getType());
        assertFalse(bui.isBuilding());
        assertFalse(bui.isMoving());
    }

    @Test
    public void testGotResForCont() {
        // b0 b1
        Engine building_engine = createBuildingEngine_gRFC();
        Controllable b0 = building_engine.getPlayers()[0].getPlayerUnits().get(0);
        Controllable b1 = building_engine.getPlayers()[1].getPlayerUnits().get(0);

        assertTrue(building_engine.gotResForCont(building_engine.getPlayers()[0], VisualType.STONEMINE));
        assertFalse(building_engine.gotResForCont(building_engine.getPlayers()[1], VisualType.STONEMINE));
    }

    /// upgrade
    @Test
    public void testUpgrade() {
        // i0 b0
        // i1 B
        Engine upgrade_engine = createUpgradeEngine();
        Controllable i0 = upgrade_engine.getPlayers()[0].getPlayerUnits().get(0);
        Controllable b0 = upgrade_engine.getPlayers()[0].getPlayerUnits().get(1);
        Controllable i1 = upgrade_engine.getPlayers()[1].getPlayerUnits().get(0);

        //got res, can upgr
        assertEquals(10, i0.getUpPrice()); //1, 9999
        upgrade_engine.upgrade(i0); //2, 9989
        assertEquals(2, i0.getCurrLvl());
        assertEquals(15, i0.getUpPrice());
        assertEquals(15, i0.getDmg());
        assertEquals(9989, upgrade_engine.getPlayers()[0].getResourceByName(ResourceType.upgrade));
        //got res, maxLvl reached
        upgrade_engine.upgrade(i0); //3, 9974
        upgrade_engine.upgrade(i0); //4, 9954
        upgrade_engine.upgrade(i0); //5, 9929
        upgrade_engine.upgrade(i0); // 6 -> 5, 9929 
        assertEquals(5, i0.getCurrLvl());
        //got res, can't upgr (type)
        upgrade_engine.upgrade(b0);
        assertEquals(1, b0.getCurrLvl());
        assertEquals(0, b0.getUpPrice());
        assertEquals(9929, upgrade_engine.getPlayers()[0].getResourceByName(ResourceType.upgrade));
        //no res, can upgr
        upgrade_engine.upgrade(i1);
        assertEquals(1, i1.getCurrLvl());
        assertEquals(10, i1.getUpPrice());
        assertEquals(0, upgrade_engine.getPlayers()[1].getResourceByName(ResourceType.upgrade));
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
        ground[2][2] = new Stone(new Point(2, 2), 1);
        Map map = new Map(ground);

        assertTrue(map.adjMineralCheck(new Point(1, 1), VisualType.STONE));
        assertTrue(map.adjMineralCheck(new Point(1, 1), VisualType.GOLD));
        assertFalse(map.adjMineralCheck(new Point(1, 4), VisualType.STONE));
        assertFalse(map.adjMineralCheck(new Point(1, 4), VisualType.GOLD));
        map.adjMineralCheck(new Point(1, 5), VisualType.GOLD);
        assertEquals("Checked area extends off the map. (mineral check)" + System.lineSeparator(), outContent.toString());
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
        //the default false won't be called
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
        assertFalse(destroyer.isValidTarget(tree)); //TREE

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
    private Engine createMovementEngine() {
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

        Engine new_movement_engine = new Engine(movement_map, players, 420);

        return new_movement_engine;
    }

    private Engine createAttackEngine() {
        Ground[][] attack_ground = new Ground[5][5];
        // t0 t0 i0 B r0
        // t0 t0 B  B B 
        // B  B  B  B B
        // t1 t1 i1 B r1
        // t1 t1 B  B B
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                attack_ground[i][j] = new Base(new Point(i, j));
            }
        }
        Map attack_map = new Map(attack_ground);

        Player[] players = new Player[2];
        Player jane_0 = new Player("jane_doe", 0);
        jane_0.addControllable(new Turret(new Point(0, 0), jane_0.getPlayerNumber()));
        jane_0.addControllable(new Infantry(new Point(0, 2), jane_0.getPlayerNumber()));
        jane_0.addControllable(new Ranger(new Point(0, 4), jane_0.getPlayerNumber()));
        Player john_1 = new Player("john_doe", 1);
        john_1.addControllable(new Turret(new Point(3, 0), john_1.getPlayerNumber()));
        john_1.addControllable(new Infantry(new Point(3, 2), john_1.getPlayerNumber()));
        john_1.addControllable(new Ranger(new Point(3, 4), john_1.getPlayerNumber()));
        players[0] = jane_0;
        players[1] = john_1;

        Engine new_attack_engine = new Engine(attack_map, players, 1337);

        return new_attack_engine;
    }

    private Engine createAttackEngine_LoS() {
        Ground[][] attack_ground = new Ground[3][3];
        // r01 S i11
        //  B  B  B 
        // i12 B  B 
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                attack_ground[i][j] = new Base(new Point(i, j));
            }
        }
        attack_ground[0][1] = new Stone(new Point(0, 2), 1);
        Map attack_map = new Map(attack_ground);

        Player[] players = new Player[2];
        Player jane_0 = new Player("jane_doe", 0);
        jane_0.addControllable(new Ranger(new Point(0, 0), jane_0.getPlayerNumber()));
        Player john_1 = new Player("john_doe", 1);
        john_1.addControllable(new Infantry(new Point(0, 2), john_1.getPlayerNumber()));
        john_1.addControllable(new Infantry(new Point(2, 0), john_1.getPlayerNumber()));
        players[0] = jane_0;
        players[1] = john_1;

        Engine new_attack_engine = new Engine(attack_map, players, 1337);

        return new_attack_engine;
    }

    private Engine createAttackEngine_deathEvent() {
        // lj0 Tr
        // i0 i1
        // ba1 B
        Ground[][] death_ground = new Ground[3][2];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                death_ground[i][j] = new Base(new Point(i, j));
            }
        }
        death_ground[0][1].setOwnerReference(new Tree(new Point(0, 1)));
        Map death_map = new Map(death_ground);

        Player[] players = new Player[2];
        Player jane_0 = new Player("jane_doe", 0);
        jane_0.addControllable(new Lumberjack(new Point(0, 0), jane_0.getPlayerNumber()));
        jane_0.addControllable(new Infantry(new Point(1, 0), jane_0.getPlayerNumber()));
        jane_0.nullifyResources();
        Player john_1 = new Player("john_doe", 1);
        john_1.addControllable(new Infantry(new Point(1, 1), john_1.getPlayerNumber()));
        john_1.addControllable(new Barricade(new Point(2, 0), john_1.getPlayerNumber()));
        players[0] = jane_0;
        players[1] = john_1;

        Engine new_death_engine = new Engine(death_map, players, 1337);
        
        return new_death_engine;
    }

    private Engine createBuildingEngine() {
        Ground[][] building_ground = new Ground[4][7];
        // b B B B Tr B B
        // B B W B B B B
        // B B B S B B B
        // B B B B B B B
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 7; ++j) {
                building_ground[i][j] = new Base(new Point(i, j));
            }
        }
        building_ground[0][4].setOwnerReference(new Tree(new Point(0, 4)));
        building_ground[1][2] = new Water(new Point(1, 2));
        building_ground[2][3] = new Stone(new Point(2, 3), 1);
        Map building_map = new Map(building_ground);

        Player[] players = new Player[1];
        Player jane = new Player("jane_doe", 0);
        jane.addControllable(new Builder(new Point(0, 0), jane.getPlayerNumber()));
        players[0] = jane;

        Engine new_building_engine = new Engine(building_map, players, 57);

        return new_building_engine;
    }

    private Engine createBuildingEngine_gRFC() {
        Ground[][] building_ground = new Ground[1][2];
        // b0 b1
        building_ground[0][0] = new Base(new Point(0, 0));
        building_ground[0][1] = new Base(new Point(0, 1));
        Map building_map = new Map(building_ground);

        Player[] players = new Player[2];
        Player jane_0 = new Player("jane_doe", 0);
        jane_0.addControllable(new Builder(new Point(0, 0), jane_0.getPlayerNumber()));
        Player john_1 = new Player("john_doe", 1);
        john_1.addControllable(new Builder(new Point(0, 1), john_1.getPlayerNumber()));
        john_1.nullifyResources();
        players[0] = jane_0;
        players[1] = john_1;

        Engine new_building_engine = new Engine(building_map, players, 57);

        return new_building_engine;
    }

    private Engine createUpgradeEngine() {
        // i0 b0
        // i1 B
        Ground[][] upgrade_ground = new Ground[2][2];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                upgrade_ground[i][j] = new Base(new Point(i, j));
            }
        }
        Map upgrade_map = new Map(upgrade_ground);

        Player[] players = new Player[2];
        Player jane_0 = new Player("jane_doe", 0);
        jane_0.addControllable(new Infantry(new Point(0, 0), jane_0.getPlayerNumber()));
        jane_0.addControllable(new Builder(new Point(0, 1), jane_0.getPlayerNumber()));
        Player john_1 = new Player("john_doe", 1);
        john_1.addControllable(new Infantry(new Point(1, 0), john_1.getPlayerNumber()));
        john_1.nullifyResources();
        players[0] = jane_0;
        players[1] = john_1;

        Engine new_upgrade_engine = new Engine(upgrade_map, players, 666);

        return new_upgrade_engine;
    }

}

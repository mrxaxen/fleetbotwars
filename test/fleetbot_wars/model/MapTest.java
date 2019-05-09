/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import exceptions.*;
import java.awt.Point;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import visual.ground.Ground;

/**
 *
 * @author trmx
 */
public class MapTest {
    
    private static Engine e;
    public static Engine getEngine() {
        return e;
    }
    private Map map;
    
    public MapTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }


    /**
     * Test of groundAt method, of class Map for valid values on given Map.
     */
    @Test
    public void testValidGroundAt() {
        
        Player[] testPlayers = new Player[4];
        testPlayers[0] = new Player("bori", 0);
        testPlayers[1] = new Player("gabor", 1);
        testPlayers[2] = new Player("laci", 2);
        testPlayers[3] = new Player("drszendrey", 3);
    
        e = Engine.getInstance(new Map(), testPlayers);
        map = e.getMap();
        
        int height = map.getMapDimension().height;
        int width = map.getMapDimension().width;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Point toCheck = new Point(i,j);
                assertTrue(map.groundAt(toCheck) instanceof Ground);
            }
        }
    }

    /**
     * Test of groundAt method, of class Map for invalid values on given Map.
     */
    @Test(expected = OutOfMapBoundsException.class)
    public void testInValidGroundAt() {
        
        Player[] testPlayers = new Player[4];
        testPlayers[0] = new Player("bori", 0);
        testPlayers[1] = new Player("gabor", 1);
        testPlayers[2] = new Player("laci", 2);
        testPlayers[3] = new Player("drszendrey", 3);
    
        e = Engine.getInstance(new Map(), testPlayers);
        map = e.getMap();
        
        int height = map.getMapDimension().height;
        int width = map.getMapDimension().width;
        for(int i = -5; i < 0; i++){
            for(int j = -5; j < 0; j++){
                Point toCheck = new Point(i,j);
                System.out.println(toCheck);
                map.groundAt(toCheck);
            }
        }
        for(int i = height; i < height+5; i++){
            for(int j = width; j < width+5; j++){
                Point toCheck = new Point(i,j);
                map.groundAt(toCheck);
            }
        }
    }
    
    /**
     * Test for expected valid values: the players number don't exceed
     * the number of starting zones.
     * The test map used in the Map class has 4 starting zones.
     */
    @Test
    public void TestValidplacePlayersOnMap(){
        Player[] testPlayers = new Player[4];
        testPlayers[0] = new Player("bori", 0);
        testPlayers[1] = new Player("gabor", 1);
        testPlayers[2] = new Player("laci", 2);
        testPlayers[3] = new Player("drszendrey", 3);
    
        map = new Map();
        map.placePlayersOnMap(testPlayers);
    }
    
    
    /**
     * Test for expected invalid values: the players number exceeds
     * the number of starting zones.
     * The test map used in the Map class has 4 starting zones.
     */
    @Test(expected = PlayersExceedStartingZoneCountException.class)
    public void TestInValidplacePlayersOnMap(){
        Player[] testPlayers = new Player[5];
        testPlayers[0] = new Player("bori", 0);
        testPlayers[1] = new Player("gabor", 1);
        testPlayers[2] = new Player("laci", 2);
        testPlayers[3] = new Player("drszendrey", 3);
        testPlayers[4] = new Player("drtormasi", 4);
    
        map = new Map();
        map.placePlayersOnMap(testPlayers);
        
    }


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void groundAt() {
    }

    @Test
    void getStartingZoneCoords() {
    }

    @Test
    void placePlayersOnMap() {
    }

    @Test
    void toString1() {
    }

    @Test
    void getGround() {
    }

    @Test
    void adjMineralCheck() {
    }

    @Test
    void getMapDimension() {
    }
}

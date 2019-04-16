/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import fleetbot_wars.model.enums.VisualType;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author trmx
 */
public class VisualTest {
    
    public VisualTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of draw method, of class Visual.
     */
    @Test
    public void testDraw() {
        System.out.println("draw");
        Visual instance = null;
        instance.draw();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class Visual.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Visual instance = null;
        VisualType expResult = null;
        VisualType result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModel method, of class Visual.
     */
    @Test
    public void testGetModel() {
        System.out.println("getModel");
        Visual instance = null;
        Image expResult = null;
        Image result = instance.getModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class Visual.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Visual instance = null;
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class Visual.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Visual instance = null;
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferenceCoords method, of class Visual.
     */
    @Test
    public void testGetReferenceCoords() {
        System.out.println("getReferenceCoords");
        Visual instance = null;
        Point expResult = null;
        Point result = instance.getReferenceCoords();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCoordsArray method, of class Visual.
     */
    @Test
    public void testGetCoordsArray() {
        System.out.println("getCoordsArray");
        Visual instance = null;
        ArrayList<Point> expResult = null;
        ArrayList<Point> result = instance.getCoordsArray();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReferenceCoords method, of class Visual.
     */
    @Test
    public void testSetReferenceCoords() {
        System.out.println("setReferenceCoords");
        Point referenceCoords = null;
        Visual instance = null;
        instance.setReferenceCoords(referenceCoords);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

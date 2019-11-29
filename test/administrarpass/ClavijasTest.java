/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarpass;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 49904022
 */
public class ClavijasTest {
    
    char a = 'A', b = 'B';
    Clavijas cla;
    
    public ClavijasTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        cla = new Clavijas(a, b);
    }
    
    @After
    public void tearDown() {
        cla = null;
    }

    /**
     * Test of getX method, of class Clavijas.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        char expResult = 'A';
        char result = cla.getX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getY method, of class Clavijas.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        char expResult = 'B';
        char result = cla.getY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}

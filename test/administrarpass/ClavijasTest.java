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
    
    private static char a = 'A';
    private static char b = 'B';
    Clavijas cla;
    
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
    public void testGetA() {
        System.out.println("getA");
        char expResult = 'A';
        char result = cla.getA();
        assertEquals(expResult, result);
    }

    /**
     * Test of getY method, of class Clavijas.
     */
    @Test
    public void testGetB() {
        System.out.println("getB");
        char expResult = 'B';
        char result = cla.getB();
        assertEquals(expResult, result);
    }
    
}

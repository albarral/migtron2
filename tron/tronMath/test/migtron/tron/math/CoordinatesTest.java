/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

import java.awt.Point;
import java.awt.geom.Point2D.Float;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author albarral
 */
public class CoordinatesTest {
    
    public CoordinatesTest() {
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
     * Test of computePolar method, of class Coordinates.
     */
    @Test
    public void testComputePolar() 
    {
        System.out.println("testComputePolar");
        
        float x = 50.0f;
        float y = 50.0f;
        Float polar = Coordinates.computePolar(x, y);
        Point polar2 = Coordinates.float2Point(polar);
        Point expected = new Point(71, 45);
        
        Assert.assertEquals(expected, polar2);
    }

    /**
     * Test of computeCartesian method, of class Coordinates.
     */
    @Test
    public void testComputeCartesian() 
    {
        System.out.println("testComputeCartesian");
        
        float mag = 100.0f;
        float angle = 60.0f;
        Float pos = Coordinates.computeCartesian(mag, angle);
        Point pos2 = Coordinates.float2Point(pos);
        Point expected = new Point(50, 87);
        
        Assert.assertEquals(expected, pos2);
    }

    @Test
    public void testFloat2Point() 
    {
        System.out.println("testFloat2Point");
        
        Point expected = new Point(50, 50);
        Float pos1 = new Float(49.8f, 49.5f);                
        Point pos2 = Coordinates.float2Point(pos1);

        Assert.assertEquals(expected, pos2);
    }    

        /**
     * Test of computePolar method, of class Coordinates.
     */
    @Test
    public void testPolarAndBackFloat() 
    {
        System.out.println("testPolarAndBackFloat");
        
        Float pos = new Float(50.0f, 50.0f);                
        Float polar = Coordinates.computePolar(pos);
        Float pos2 = Coordinates.computeCartesian(polar);
        
        Assert.assertEquals(pos, pos2);
    }

    /**
     * Test of computePolar method, of class Coordinates.
     */
    @Test
    public void testPolarAndBackPoint() 
    {
        System.out.println("testPolarAndBackPoint");
        
        Point pos = new Point(50, 50);                
        Point polar = Coordinates.computePolarPoint(pos);
        Point pos2 = Coordinates.computeCartesianPoint(polar);

        Assert.assertEquals(pos, pos2);
    }    
}

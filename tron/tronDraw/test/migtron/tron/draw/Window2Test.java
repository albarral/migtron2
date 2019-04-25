/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 *
 * @author albarral
 */
public class Window2Test {
    
    public Window2Test() {
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
     * Test of getUnion method, of class Window2.
     */
    @Test
    public void testGetUnion() {
        System.out.println("getUnion");
        
        Point p1 = new Point(10, 10);
        Point p2 = new Point(50, 50);
        Point p3 = new Point(20, 20);
        Point p4 = new Point(60, 60);
        Rect window1 = new Rect(p1, p2);
        Rect window2 = new Rect(p3, p4);
        Rect expResult = new Rect(p1, p4);
        Rect result = Window2.getUnion(window1, window2);

        System.out.println("window1 = " + window1.toString());
        System.out.println("window2 = " + window2.toString());
        System.out.println("union = " + result.toString());

        Assert.assertEquals(expResult, result);
    }
    
}

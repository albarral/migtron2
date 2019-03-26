/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opencv.core.Point;
import org.opencv.core.Point3;

/**
 *
 * @author albarral
 */
public class AverageCVTest {
    
    public AverageCVTest() {
    }
        
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of compute2DAverage method, of class AverageCV.
     */
    @Test
    public void testCompute2DAverage() 
    {
        System.out.println("compute2DAverage");
        
        Point p1 = new Point(10,10);
        Point p2 = new Point(5,5);
        Point p3 = new Point(3,3);        
        Point[] points = {p1, p2, p3};
        Point expResult = new Point(6, 6);

        Point result = AverageCV.compute2DAverage(points);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of compute2DWeightedAverage method, of class AverageCV.
     */
    @Test
    public void testCompute2DWeightedAverage() 
    {
        System.out.println("compute2DWeightedAverage");

        Point p1 = new Point(10,10);
        Point p2 = new Point(10,10);
        Point p3 = new Point(10,10);         
        Point[] points = {p1, p2, p3};
        Point expResult = new Point(10, 10);

        int[] weights = {1, 2, 4};
        Point result = AverageCV.compute2DWeightedAverage(points, weights);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of compute3DAverage method, of class AverageCV.
     */
    @Test
    public void testCompute3DAverage() 
    {
        System.out.println("compute3DAverage");
    
        Point3 p1 = new Point3(10,10,10);
        Point3 p2 = new Point3(5,5,5);
        Point3 p3 = new Point3(3,3,3);        
        Point3[] points = {p1, p2, p3};
        Point3 expResult = new Point3(6, 6, 6);
        
        Point3 result = AverageCV.compute3DAverage(points);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of compute3DWeightedAverage method, of class AverageCV.
     */
    @Test
    public void testCompute3DWeightedAverage() 
    {
        System.out.println("compute3DWeightedAverage");
    
        Point3 p1 = new Point3(10,10,10);
        Point3 p2 = new Point3(10,10,10);
        Point3 p3 = new Point3(10,10,10);        
        Point3[] points = {p1, p2, p3};
        Point3 expResult = new Point3(10, 10, 10);

        int[] weights = {1, 2, 4};
        Point3 result = AverageCV.compute3DWeightedAverage(points, weights);
        Assert.assertEquals(expResult, result);
    }
    
}

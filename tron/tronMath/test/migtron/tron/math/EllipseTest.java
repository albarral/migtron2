/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

import java.awt.geom.Point2D.Float;

import org.junit.After;
//import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author albarral
 */
public class EllipseTest 
{
    private Ellipse ellipse;
    
    public EllipseTest() 
    {        
    }
        
    @Before
    public void setUp() 
    {
        Float pos = new Float(50, 50);
        Vec3f covs = new Vec3f(100, 100, 0);        
        
        ellipse = new Ellipse(pos, covs);
        System.out.println("\nellipse1: " + ellipse.toString());
    }
    
    @After
    public void tearDown() {
        ellipse = null;
    }

    /**
     * Test of clone method, of class Ellipse.
     */
    @Test
    public void testClone() throws Exception 
    {
        System.out.println("clone");
        
        Ellipse ellipse2 = (Ellipse)ellipse.clone();
        System.out.println("ellipse2: " + ellipse2.toString());

        Assert.assertEquals(ellipse, ellipse2);
    }


    /**
     * Test of setCovariances method, of class Ellipse.
     */
    @Test
    public void testSetCovariances() 
    {
        System.out.println("setCovariances");
        
        Vec3f covs = new Vec3f(900, 100, 0);        
        ellipse.setCovariances(covs);
        System.out.println("ellipse1 changed: " + ellipse.toString());
        
        Assert.assertTrue(true);
    }

    /**
     * Test of mergeEllipse method, of class Ellipse.
     */
    @Test
    public void testMergeHorizontal() throws Exception 
    {
        System.out.println("mergeHorizontal");
                
        Ellipse ellipse2 = getShiftedEllipse(50f, 0f);
        System.out.println("ellipse2: " + ellipse2.toString());
        
        float w1 = 0.5f;
        float w2 = 0.5f;
        ellipse.mergeEllipse(ellipse2, w1, w2);
        System.out.println("merged: " + ellipse.toString());

        Assert.assertTrue(true);
    }

        /**
     * Test of mergeEllipse method, of class Ellipse.
     */
    @Test
    public void testMergeVertical() throws Exception 
    {
        System.out.println("mergeVertical");
                
        Ellipse ellipse2 = getShiftedEllipse(0f, 50f);
        System.out.println("ellipse2: " + ellipse2.toString());
        
        float w1 = 0.5f;
        float w2 = 0.5f;
        ellipse.mergeEllipse(ellipse2, w1, w2);
        System.out.println("merged: " + ellipse.toString());

        Assert.assertTrue(true);
    }

        /**
     * Test of mergeEllipse method, of class Ellipse.
     */
    @Test
    public void testMergeDiagonal() throws Exception 
    {
        System.out.println("mergeDiagonal");
                
        Ellipse ellipse2 = getShiftedEllipse(50f, 50f);
        System.out.println("ellipse2: " + ellipse2.toString());
        
        float w1 = 0.5f;
        float w2 = 0.5f;
        ellipse.mergeEllipse(ellipse2, w1, w2);
        System.out.println("merged: " + ellipse.toString());

        Assert.assertTrue(true);
    }
    
    private Ellipse getShiftedEllipse(float dx, float dy) throws Exception 
    {                
        Ellipse ellipse2 = (Ellipse)ellipse.clone(); 
        Float pos2 = (Float)ellipse.getPosition().clone();
        pos2.x += dx;
        pos2.y += dy;
        ellipse2.setPosition(pos2);
        
        return ellipse2;
    }
    
}

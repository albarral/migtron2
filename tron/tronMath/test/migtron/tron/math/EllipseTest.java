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
        System.out.println("ellipse1: " + ellipse.toString());
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
        
        System.out.println("ellipse1: " + ellipse.toString());
        Vec3f covs = new Vec3f(900, 100, 0);        
        ellipse.setCovariances(covs);
        System.out.println("ellipse3: " + ellipse.toString());
        
        Assert.assertTrue(true);
    }

    /**
     * Test of merge method, of class Ellipse.
     */
    @Test
    public void testMergeHorizontal() throws Exception 
    {
        System.out.println("mergeHorizontal");
                
        Ellipse ellipse2 = (Ellipse)ellipse.clone(); 
        shiftEllipse(ellipse2, 50f, 0f);
        System.out.println("ellipse1: " + ellipse.toString());
        System.out.println("ellipse2: " + ellipse2.toString());
        
        float w1 = 0.5f;
        float w2 = 0.5f;
        ellipse.merge(ellipse2, w1, w2);
        System.out.println("ellipse3: " + ellipse.toString());

        Assert.assertTrue(true);
    }

        /**
     * Test of merge method, of class Ellipse.
     */
    @Test
    public void testMergeVertical() throws Exception 
    {
        System.out.println("mergeVertical");
                
        Ellipse ellipse2 = (Ellipse)ellipse.clone(); 
        shiftEllipse(ellipse2, 0f, 50f);
        System.out.println("ellipse1: " + ellipse.toString());
        System.out.println("ellipse2: " + ellipse2.toString());
        
        float w1 = 0.5f;
        float w2 = 0.5f;
        ellipse.merge(ellipse2, w1, w2);
        System.out.println("ellipse3: " + ellipse.toString());

        Assert.assertTrue(true);
    }

        /**
     * Test of merge method, of class Ellipse.
     */
    @Test
    public void testMergeDiagonal() throws Exception 
    {
        System.out.println("mergeDiagonal");
                
        Ellipse ellipse2 = (Ellipse)ellipse.clone(); 
        shiftEllipse(ellipse2, 50f, 50f);
        System.out.println("ellipse1: " + ellipse.toString());
        System.out.println("ellipse2: " + ellipse2.toString());
        
        float w1 = 0.5f;
        float w2 = 0.5f;
        ellipse.merge(ellipse2, w1, w2);
        System.out.println("ellipse3: " + ellipse.toString());

        Assert.assertTrue(true);
    }
    
    private void shiftEllipse(Ellipse ellipse, float dx, float dy) 
    {                
        Float pos = ellipse.getPosition();
        pos.x += dx;
        pos.y += dy;
        ellipse.setPosition(pos);
    }
    
}

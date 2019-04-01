/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import java.awt.Point;
import java.awt.geom.Point2D;

import migtron.tron.math.Ellipse;
import migtron.tron.math.Vec3f;
import migtron.tron.util.display.Display;

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
public class MathDrawerTest 
{
    private static Display display;
    private MathDrawer mathDrawer;
    
    public MathDrawerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        NativeOpenCV.load();
        display = new Display("MathDrawerTest");
    }
        
   @AfterClass
    public static void tearDownClass() {
        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
    }
    
    @Before
    public void setUp() {
        mathDrawer = new MathDrawer(200, 100);        
    }
    
    @After
    public void tearDown() {
        // wait for a while to see the result
        try {Thread.sleep(500);}
        catch (InterruptedException e) {}
    }

    /**
     * Test of drawVector method, of class MathDrawer.
     */
    @Test
    public void testDrawVector() 
    {
        System.out.println("drawVector");

        Point origin = new Point(100, 50);
        float len = 40f;
        float angle = -45f;
        mathDrawer.drawVector(origin,len, angle);
        display.addWindow(mathDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of drawEllipse method, of class MathDrawer.
     */
    @Test
    public void testDrawEllipse() {
        System.out.println("drawEllipse");

        Point2D.Float pos = new Point2D.Float(100, 50);
        Vec3f covs = new Vec3f(1600, 800, 0);        
        
        Ellipse ellipse = new Ellipse(pos, covs);

        mathDrawer.drawEllipse(ellipse);
        display.addWindow(mathDrawer.getImage());

        Assert.assertTrue(true);
    }
    
}

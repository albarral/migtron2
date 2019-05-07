/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.data;

import java.util.ArrayList;
import java.util.List;

import migtron.tron.cv.Mask;
import migtron.tron.cv.NativeOpenCV;
import migtron.tron.draw.BlocksDrawer;
import migtron.tron.draw.DrawUtils;
import migtron.tron.math.Vec3f;
import migtron.tron.math.Vec3i;
import migtron.tron.math.color.Colors;
import migtron.tron.util.display.Display;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.opencv.core.Mat;

/**
 *
 * @author albarral
 */
public class BodyTest 
{
    private int w;
    private int h;
    private List<Body> listBodies;    // list of bodies
    private BlocksDrawer blocksDrawer;  // blocks drawing utility
    private int type;    // type of matrix to show results
    
    public BodyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        NativeOpenCV.load();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {   
        // create drawing utilities
        w = 200;
        h = 100;
        blocksDrawer = new BlocksDrawer(w, h, 3);        
        type = blocksDrawer.getMat().type();
        listBodies = new ArrayList<>();
}
    
    @After
    public void tearDown() {
        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
    }

    /**
     * Test of clone method, of class Body.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        Body instance = null;
        Object expResult = null;
        Object result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMask method, of class Body.
     */
    @Test
    public void testSetMask() 
    {
        System.out.println("setMask");
        Mask mask = null;
        Body instance = null;
        instance.setMask(mask);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class Body.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Body instance = null;
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeBorderMask method, of class Body.
     */
    @Test
    public void testComputeBorderMask() {
        System.out.println("computeBorderMask");
        Body instance = null;
        Mat expResult = null;
        Mat result = instance.computeBorderMask();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of merge method, of class Body.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");

        Vec3i red = Colors.getRGB(Colors.eColor.eCOLOR_RED);                
        Vec3i green = Colors.getRGB(Colors.eColor.eCOLOR_GREEN);

        // top left corner mask
        blocksDrawer.fillBlock(0, 0);        
        Mask mask1 = new Mask(blocksDrawer.getMat(), blocksDrawer.getDrawnWindow());
        Body body1 = new Body(mask1, new Vec3f(green));

        // center block mask
        blocksDrawer.clear();
        blocksDrawer.fillBlock(1, 1);        
        Mask mask2 = new Mask(blocksDrawer.getMat(), blocksDrawer.getDrawnWindow());
        Body body2 = new Body(mask2, new Vec3f(red));
        
        // merge 1 & 2 (with cloned 1)
        Body body3 = (Body)body1.clone();
        body3.merge(body2);        

        listBodies.add(body1);
        listBodies.add(body2);
        listBodies.add(body3);
        showBodies("merge");

        int mass1 = body1.getMass();
        int mass2 = body2.getMass();
        int mass3 = body3.getMass();
        
        Assert.assertTrue((mass1 + mass2) == mass3);
    }

    /**
     * Test of computeOverlap method, of class Body.
     */
    @Test
    public void testComputeOverlap() {
        System.out.println("computeOverlap");
        Body body = null;
        Body instance = null;
        int expResult = 0;
        int result = instance.computeOverlap(body);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
  
    // show bodies in a display
    private void showBodies(String title)
    {
        Display display = new Display(title);        

        for (Body body : listBodies)
            displayBody(display, body);
    }

    // display mask in display
    private void displayBody(Display display, Body body)
    {
        // copy mask in big frame image
        Mat matShow = Mat.zeros(h, w, type);
        Mat mat2 = matShow.submat(body.getMask().getWindow());
        body.getMask().getMat().copyTo(mat2);
        
        display.addWindow(DrawUtils.cvMask2Java(matShow));            
    }
    
}

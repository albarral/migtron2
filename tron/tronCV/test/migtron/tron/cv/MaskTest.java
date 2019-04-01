/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import migtron.tron.draw.BlocksDrawer;
import migtron.tron.draw.DrawUtils;
import migtron.tron.draw.MathDrawer;
import migtron.tron.math.Ellipse;
import migtron.tron.math.color.Colors;
import migtron.tron.util.display.Display;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;       
import org.junit.Test;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 *
 * @author albarral
 */
public class MaskTest 
{
    private int w;
    private int h;
    private List<Mask> listMasks;    // original masks
    private List<Mask> listMasks2;   // result masks  
    private BlocksDrawer blocksDrawer;  // blocks drawing utility
    private MathDrawer mathDrawer;      // ellipses drawing utility
    
    public MaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        NativeOpenCV.load();
    }
    
    @AfterClass
    public static void tearDownClass() {
        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
    }
    
    @Before
    public void setUp() {
        // create drawing utilities
        w = 200;
        h = 100;
        blocksDrawer = new BlocksDrawer(w, h, 3);        
        mathDrawer = new MathDrawer(w, h);
        mathDrawer.setStandardColor(Colors.eColor.eCOLOR_GREY);        
        // create lists of masks
        listMasks = new ArrayList<>();
        listMasks2 = new ArrayList<>();        
    }
    
    @After
    public void tearDown() {
        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
    }

    /**
     * Test of clone method, of class Mask.
     */
    @Test
    public void testClone() 
    {
        System.out.println("clone");
        
        blocksDrawer.fillBlock(1, 1);
        Mask mask1 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask1);
        
        Mask mask2 = (Mask)mask1.clone();
        listMasks2.add(mask2);

        showMasks("cloning");

        Assert.assertTrue(true);
    }


    /**
     * Test of computeMass method, of class Mask.
     */
    @Test
    public void testComputeMass() 
    {
        System.out.println("computeMass");

        blocksDrawer.fillBlock(1, 1);
        Mask mask1 = new Mask(blocksDrawer.getMat());

        int mass = mask1.computeMass();       
        int n = blocksDrawer.getGranularity();
        int expResult = (w/n)*(h/n);
        
        Assert.assertEquals(expResult, mass);
    }

    /**
     * Test of computeEllipse method, of class Mask.
     */
    @Test
    @Ignore
    public void testComputeEllipse() {
        System.out.println("computeEllipse");
        Mask instance = null;
        Ellipse expResult = null;
        Ellipse result = instance.computeEllipse();
        
        Assert.assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assert.fail("The test case is a prototype.");
    }

    /**
     * Test of computeLevelCurve method, of class Mask.
     */
    @Test
    @Ignore
    public void testComputeLevelCurve() {
        System.out.println("computeLevelCurve");
        int value = 0;
        Mask instance = null;
        Mat expResult = null;
        Mat result = instance.computeLevelCurve(value);
        Assert.assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assert.fail("The test case is a prototype.");
    }

    /**
     * Test of merge method, of class Mask.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");

        // top filled mask
        blocksDrawer.fillTop();
        //blocksDrawer.fillBlock(0, 0);        
        Mask mask1 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask1);

        // right filled mask
        blocksDrawer.clear();
        blocksDrawer.fillRight();
        //blocksDrawer.fillBlock(1, 1);        
        Mask mask2 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask2);
        
        // merge 1 & 2 (with cloned 1)
        Mask mask3 = (Mask)mask1.clone();                
        mask3.merge(mask2);        
        listMasks2.add(mask3);

        showMasks("merge");

        Assert.assertTrue(true);
    }

    /**
     * Test of intersect method, of class Mask.
     */
    @Test
    public void testIntersect() {
        System.out.println("intersect");

        // top filled mask
        blocksDrawer.fillTop();
        //blocksDrawer.fillBlock(0, 0);        
        Mask mask1 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask1);

        // right filled mask
        blocksDrawer.clear();
        blocksDrawer.fillRight();
        //blocksDrawer.fillBlock(1, 1);        
        Mask mask2 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask2);
        
        // intersect 1 & 2 (with cloned 1)
        Mask mask3 = (Mask)mask1.clone();                
        mask3.intersect(mask2);        
        listMasks2.add(mask3);

        showMasks("intersection");

        Assert.assertTrue(true);
    }

    
    // process original and result masks (computing their ellipses and drawing them)
    private void processMasks()
    {
        for (Mask mask : listMasks)
        {
            processMask(mask);
        }
        for (Mask mask : listMasks2)
        {
            processMask(mask);
        }
    }    

    // process given mask (compute its ellipse and draw it on the mask)
    private void processMask(Mask mask)
    {
        // compute ellipse
        Ellipse ellipse = mask.computeEllipse();
        // draw ellipse
        mathDrawer.setBase(mask.getMat());
        mathDrawer.drawEllipse(ellipse);
        // set it on mask
        Rect window = new Rect(0, 0, w, h);
        mask.set(mathDrawer.getMat(), window);
    }    
    
    // show original and result masks in a display
    private void showMasks(String title)
    {
        Display display = new Display(title);        
        for (Mask mask : listMasks)
        {
            display.addWindow(DrawUtils.cvMask2Java(mask.getMat()));            
        }
        for (Mask mask : listMasks2)
        {
            display.addWindow(DrawUtils.cvMask2Java(mask.getMat()));            
        }
    }
}

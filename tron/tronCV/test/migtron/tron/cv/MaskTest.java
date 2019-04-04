/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

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
    private List<Ellipse> listEllipses;     // ellipses of original masks
    private List<Ellipse> listEllipses2;    // ellipses of result masks
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
        // create lists of ellipses
        listEllipses = new ArrayList<>();
        listEllipses2 = new ArrayList<>();
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

        processMasks();
        processResultMasks();
        
        Assert.assertEquals(listEllipses.get(0), listEllipses2.get(0));
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

        // top left corner mask
        blocksDrawer.fillBlock(0, 0);        
        Mask mask1 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask1);

        // center block mask
        blocksDrawer.clear();
        blocksDrawer.fillBlock(1, 1);        
        Mask mask2 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask2);
        
        // merge 1 & 2 (with cloned 1)
        Mask mask3 = (Mask)mask1.clone();                
        mask3.merge(mask2);        
        listMasks2.add(mask3);

        showMasks("merge");

        int mass1 = mask1.computeMass();
        int mass2 = mask2.computeMass();
        int mass3 = mask3.computeMass();
        
        Assert.assertTrue((mass1 + mass2) == mass3);
    }

    /**
     * Test of intersect method, of class Mask.
     */
    @Test
    public void testIntersect() {
        System.out.println("intersect");

        // top filled mask
        blocksDrawer.fillTop();
        Mask mask1 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask1);

        // right filled mask
        blocksDrawer.clear();
        blocksDrawer.fillRight();
        Mask mask2 = new Mask(blocksDrawer.getMat());
        listMasks.add(mask2);
        
        // intersect 1 & 2 (with cloned 1)
        Mask mask3 = (Mask)mask1.clone();                
        mask3.intersect(mask2);        
        listMasks2.add(mask3);

        showMasks("intersection");

        int mass1 = mask1.computeMass();
        int mass2 = mask2.computeMass();
        int mass3 = mask3.computeMass();
        
        Assert.assertTrue(mass3 == mass1/3 && mass3 == mass2/3);
    }  

    // process original masks (computing their ellipses)
    private void processMasks()
    {
        for (Mask mask : listMasks)
        {
            listEllipses.add(mask.computeEllipse());
        }
    }    

    // process result masks (computing their ellipses)
    private void processResultMasks()
    {
        for (Mask mask : listMasks2)
        {
            listEllipses2.add(mask.computeEllipse());
        }
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

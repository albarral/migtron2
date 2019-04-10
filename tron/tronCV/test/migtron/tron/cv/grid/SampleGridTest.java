/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import migtron.tron.cv.NativeOpenCV;

import java.awt.Rectangle;
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
public class SampleGridTest 
{
    private int w;
    private int h;
    private SampleGrid sampleGrid;
    
    public SampleGridTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        NativeOpenCV.load();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        w = 100;
        h = 50;
        float reductionFactor = 0.1f;
        sampleGrid = new SampleGrid(w, h, reductionFactor);                
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSampledWindow method, of class SampleGrid.
     */
    @Test
    public void testGetSampledWindow() {
        System.out.println("getSampledWindow");
        SampleGrid instance = null;
        Rectangle expResult = null;
        Rectangle result = instance.getSampledWindow();
        Assert.assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assert.fail("The test case is a prototype.");
    }


    /**
     * Test of addSample method, of class SampleGrid.
     */
    @Test
    public void testAddSample() 
    {
        System.out.println("addSample");
        
        int walk = 10;
        doWalk(0, 20, walk-1, 20);

        int samples = sampleGrid.getFocusSamples();
        System.out.println("focus = " + sampleGrid.getFocus().toString());                    
        System.out.println("samples = " + samples);                    
        int expResult = walk;
        
        Assert.assertEquals(expResult, samples);
    }

    /**
     * Test of merge method, of class SampleGrid.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");
        SampleGrid sampleGrid = null;
        SampleGrid instance = null;
        boolean expResult = false;
        boolean result = instance.merge(sampleGrid);
        Assert.assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assert.fail("The test case is a prototype.");
    }
    
    // walk all points in the window going from (x0, y0) to (x1, y1) adding a sample at each step
    private void doWalk(int x0, int y0, int x1, int y1)
    {
        for (int x=x0; x<=x1; x++)
        for (int y=y0; y<=y1; y++)
        {
            sampleGrid.focus(x, y);            
            sampleGrid.addSample();
            System.out.println("sampled (x,y) = " + x + "," + y);                    
        }        
    }
}

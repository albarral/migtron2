/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import migtron.tron.cv.Mask;
import migtron.tron.cv.NativeOpenCV;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.opencv.core.Rect;

/**
 *
 * @author albarral
 */
public class SampleGridTest 
{
    private int w;
    private int h;
    float reductionFactor;
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
        reductionFactor = 0.1f;
        sampleGrid = new SampleGrid(w, h, reductionFactor);                
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSampledWindow method, of class SampleGrid.
     */
    @Test
    public void testGetSampledWindow() 
    {
        System.out.println("getSampledWindow");
        
        int x0 = 0;
        int y0 = 20;
        int width = 20;
        int height = 20;
        doWalk(sampleGrid, x0, y0, width, height);
        System.out.println("> grid \n" + sampleGrid.toString());                    
        
        Rect expResult = new Rect((int)(x0*reductionFactor), (int)(y0*reductionFactor), (int)(width*reductionFactor), (int)(height*reductionFactor));
        Rect result = sampleGrid.getSampledWindow();
        System.out.println("sampled window = " + result);                    
        Assert.assertEquals(expResult, result);
    }


    /**
     * Test of addSample method, of class SampleGrid.
     */
    @Test
    public void testAddSample() 
    {
        System.out.println("addSample");
        
        int x0 = 0;
        int y0 = 20;
        int width = 8;
        doWalk(sampleGrid, x0, y0, width, 1);
        System.out.println("> grid \n" + sampleGrid.toString());                    
     
        sampleGrid.focus(x0, y0);
        int samples = sampleGrid.getFocusSamples();
        System.out.println("focus = " + sampleGrid.getFocus().toString());                    
        System.out.println("samples = " + samples);        
        int nodeSize = (int)(1/reductionFactor); 
        int expResult = Math.min(nodeSize, width);
        
        Assert.assertEquals(expResult, samples);
    }

    /**
     * Test of getSamplesMask method, of class SampleGrid.
     */
    @Test
    public void testGetSamplesMask() 
    {
        System.out.println("getSamplesMask");
        
        int x0 = 0;
        int y0 = 20;
        int width = 35;
        int height = 35;
        doWalk(sampleGrid, x0, y0, width, height);
        System.out.println("> grid \n" + sampleGrid.toString());                    
     
        Mask mask = sampleGrid.getSamplesMask();
        System.out.println("> mask \n" + mask.getMat().dump());                    
                
        Assert.assertTrue(true);
    }
    
    /**
     * Test of merge method, of class SampleGrid.
     */
    @Test
    public void testMerge() 
    {
        System.out.println("merge");
    
        // create second grid (equal to first one)
        SampleGrid sampleGrid2 = (SampleGrid)sampleGrid.clone();
        
        // walk first grid
        int x0 = 10;
        int y0 = 10;
        int width = 20;
        int height = 20;
        doWalk(sampleGrid, x0, y0, width, height);
        System.out.println("> grid1 \n" + sampleGrid.toString());                    

        // walk second grid (overlapped with first)
        int x2 = x0+10;
        int y2 = y0+10;
        int width2 = 20;
        int height2 = 20;
        doWalk(sampleGrid2, x2, y2, width2, height2);        
        System.out.println("> grid2 \n" + sampleGrid2.toString());                    

        // write down both samples in a shared node
        sampleGrid.focus(x2, y2);
        sampleGrid2.focus(x2, y2);
        int samples1 = sampleGrid.getFocusSamples();
        int samples2 = sampleGrid2.getFocusSamples();
        
        // merge both grids
        sampleGrid.merge(sampleGrid2);
        System.out.println("> merged \n" + sampleGrid.toString());                    

        // check merged samples in the shared node
        sampleGrid.focus(x2, y2);
        int samples = sampleGrid.getFocusSamples();

        System.out.println("samples1 = " + samples1);                    
        System.out.println("samples2 = " + samples2);                    
        System.out.println("samples = " + samples);                            
        Assert.assertEquals(samples, samples1+samples2);
    }
    
    // walks a sampled grid covering all points in a specified window (defined by a top-left point (x0, y0) and with w x h dimensions)
    private void doWalk(SampleGrid sampleGrid, int x0, int y0, int w, int h)
    {
        System.out.println("walk grid from (" + x0 + "," + y0 + "), w=" + w + ", h=" + h);                    
        int x1 = x0+w;
        int y1 = y0+h;
        for (int x=x0; x<x1; x++)
        for (int y=y0; y<y1; y++)
        {
            sampleGrid.focus(x, y);            
            sampleGrid.addSample();
            //System.out.println("sample (x,y) = " + x + "," + y + ", node = " + sampleGrid.getFocus().toString());                    
        }        
    }
}

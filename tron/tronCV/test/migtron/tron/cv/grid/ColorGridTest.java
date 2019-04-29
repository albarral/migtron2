/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import migtron.tron.cv.NativeOpenCV;
import migtron.tron.math.Average3f;
import migtron.tron.math.Vec3f;
import migtron.tron.math.Vec3i;
import migtron.tron.math.color.Colors;

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
public class ColorGridTest 
{
    private int w;
    private int h;
    float reductionFactor;
    private ColorGrid colorGrid;
    
    public ColorGridTest() {
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
        w = 30;
        h = 30;
        reductionFactor = 0.1f;
        colorGrid = new ColorGrid(w, h, reductionFactor);                
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of addColorSample method, of class ColorGrid.
     */
    @Test
    public void testAddColorSample() 
    {
        System.out.println("addColorSample");
        
        Vec3i red = Colors.getRGB(Colors.eColor.eCOLOR_RED);
        int x0 = 0;
        int y0 = 20;
        int width = 8;
        // walk grid adding red samples
        doWalk(colorGrid, x0, y0, width, 1, red);
        System.out.println("> grid \n" + colorGrid.toString());                    
     
        // check that focus color is red
        colorGrid.focus(x0, y0);
        Vec3i color = new Vec3i(colorGrid.getFocusColor());
        System.out.println("focus = " + colorGrid.getFocus().toString());                    
        System.out.println("samples = " + colorGrid.getFocusSamples());        
        System.out.println("color = " + color);        
        
        Assert.assertEquals(red, color);
    }

    /**
     * Test of merge method, of class ColorGrid.
     */
    @Test
    public void testMerge() 
    {
        System.out.println("merge");
    
        // create second grid (equal to first one)
        ColorGrid colorGrid2 = (ColorGrid)colorGrid.clone();
        
        // walk first grid
        Vec3i red = Colors.getRGB(Colors.eColor.eCOLOR_RED);
        int x0 = 0;
        int y0 = 0;
        int width = 20;
        int height = 20;
        doWalk(colorGrid, x0, y0, width, height, red);
        System.out.println("> grid1 \n" + colorGrid.toString());                    

        // walk second grid (overlapped with first)
        Vec3i green = Colors.getRGB(Colors.eColor.eCOLOR_GREEN);
        int x2 = x0+10;
        int y2 = y0+10;
        int width2 = 20;
        int height2 = 20;
        doWalk(colorGrid2, x2, y2, width2, height2, green);        
        System.out.println("> grid2 \n" + colorGrid2.toString());                    

        // write down both colors in a shared node
        colorGrid.focus(x2, y2);
        colorGrid2.focus(x2, y2);
        Vec3f color1 = (Vec3f)colorGrid.getFocusColor().clone();
        Vec3f color2 = (Vec3f)colorGrid2.getFocusColor().clone();
        
        // merge both grids
        colorGrid.merge(colorGrid2);
        System.out.println("> merged \n" + colorGrid.toString());                    

        // check merged samples in the shared node
        colorGrid.focus(x2, y2);
        Vec3f avg = Average3f.computeAverage(color1, color2);
        
        System.out.println("color1 = " + color1);                    
        System.out.println("color2 = " + color2);                    
        System.out.println("merged color = " + colorGrid.getFocusColor());                            
        Assert.assertEquals(avg, colorGrid.getFocusColor());
    }
    
    // walks a sampled grid adding color samples to all points in a specified window (defined by a top-left point (x0, y0) and with w x h dimensions)
    private void doWalk(ColorGrid colorGrid, int x0, int y0, int w, int h, Vec3i color)
    {
        //System.out.println("walk grid from (" + x0 + "," + y0 + "), w=" + w + ", h=" + h + ", color=" + color);                    
        int x1 = x0+w;
        int y1 = y0+h;
        for (int x=x0; x<x1; x++)
        for (int y=y0; y<y1; y++)
        {
            colorGrid.focus(x, y);            
            colorGrid.addColorSample(color);
        }        
    }
}

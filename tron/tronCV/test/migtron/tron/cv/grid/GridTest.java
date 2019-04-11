/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

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
public class GridTest 
{
    private int w;
    private int h;
    private Grid grid;
    
    public GridTest() {
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
        grid = new Grid(w, h, reductionFactor);                
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSampledWindow method, of class SampleGrid.
     */
    @Test
    public void testWindowMatrix2GridCoordinates() 
    {
        System.out.println("windowMatrix2GridCoordinates");
        
        Rect window = new Rect(20, 20, 60, 20);        
        Rect window2 = grid.windowMatrix2GridCoordinates(window);
        Rect expResult = new Rect(2,2,6,2);
        System.out.println("matrix window = " + window.toString());
        System.out.println("grid window = " + window2.toString());

        Assert.assertEquals(expResult, window2);
    }

    /**
     * Test of focus method, of class Grid.
     */
    @Test
    public void testFocus() 
    {
        System.out.println("focus");

        System.out.println("horizontal walk");
        int x0 = 0;
        int y0 = 20;
        int width = w;
        doWalk(grid, x0, y0, width, 1);

        System.out.println("vertical walk");
        int x2 = 20;
        int y2 = 0;
        int height = h;
        doWalk(grid, x2, y2, 1, height);
        
        Assert.assertTrue(true);
    }    
    
    // walks a grid covering all points in a specified window (defined by a top-left point (x0, y0) and with w x h dimensions)
    private void doWalk(Grid grid, int x0, int y0, int w, int h)
    {
        System.out.println("walking grid from (x,y) = " + x0 + "," + y0 + ", w = " + w + ", h = " + h);                    
        int x1 = x0+w;
        int y1 = y0+h;
        for (int x=x0; x<x1; x++)
        for (int y=y0; y<y1; y++)
        {
            grid.focus(x, y);            
            System.out.println("sample (x,y) = " + x + "," + y + ", node = " + grid.getFocus().toString());                    
        }        
    }
    
}

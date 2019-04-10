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
        int y = 20;
        for (int x=0; x<w; x++)
        {
            grid.focus(x, y);            
            System.out.println("(x,y) = " + x + "," + y + ", focus = " + grid.getFocus().toString());                    
        }

        System.out.println("vertical walk");
        int x = 20;
        for (y=0; y<h; y++)
        {
            grid.focus(x, y);            
            System.out.println("(x,y) = " + x + "," + y + ", focus = " + grid.getFocus().toString());                    
        }
        
        Assert.assertTrue(true);
    }    
}

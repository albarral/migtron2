/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.image.BufferedImage;

import migtron.tron.util.display.Display;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
* Test class for MaskDrawing class
* @author albarral
 */

public class MaskDrawingTest
{
    public MaskDrawingTest()
    {
    }

    @BeforeClass
    public static void setUpClass() {
        OpenCV.activate();        
    }

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }    
       
    @Test
    public void testMaskRectangles()
    {
        System.out.println("testMaskRectangles");

        Display display = new Display("rectangles");
                
        // create 4 masks (200 x 100)
        MaskDrawing maskDrawing = new MaskDrawing(200, 100);
        maskDrawing.fillTopLeft();        
        showMask(display, maskDrawing);

        maskDrawing.clear();
        maskDrawing.fillTopRight();
        showMask(display, maskDrawing);

        maskDrawing.clear();
        maskDrawing.fillBottomLeft();
        showMask(display, maskDrawing);

        maskDrawing.clear();
        maskDrawing.fillBottomRight();
        showMask(display, maskDrawing);
        Assert.assertTrue(true);
    }
    
    // show list of masks in a display
    private void showMask(Display display, MaskDrawing maskDrawing)
    {
        BufferedImage image = ImageUtils.cvMask2Java(maskDrawing.getMat());
        display.addWindow(image);            
    }
}

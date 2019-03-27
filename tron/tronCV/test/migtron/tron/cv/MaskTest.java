/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import migtron.tron.util.display.Display;
import migtron.tron.math.Ellipse;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
* Test class for Mask class
* @author albarral
 */

public class MaskTest
{
    public MaskTest() {
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
    public void testMaskEllipse()
    {
        System.out.println("testMaskEllipse");
        // create mask
        int w = 200;
        int h = 200;
        Mat mat1 = Mat.zeros(h, w, CvType.CV_8UC1);
        Mat mat2 = mat1.clone();

        // draw ellipse
        int ymid = h/2;
        int xmid = w/2;
        int diameter1 = w/2;
        int diameter2 = diameter1/2;
        int radius1 = diameter1/2;
        int radius2 = diameter2/2;
        float angle = 0f;
        ImageUtils.drawFilledEllipse(mat1, xmid, ymid, radius1, radius2, angle, ImageUtils.eColor.eCOLOR_WHITE);
        ImageUtils.drawEllipse(mat2, xmid, ymid, radius1, radius2, angle, ImageUtils.eColor.eCOLOR_WHITE);
        
        // create list for masks
        List<Mask> listMasks = new ArrayList<>();
        listMasks.add(new Mask(mat1));
        listMasks.add(new Mask(mat2));

        // process masks (compute ellipses)
        List<Mask> listMasks2 = processMasks(listMasks);
        
        // show masks (original and processed)
        showMasks(listMasks2, "testMaskEllipse");
        
        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
        
        Assert.assertTrue(true);
    }

    @Test
    public void testMaskLines()
    {
        System.out.println("testMaskLines");
        // create mask
        int w = 200;
        int h = 200;
        Mat mat1 = Mat.zeros(h, w, CvType.CV_8UC1);
        Mat mat2 = mat1.clone();
        Mat mat3 = mat1.clone();
        Mat mat4 = mat1.clone();

        // draw lines
        int ymid = h/2;
        int xmid = w/2;
        float length = (float)w/4;
        float angle1 = 30.0f;
        float angle2 = -45.0f;
        // horizontal
        ImageUtils.drawLine(mat1, 0, ymid, length, angle1, ImageUtils.eColor.eCOLOR_WHITE);
        ImageUtils.drawLine(mat2, xmid, ymid, length, angle1, ImageUtils.eColor.eCOLOR_WHITE);
        // vertical 
        ImageUtils.drawLine(mat3, xmid, 0, length, angle2, ImageUtils.eColor.eCOLOR_WHITE);
        ImageUtils.drawLine(mat4, xmid, ymid, length, angle2, ImageUtils.eColor.eCOLOR_WHITE);

        // create list for masks
        List<Mask> listMasks = new ArrayList<>();
        listMasks.add(new Mask(mat1));
        listMasks.add(new Mask(mat2));
        listMasks.add(new Mask(mat3));
        listMasks.add(new Mask(mat4));
        
        // process masks (compute ellipses)
        List<Mask> listMasks2 = processMasks(listMasks);
        
        // show masks (original and processed)
        showMasks(listMasks2, "testMaskLines");

        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
        
        Assert.assertTrue(true);
    }

    @Test
    public void testMaskOperations()
    {
        System.out.println("testMaskOperations");

        // create 2 masks (200 x 100)
        MaskDrawing maskDrawing = new MaskDrawing(200, 100);
        maskDrawing.fillTopRight();
        Mask mask1 = new Mask(maskDrawing.getMat());
                
        maskDrawing.clear();
        maskDrawing.fillBottomLeft();
        Mask mask2 = new Mask(maskDrawing.getMat());

        // get union
        Mask maskOr = (Mask)mask1.clone();                
        maskOr.merge(mask2);

        // get intersection
        Mask maskAnd = (Mask)mask1.clone();                
        maskAnd.intersect(mask2);
                
        // create list for masks
        List<Mask> listMasks = new ArrayList<>();
        listMasks.add(mask1);
        listMasks.add(mask2);
        listMasks.add(maskOr);
        listMasks.add(maskAnd);

        // process masks (compute ellipses)
        List<Mask> listMasks2 = processMasks(listMasks);
        
        // show masks (original and processed)
        showMasks(listMasks, "testMaskOperations - original");
        showMasks(listMasks2, "testMaskOperations - processed");

        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}

        Assert.assertTrue(true);
    }    
    
    // show list of masks in a display
    private void showMasks(List<Mask> listMasks, String title)
    {
        Display display = new Display(title);
        
        for (Mask mask : listMasks)
        {
            BufferedImage image = ImageUtils.cvMask2Java(mask.getMat());
            display.addWindow(image);            
        }
    }

    // process list of masks computing their ellipses and drawing them 
    private List<Mask> processMasks(List<Mask> listMasks)
    {
        List<Mask> listMasks2 = new ArrayList<>();
        
        for (Mask mask : listMasks)
        {
            Mask mask2 = computeAndDrawEllipse(mask);
            listMasks2.add(mask2);
        }
        return listMasks2;
    }
    
    private Mask computeAndDrawEllipse(Mask mask)
    {
        // compute ellipse
        Ellipse ellipse = mask.computeEllipse();
        System.out.println("mask: " + ellipse.toString());
        // and draw it in a copy of the mask
        Mask mask2 = (Mask)mask.clone();
        ImageUtils.drawEllipse(mask2.getMat(), ellipse, ImageUtils.eColor.eCOLOR_GREY);
        return mask2;
    }
}

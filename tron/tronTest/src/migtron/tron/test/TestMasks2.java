/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import migtron.tron.cv.Mask;
import migtron.tron.cv.NativeOpenCV;
import migtron.tron.draw.BlocksDrawer;
import migtron.tron.draw.DrawUtils;
import migtron.tron.draw.MathDrawer;
import migtron.tron.math.Ellipse;
import migtron.tron.math.Vec3f;
import migtron.tron.math.color.Colors;
import migtron.tron.util.display.Display;

import org.opencv.core.Rect;

/**
* Test class for Mask class
* @author albarral
 */
public class TestMasks2 
{
    private String modName;
    private int w;
    private int h;
    private List<Mask> listMasks;    // original masks
    private List<Ellipse> listEllipses;     // ellipses of original masks
    private BlocksDrawer blocksDrawer;  // blocks drawing utility
    private MathDrawer mathDrawer;      // ellipses drawing utility
    
    public TestMasks2()
    {
        modName = "TestMasks2";
        // create drawing utilities
        w = 200;
        h = 100;
        blocksDrawer = new BlocksDrawer(w, h, 3);        
        mathDrawer = new MathDrawer(w, h);
        mathDrawer.setStandardColor(Colors.eColor.eCOLOR_GREY);        
        // create lists of masks
        listMasks = new ArrayList<>();
        // create lists of ellipses
        listEllipses = new ArrayList<>();
    }

    public void makeTest()
    {
        System.out.println(modName  + ": test start");     
        //testEllipses();
        testVerticalMerge();
        testHorizontalMerge();
        testDiagonalMerge();
        System.out.println(modName  + ": test end");
    }
    
    public void testEllipses()
    {
        System.out.println("testEllipses");

        resetLists();

        // create ellipse
        Point2D.Float pos = new Point2D.Float(w/2, h/2);
        Vec3f covs = new Vec3f(1600, 800, 0);                
        Ellipse ellipse = new Ellipse(pos, covs);
                           
        mathDrawer.setStandardColor(Colors.eColor.eCOLOR_WHITE);                      
        // draw ellipse
        mathDrawer.drawEllipse(ellipse); 
        // create mask & add to list
        Mask mask = new Mask(mathDrawer.getMat());
        listMasks.add(mask);
        
        // draw filled ellipse
        mathDrawer.clear();
        mathDrawer.drawFilledEllipse(ellipse);        
        // create mask & add to list
        Mask mask2 = new Mask(mathDrawer.getMat());
        listMasks.add(mask2);
                
        processMasks();
        
        mathDrawer.setStandardColor(Colors.eColor.eCOLOR_GREY);                
        drawEllipses();
        showMasks("testEllipses");
    }
        
    /**
     * Test vertical merge of masks
     */
    public void testVerticalMerge() 
    {
        System.out.println("testVerticalMerge");

        resetLists();
        createBlockMask(1, 0);
        createBlockMask(1, 1);

        processMasks();
        Ellipse merge = mergeEllipses(listEllipses);
        
        Mask mask3 = mergeMasks(listMasks);
        listMasks.add(mask3);
        Ellipse ellipse3 = mask3.computeEllipse();
        listEllipses.add(ellipse3);
        
        System.out.println("merged ellipses: " + merge.toString());
        System.out.println("merged masks: " + ellipse3.toString());
                
        drawEllipses();
        showMasks("vertical merge");
    }

    /**
     * Test horizontal merge of masks
     */
    public void testHorizontalMerge() 
    {
        System.out.println("testHorizontalMerge");

        resetLists();
        createBlockMask(1, 0);
        createBlockMask(2, 0);

        processMasks();
        Ellipse merge = mergeEllipses(listEllipses);
        
        Mask mask3 = mergeMasks(listMasks);
        listMasks.add(mask3);
        Ellipse ellipse3 = mask3.computeEllipse();
        listEllipses.add(ellipse3);
        
        System.out.println("merged ellipses: " + merge.toString());
        System.out.println("merged masks: " + ellipse3.toString());
                
        drawEllipses();
        showMasks("horizontal merge");
    }

    /**
     * Test diagonal merge of masks
     */
    public void testDiagonalMerge() 
    {
        System.out.println("testDiagonalMerge");

        resetLists();
        createBlockMask(0, 0);
        createBlockMask(1, 1);

        processMasks();
        Ellipse merge = mergeEllipses(listEllipses);
        
        Mask mask3 = mergeMasks(listMasks);
        listMasks.add(mask3);
        Ellipse ellipse3 = mask3.computeEllipse();
        listEllipses.add(ellipse3);
        
        System.out.println("merged ellipses: " + merge.toString());
        System.out.println("merged masks: " + ellipse3.toString());
                
        drawEllipses();
        showMasks("diagonal merge");
    }
    
    private void resetLists()
    {
        listMasks.clear();
        listEllipses.clear();        
    }
    
     // create new mask with block drawn in specified position
    private void createBlockMask(int x, int y) 
    {
        // draw block
        blocksDrawer.clear();
        blocksDrawer.fillBlock(y, x);        
        // create mask with it & add masks list
        Mask mask = new Mask(blocksDrawer.getMat());
        listMasks.add(mask);
    }

     // merge a list of masks and return the resulting one
    private Mask mergeMasks(List<Mask> listMasks)
    {
        Mask mask2 = null;
        for (Mask mask : listMasks)
        {
            // clone the first one, and merge the rest
            if (mask2 == null)
                mask2 = (Mask)mask.clone();
            else
                mask2.merge(mask);
        }
        return mask2;
    }
    
     // merge a list of ellipses and return the resulting one
    private Ellipse mergeEllipses(List<Ellipse> listEllipses)
    {
        Ellipse ellipse2 = null;
        for (Ellipse ellipse : listEllipses)
        {
            // clone the first one, and merge the rest
            if (ellipse2 == null)
                ellipse2 = (Ellipse)ellipse.clone();
            else
                ellipse2.merge(ellipse);
        }
        return ellipse2;
    }
    
    // process the list of masks (computing their ellipses)
    private void processMasks()
    {
        for (Mask mask : listMasks)
        {
            listEllipses.add(mask.computeEllipse());
        }
    }    
        
    // draw obtained ellipses on their respective masks
    private void drawEllipses()
    {
        int i=0;
        for (Mask mask : listMasks)
        {
            Ellipse ellipse = listEllipses.get(i);
            if (ellipse != null)
                drawEllipseInMask(mask, ellipse);
            i++;
        }
    }

    // draw given mask in given mask
    private void drawEllipseInMask(Mask mask, Ellipse ellipse)
    {
        // set mask as base & draw mask
        mathDrawer.setBase(mask.getMat());
        mathDrawer.drawEllipse(ellipse);
        // put result back to mask
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
    }

}

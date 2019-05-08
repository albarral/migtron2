/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.data;

import java.util.ArrayList;
import java.util.List;

import migtron.tron.cv.Mask;
import migtron.tron.cv.NativeOpenCV;
import migtron.tron.draw.BlocksDrawer;
import migtron.tron.draw.DrawUtils;
import migtron.tron.draw.MathDrawer;
import migtron.tron.math.Vec3f;
import migtron.tron.math.Vec3i;
import migtron.tron.math.color.Colors;
import migtron.tron.util.display.Display;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.opencv.core.Mat;

/**
 *
 * @author albarral
 */
public class BodyTest 
{
    private int w;
    private int h;
    private List<Body> listBodies;    // list of bodies
    private BlocksDrawer blocksDrawer;  // blocks drawing utility
    private MathDrawer mathDrawer;      // ellipses drawing utility
    private int type;    // type of matrix to show results
    
    public BodyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        NativeOpenCV.load();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {   
        // create drawing utilities
        w = 200;
        h = 100;
        blocksDrawer = new BlocksDrawer(w, h, 3);        
        mathDrawer = new MathDrawer(w, h);
        mathDrawer.setStandardColor(Colors.eColor.eCOLOR_GREY);        
        type = blocksDrawer.getMat().type();
        listBodies = new ArrayList<>();
}
    
    @After
    public void tearDown() {
        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
    }

    /**
     * Test of clone method, of class Body.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        Body instance = null;
        Object expResult = null;
        Object result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMask method, of class Body.
     */
    @Test
    public void testSetMask() 
    {
        System.out.println("setMask");
        Mask mask = null;
        Body instance = null;
        instance.setMask(mask);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class Body.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Body instance = null;
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeBorderMask method, of class Body.
     */
    @Test
    public void testComputeBorderMask() {
        System.out.println("computeBorderMask");
        Body instance = null;
        Mat expResult = null;
        Mat result = instance.computeBorderMask();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of merge method, of class Body.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");

        // create body (top left block)
        listBodies.add(createBody(0, 0, Colors.eColor.eCOLOR_RED));
        // create body (center block)
        listBodies.add(createBody(1, 1, Colors.eColor.eCOLOR_GREEN));
        // create body (bottom right block)
        listBodies.add(createBody(2, 2, Colors.eColor.eCOLOR_GREEN));

        // compute sum of original masses
        int mass = 0;
        for (Body body : listBodies)
            mass += body.getMass();
        
        // merge bodies
        Body body2 = mergeBodies();
        listBodies.add(body2);

        showBodies("merge", true);
        
        Assert.assertTrue(mass == body2.getMass());
    }

    /**
     * Test of computeOverlap method, of class Body.
     */
    @Test
    public void testComputeOverlap() {
        System.out.println("computeOverlap");
        Body body = null;
        Body instance = null;
        int expResult = 0;
        int result = instance.computeOverlap(body);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }  

    // creates body with block in given position and color
    private Body createBody(int x, int y, Colors.eColor eColor)
    {
        Vec3i color = Colors.getRGB(eColor);                

        blocksDrawer.clear();
        blocksDrawer.fillBlock(y, x);        
        Mask mask = new Mask(blocksDrawer.getMat(), blocksDrawer.getDrawnWindow());
        return new Body(mask, new Vec3f(color));        
    }

    // merge bodies in list
    private Body mergeBodies()
    {
        if (listBodies.isEmpty())
            return null;

        Body body2 = null;
        // merge all bodies in the list (into a new body)
        for (Body body : listBodies)
        {
            if (body2 == null)
                body2 = (Body)body.clone();
            else
                body2.merge(body);        
        }

        return body2;        
    }

    // show bodies in a display
    private void showBodies(String title, boolean showEllipses)
    {
        Display display = new Display(title);        

        for (Body body : listBodies)
            displayBody(display, body, showEllipses);
    }

    // display body in display (optionally with its representing ellipse)
    private void displayBody(Display display, Body body, boolean showEllipse)
    {
        // copy mask in big frame image
        Mat matShow = Mat.zeros(h, w, type);
        Mat mat2 = matShow.submat(body.getMask().getWindow());
        body.getMask().getMat().copyTo(mat2);
        if (showEllipse)
        {
            // set mask as base & draw mask
            mathDrawer.setBase(matShow);
            mathDrawer.drawEllipse(body.getEllipse());            
        }
        
        display.addWindow(DrawUtils.cvMask2Java(matShow));            
    }
    
}

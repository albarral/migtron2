/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import migtron.tron.util.display.Display;

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
public class BlocksDrawerTest 
{
    private static Display display;
    private BlocksDrawer blocksDrawer;
    
    public BlocksDrawerTest() 
    {
    }
    
    @BeforeClass
    public static void setUpClass() {
        NativeOpenCV.load();
        display = new Display("BlocksDrawerTest");
    }

   @AfterClass
    public static void tearDownClass() {
        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}
    }
    
    @Before
    public void setUp() {
        blocksDrawer = new BlocksDrawer(200, 100, 3);        
    }
    
    @After
    public void tearDown() {
        // wait for a while to see the result
        try {Thread.sleep(500);}
        catch (InterruptedException e) {}
    }

    /**
     * Test of fillBlock method, of class BlocksDrawer.
     */
    @Test
    public void testFillBlock() 
    {
        System.out.println("fillBlock");

        blocksDrawer.fillBlock(0, 0);
        blocksDrawer.fillBlock(1, 1);
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillTop method, of class BlocksDrawer.
     */
    @Test
    public void testFillTop() {
        System.out.println("fillTop");

        blocksDrawer.fillTop();   
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillBottom method, of class BlocksDrawer.
     */
    @Test
    public void testFillBottom() {
        System.out.println("fillBottom");

        blocksDrawer.fillBottom();
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillLeft method, of class BlocksDrawer.
     */
    @Test
    public void testFillLeft() {
        System.out.println("fillLeft");

        blocksDrawer.fillLeft();
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillRight method, of class BlocksDrawer.
     */
    @Test
    public void testFillRight() {
        System.out.println("fillRight");
        
        blocksDrawer.fillRight();
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillTopLeft method, of class BlocksDrawer.
     */
    @Test
    public void testFillTopLeft() {
        System.out.println("fillTopLeft");

        blocksDrawer.fillTopLeft();
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillTopRight method, of class BlocksDrawer.
     */
    @Test
    public void testFillTopRight() {
        System.out.println("fillTopRight");

        blocksDrawer.fillTopRight();
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillBottomLeft method, of class BlocksDrawer.
     */
    @Test
    public void testFillBottomLeft() {
        System.out.println("fillBottomLeft");

        blocksDrawer.fillBottomLeft();
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }

    /**
     * Test of fillBottomRight method, of class BlocksDrawer.
     */
    @Test
    public void testFillBottomRight() {
        System.out.println("fillBottomRight");

        blocksDrawer.fillBottomRight();
        display.addWindow(blocksDrawer.getImage());

        Assert.assertTrue(true);
    }
    
}

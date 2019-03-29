/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.display;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author albarral
 */
public class DisplayerTest {
    
    public DisplayerTest() {
    }
        
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of showImage method, of class Displayer.
     */
    @Test
    public void testShowImage() {
        System.out.println("showImage");
        
        String path1 = "tomas_small.jpg";
        String path2 = "david_small.jpg";
        
        String window1 = "first";
        String window2 = "second";

        // show two images at the same time
        Displayer displayer = new Displayer("displayer");
        displayer.showImage(window1, path1);
        displayer.showImage(window2, path2);
        
        // then exchange them after a while
        try {Thread.sleep(1000);} catch (Exception e) {}
        displayer.showImage(window1, path2);
        displayer.showImage(window2, path1);

        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}

        Assert.assertTrue(true);
    }    
}

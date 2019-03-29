/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.display;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author albarral
 */
public class DisplayTest {
    
    public DisplayTest() {
    }
        
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addWindow method, of class Display.
     */
    @Test
    public void testAddWindow() {
        System.out.println("addWindow");
        
        String path1 = "tomas_small.jpg";
        String path2 = "david_small.jpg";
        // show two images at the same time
        Display display = new Display("display");
        display.addWindow(path1);
        display.addWindow(path2);
        
        // then exchange them after a while
        try {Thread.sleep(1000);} catch (Exception e) {}
        display.updateWindow(0, path2);
        display.updateWindow(1, path1);

        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}

        Assert.assertTrue(true);
    }

    /**
     * Test of addWindow method, of class Display.
     */
    @Test
    public void testAddWindow2() {
        System.out.println("addWindow2");

        // read two images from files
        File file1 = new File("tomas_small.jpg");
        File file2 = new File("david_small.jpg");
        BufferedImage image1, image2; 

        try {
            image1 = ImageIO.read(file1); 
            image2 = ImageIO.read(file2); 
        } 
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // show two images at the same time
        Display display = new Display("display");
        display.addWindow(image1);
        display.addWindow(image2);
        
        // then exchange them after a while
        try {Thread.sleep(1000);} catch (Exception e) {}
        display.updateWindow(0, image2);
        display.updateWindow(1, image1);

        // wait for a while to see the result
        try {Thread.sleep(2000);}
        catch (InterruptedException e) {}

        Assert.assertTrue(true);
    }

}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.test;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import migtron.tron.util.display.Display;
import migtron.tron.util.display.SimpleDisplay;

/**
* Test class for migtron.tron.util.display package.
* @author albarral
 */

public class TestUtilDisplay
{
    String modName;
    
    public TestUtilDisplay()
    {
        modName = "TestUtilDisplay";
    }

    public void makeTest()
    {
        System.out.println(modName  + ": test start");
        //testSimpleDisplay1();
        //testSimpleDisplay2();
        testDisplay1();
        //testDisplay2();
        System.out.println(modName  + ": test end");
    }

    private void testSimpleDisplay1()
    {                    
        String path1 = "tomas_small.jpg";
        String path2 = "david_small.jpg";

        // show two images with a time delay between them
        SimpleDisplay display = new SimpleDisplay("display1");
        display.showImage(path1);        
        try {Thread.sleep(1000);} catch (Exception e) {}
        display.showImage(path2);        
    }

    private void testSimpleDisplay2()
    {                
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
        
        SimpleDisplay display = new SimpleDisplay("display2");
        // show the two images with a time delay between them
        display.showImage(image1);
        try {Thread.sleep(1000);} catch (Exception e) {}
        display.showImage(image2);
    }
    
    private void testDisplay1()
    {
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
    }

    private void testDisplay2()
    {
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
    }
}

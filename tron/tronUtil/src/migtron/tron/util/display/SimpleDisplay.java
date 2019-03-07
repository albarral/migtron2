/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.display;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import migtron.tron.util.box.Files;

/**
 * Utility class to show an image in a display
 * @author albarral
 */
public class SimpleDisplay 
{
    JFrame frame;       // display frame
    JLabel label;          // display label     
    
    public SimpleDisplay(String title)
    {
        frame = new JFrame(title);        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        label = null;
    }
    
    // show image located in given path 
    public void showImage(String imagePath)
    {                
        try 
        {
            if (Files.checkPathExists(imagePath))
            {
                System.out.println("SimpleDisplay: showing image " + imagePath);
                displayLabel(new ImageIcon(imagePath));
            }
            else
                System.out.println("SimpleDisplay: can't show image, path not exists " + imagePath);                                       
        } 
        catch (Exception e) 
        {
          e.printStackTrace();
        }            
    }    
        
    // show buffered image 
    public void showImage(BufferedImage image)
    {                
        try 
        {
            displayLabel(new ImageIcon(image));
        } 
        catch (Exception e) 
        {
          e.printStackTrace();
        }            
    }    

    // create or update label with given image icon
    private void displayLabel(ImageIcon icon)
    {                
        // if first image, create label and add it to frame
        if (label == null)
        {
            label = new JLabel(icon);
            frame.getContentPane().add(label);
            frame.pack();
            frame.setVisible(true);                    
        }
        // otherwise, just update the label
        else
            label.setIcon(icon);
    }
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.display;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import migtron.tron.util.box.Files;

/**
 * Utility class to show multiple images in a display
 * @author albarral
 */
public class Display
{
    private JFrame frame;       // display frame
    private List<JLabel> listWindows;   // list of display windows (JLabels)
    
    public Display(String title)
    {
        frame = new JFrame(title);        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));        
        listWindows = new ArrayList<>();
    }
    
    // add new window to display with image from path
    public void addWindow(String imagePath)
    {      
        // create icon
        ImageIcon icon = createIcon(imagePath);
        addWindow(icon);
    }    
    
    // add new window to display with buffered image
    public void addWindow(BufferedImage image)
    {      
        // create icon
        ImageIcon icon = createIcon(image);
        addWindow(icon);
    }    

    // update existing display window with image from path
    public void updateWindow(int position, String imagePath)
    {                
        // create icon        
        ImageIcon icon = createIcon(imagePath);
        updateWindow(position, icon);
    }
    
    // update existing display window with buffered image
    public void updateWindow(int position, BufferedImage image)
    {                
        // create icon        
        ImageIcon icon = createIcon(image);
        updateWindow(position, icon);
    }
    
    // adds a new window to the display showing an image icon
    private void addWindow(ImageIcon icon)
    {      
        // create label and add it to frame
        if (icon != null)
        {
            JLabel label = new JLabel(icon);
            listWindows.add(label);            
            frame.getContentPane().add(label);            
            // show the frame
            frame.pack();
            frame.setVisible(true);                                    
        }
    }    
    
    // update existing display window with an image icon
    private void updateWindow(int position, ImageIcon icon)
    {                
        if (position < listWindows.size())
        {            
            // update label
            if (icon != null)
                listWindows.get(position).setIcon(icon);
        }
        else
            System.out.println("Display: updateWindow() failed, window not exists " + position);                                                   
    }
    
    private ImageIcon createIcon(String imagePath)
    {
        if (Files.checkPathExists(imagePath))
            return new ImageIcon(imagePath);
        else
            return null;        
    }

    private ImageIcon createIcon(BufferedImage image)
    {
        if (image != null)
            return new ImageIcon(image);
        else
            return null;        
    }       
}

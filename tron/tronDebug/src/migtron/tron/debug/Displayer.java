/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.debug;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import migtron.tron.math.Ellipse;
import migtron.tron.math.color.Colors;
import migtron.tron.util.display.Display;

import org.opencv.core.Mat;

/**
 * Class to display images in a graphical display.
 * A display is a graphical window composed by various sub-windows, each identified by a unique name.
 * It uses a Display to hold the windows and a map of Drawers, one for each window.
 * Implements the iDisplayer interface.
 * @author albarral
 */
public class Displayer implements iDisplayer
{
    private Display display;    
    Map<String, Integer> mapWindows;
    Map<String, Drawer> mapDrawers;
    
    public Displayer(String name)
    {
        display = new Display(name);
        mapWindows = new HashMap<>();
        mapDrawers = new HashMap<>();
    }        

    // set the drawing color for a display window
    @Override
    public void setColor(String windowName, Colors.eColor color)
    {
        Drawer drawer = getWindowDrawer(windowName);
        if (drawer != null)
            drawer.setColor(color);
    }

    // set the additive mode for a display window (when active, new images are supperposed to previsous ones)
    @Override
    public void setAdditive(String windowName, boolean mode)
    {
        Drawer drawer = getWindowDrawer(windowName);
        if (drawer != null)
            drawer.setAdditive(mode);
    }

    // show the image in given path in a display window
    @Override
    public void showImage(String windowName, String imagePath)
    {
        Integer pos = getWindowPosition(windowName);
        // if window is new, create it
        if (pos == null)
        {
            int newPos = display.addWindow(imagePath);
            addWindow2Map(windowName, newPos);
        }
        // otherwise update it
        else             
            display.updateWindow(pos, imagePath);        
    }

    // show given image in a display window
    @Override
    public void showImage(String windowName, BufferedImage image)
    {
        Integer pos = getWindowPosition(windowName);
        // puts image in the display (in new or existing window)
        putImageInDisplay(image, pos, windowName);
    }
            
    // show given mask in a display window
    @Override
    public void showMask(String windowName, Mat mat)
    {
        Drawer drawer = getWindowDrawer(windowName);
        // if no drawer exists for window, create a new one
        if (drawer == null)
        {
            drawer = new Drawer(mat.width(), mat.height());
            addDrawer2Map(windowName, drawer);            
        }        
        drawer.drawMask(mat);
        
        // puts image in the display (in new or existing window)        
        Integer pos = getWindowPosition(windowName);        
        putImageInDisplay(drawer.getImage(), pos, windowName);        
    }

    // show given ellipse in a display window
    @Override
    public void showEllipse(String windowName, Ellipse ellipse)
    {
        Drawer drawer = getWindowDrawer(windowName);
        // if no drawer exists for window, create a new one
        if (drawer == null)
        {
            int size = 2*(int)ellipse.getWidth();
            drawer = new Drawer(size, size);
            addDrawer2Map(windowName, drawer);            
        }        
        drawer.drawEllipse(ellipse);
        
        // puts image in the display (in new or existing window)        
        Integer pos = getWindowPosition(windowName);        
        putImageInDisplay(drawer.getImage(), pos, windowName);                
    }
    
    private Integer getWindowPosition(String windowName)
    {
        return mapWindows.get(windowName);
    }

    private Drawer getWindowDrawer(String windowName)
    {
        return mapDrawers.get(windowName);
    }    
    
    // put image in specified display position
    private void putImageInDisplay(BufferedImage image, Integer position, String windowName)
    {
        // if window is new, create it
        if (position == null)
        {
            int newPos = display.addWindow(image);
            addWindow2Map(windowName, newPos);
        }
        // otherwise update it
        else             
            display.updateWindow(position, image);        
    }
    
    // add window name to window map
    private void addWindow2Map(String windowName, int position)
    {
        mapWindows.put(windowName, position);
    }

    // add drawer to drawers map
    private void addDrawer2Map(String windowName, Drawer drawer)
    {
        mapDrawers.put(windowName, drawer);
    }
}

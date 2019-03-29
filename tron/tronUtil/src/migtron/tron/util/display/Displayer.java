/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.display;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to display images in a graphical display.
 * The display is composed by named windows which can be accessed by their name.
 * @author albarral
 */
public class Displayer
{
    private Display display;    
    private Map<String, Integer> mapWindows;    // mapping of window names to their position in the display
    
    public Displayer(String name)
    {
        display = new Display(name);
        mapWindows = new HashMap<>();
    }        

    // show image file (specified by its path) in a named display window
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

    // show given image in a named display window
    public void showImage(String windowName, BufferedImage image)
    {
        Integer pos = getWindowPosition(windowName);
        // if window is new, create it
        if (pos == null)
        {
            int newPos = display.addWindow(image);
            addWindow2Map(windowName, newPos);
        }
        // otherwise update it
        else             
            display.updateWindow(pos, image);        
    }
            
    
    private Integer getWindowPosition(String windowName)
    {
        return mapWindows.get(windowName);
    }
    
    // add window name to window map
    private void addWindow2Map(String windowName, int position)
    {
        mapWindows.put(windowName, position);
    }
}

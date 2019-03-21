/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Point;
import java.awt.Rectangle;

import migtron.tron.cv.Window;
import org.opencv.core.Rect;

/**
* Utility class to handle matrix explorations.
* A Matrix has 2D limits, a focused point and a neighborhood window around the focus (of radius 1). 
* Whenever the focus changes the limits are checked and the neighborhood window is recomputed (efficiently).
* @author albarral
 */

public class Matrix
{
    // locations of a point in a matrix dimension (regarding borders)
    public enum eLocation{
         eLOC_HEAD,    // initial border
         eLOC_MIDDLE,  // internal (no border)
         eLOC_TAIL   // final border
    }

    protected int w;       // matrix width
    protected int h;       // matrix height
    protected Point focus;   // focused point
    protected Rectangle window; // neighbourhood window (in matrix coordinates)
    private Rectangle relWindow;  // relative neighbourhood window (in focus coordinates)
    private eLocation xLocation;    // focus location in x axis
    private eLocation yLocation;    // focus location in y axis
    private eLocation[] xLocations; // prefixed locations in x axis (precomputed for efficiency)
    private eLocation[] yLocations; // prefixed locations in y axis (precomputed for efficiency)

    public Matrix(int w, int h)
    {
        // safety check
        if (w > 0 && h > 0)
        {
            this.w = w;
            this.h = h;
            focus = new Point();
            window = new Rectangle();
            relWindow = new Rectangle();
            xLocation = null;
            yLocation = null;
            xLocations = new eLocation[w];
            yLocations = new eLocation[h];
            // define the prefixed locations
            informLocations();
        }
    }    
        
    public int geWidth() {return w;};
    public int getHeight() {return h;};
    public Point getFocus() {return focus;}    
    public Rectangle getWindow() {return window;}
    // get neighbourhood window in openCV form
    public Rect getWindowCV() 
    {
        return Window.rectangleJava2CV(window);        
    }
        
    // set matrix focus to given position 
    // it checks for limits and recomputes the neigbourhood window
    // returns true if focus inside limits, false otherwise
    public boolean setFocus(int x, int y)
    {    
        // safety check
        if (x < w && y < h)
        {
            focus.x = x;
            focus.y = y;
            // update x location if changed (updating the relative window internally)
            if (xLocation != xLocations[x])
                updateXLocation(xLocations[x]);
            // update y location if changed (updating the relative window internally)
            if (yLocation != yLocations[y])
                updateYLocation(yLocations[y]);

            // recompute the absolute window
            window = new Rectangle(relWindow);
            window.translate(x, y);
            return true; 
        }
        else
            return false;
    }
   
    public boolean setFocus(Point point)
    {
        return setFocus(point.x, point.y);
    }
        
    // defines the prefixed location arrays
    private void informLocations()
    {
        // create the x locations array
        int xtail = w-1;
        xLocations[0] = eLocation.eLOC_HEAD;
        xLocations[xtail] = eLocation.eLOC_TAIL;
        for (int i=1; i<xtail; i++)
            xLocations[i] = eLocation.eLOC_MIDDLE;

        // create the y locations array
        int ytail = h-1;
        yLocations[0] = eLocation.eLOC_HEAD;
        yLocations[ytail] = eLocation.eLOC_TAIL;
        for (int j=1; j<ytail; j++)
            yLocations[j] = eLocation.eLOC_MIDDLE;
    }
    
    // update x location and compute the x values of the relative window
    private void updateXLocation(eLocation location)
    {
        xLocation = location;
        
        int me = 0;
        int radius = 1;
        int wide = 2*radius + 1;
        int limited = radius + 1;
        switch (location)
        {
            case eLOC_MIDDLE:
                relWindow.x = -radius;
                relWindow.width = wide;
                break;
            case eLOC_HEAD:
                relWindow.x = me;
                relWindow.width = limited;
                break;
            case eLOC_TAIL:
                relWindow.x = -radius;
                relWindow.width = limited;
                break;
        }                    
    }
        
    // update y location and compute the y values of the relative window
    private void updateYLocation(eLocation location)
    {
        yLocation = location;
        
        int me = 0;
        int radius = 1;
        int wide = 2*radius + 1;
        int limited = radius + 1;
        switch (location)
        {
            case eLOC_MIDDLE:
                relWindow.y = -radius;
                relWindow.height = wide;
                break;
            case eLOC_HEAD:
                relWindow.y = me;
                relWindow.height = limited;
                break;
            case eLOC_TAIL:
                relWindow.y = -radius;
                relWindow.height = limited;
                break;
        }                    
    }    
}
							 
/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Point;

import migtron.tron.cv.Window;
import org.opencv.core.Rect;

/**
* Utility class to handle matrix explorations.
* A Matrix has 2D limits, a focused point and a neighborhood window around the setFocus (of radius 1). 
 Whenever the setFocus changes the limits are checked and the neighborhood window is recomputed (efficiently).
* @author albarral
 */

public class Matrix implements Cloneable
{
    // 

    /**
     * Possible locations of a point (regarding the matrix borders) in each matrix dimension 
     */
    public enum eLocation{
        /**
         * point located in the initial border
         */
        eLOC_HEAD,    
        /**
         * point located internally (not in any border) 
         */
         eLOC_MIDDLE,  
        /**
         * point located in the final border
         */
         eLOC_TAIL   
    }

    protected int w;       // matrix width
    protected int h;       // matrix height
    protected Point focus;   // focused point
    protected Rect focusWindow; // neighbourhood window (in matrix coordinates)
    private Rect relWindow;  // relative neighbourhood window (in focus coordinates)
    private eLocation xLocation;    // setFocus location in x axis
    private eLocation yLocation;    // setFocus location in y axis
    private eLocation[] xLocations; // prefixed locations in x axis (precomputed for efficiency)
    private eLocation[] yLocations; // prefixed locations in y axis (precomputed for efficiency)
    private final int radius = 1;       // radius of neighbourhood window
    private final int wide = 2*radius + 1;  // full width (and height) of neighbourhood window
    private final int limited = radius + 1;  // limited width (and height) of neighbourhood window

    public Matrix(int w, int h)
    {
        // safety check        
        if (w <= 0 || h <= 0)
            throw new java.lang.IllegalArgumentException("Matrix dimensions must be positive");
        
        this.w = w;
        this.h = h;
        focus = new Point();
        focusWindow = new Rect();
        relWindow = new Rect();
        xLocation = null;
        yLocation = null;
        xLocations = new eLocation[w];
        yLocations = new eLocation[h];
        // define the prefixed locations
        informLocations();
    }    

    @Override
    public Object clone()
    {
        try {
            Matrix cloned = (Matrix)super.clone();
            cloned.focus = (Point)focus.clone();
            cloned.focusWindow = focusWindow.clone();
            cloned.relWindow = relWindow.clone();
            cloned.xLocations = xLocations.clone();
            cloned.yLocations = yLocations.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }        
    }
        
    public int geWidth() {return w;};
    public int getHeight() {return h;};
    public Point getFocus() {return focus;}    
    /**
     * Get the focus neighbourhood window 
     * @return the window 
     */
    public Rect getFocusWindow() {return focusWindow;}
    /**
     * Set matrix focus to given position. 
     * It checks for limits and recomputes the neigbourhood window.
     * @param x focus x component
     * @param y focus y component
     * @return true if focus inside limits, false otherwise
     */
    public boolean focus(int x, int y)
    {    
        // safety check
        if (x < w && y < h)
        {
            focus.x = x;
            focus.y = y;
            // if x location changed update the relative window horizontally
            if (xLocation != xLocations[x])
            {
                xLocation = xLocations[x];
                updateWindowHorizontally(xLocation);
            }
            // if y location changed update the relative window vertically
            if (yLocation != yLocations[y])
            {
                yLocation = yLocations[y];
                updateWindowVertically(yLocation);
            }

            // recompute the focus neighborhood window
            focusWindow = Window.translate(relWindow, x, y);
            return true; 
        }
        else
            return false;
    }
   
    /**
     * Set matrix focus to given position. 
     * It checks for limits and recomputes the neigbourhood window.
     * @param point new focus position
     * @return true if focus inside limits, false otherwise
     */
    public boolean focus(Point point)
    {
        return focus(point.x, point.y);
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
    
    // update the relative window horizontally given the new location
    private void updateWindowHorizontally(eLocation location)
    {        
        switch (location)
        {
            case eLOC_MIDDLE:
                relWindow.x = -radius;
                relWindow.width = wide;
                break;
            case eLOC_HEAD:
                relWindow.x = 0;
                relWindow.width = limited;
                break;
            case eLOC_TAIL:
                relWindow.x = -radius;
                relWindow.width = limited;
                break;
        }                    
    }
        
    // update the relative window vertically given the new location
    private void updateWindowVertically(eLocation location)
    {
        switch (location)
        {
            case eLOC_MIDDLE:
                relWindow.y = -radius;
                relWindow.height = wide;
                break;
            case eLOC_HEAD:
                relWindow.y = 0;
                relWindow.height = limited;
                break;
            case eLOC_TAIL:
                relWindow.y = -radius;
                relWindow.height = limited;
                break;
        }                    
    }    
}
							 
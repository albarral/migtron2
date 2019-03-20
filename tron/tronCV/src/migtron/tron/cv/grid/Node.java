/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import migtron.tron.cv.Window;
import org.opencv.core.Rect;

/**
* Utility class to represent nodes in grids.
* The node location in the grid (internal, border, corner ...) is used to compute the node's sorrounding window.
* @author albarral
 */

public class Node
{
    // node locations in the grid
    public enum eLocation{
         eLOC_INTERNAL,  // internal
         eLOC_TOP,    // top border
         eLOC_BOTTOM,    // bottom border
         eLOC_LEFT,    // left border
         eLOC_RIGHT,    // right border
         eLOC_TOPLEFT,   // top left corner     
         eLOC_TOPRIGHT,   // top right corner
         eLOC_BOTTOMLEFT,   // bottom left corner
         eLOC_BOTTOMRIGHT  // bottom right corner
    }
    private static final eLocation[] LOCATIONS = eLocation.values();
    private List<Rectangle> listSorroundWindows;     // list of sorrounding windows (in node coordinates), one for each location

    private int row;
    private int col;
    private eLocation location;       // node location in the grid
    private Rect window;       // sorrounding window (in grid coordinates)

    public Node(int row, int col, eLocation location)
    {
        this.row = row;
        this.col = col;
        this.location = location;
        // define generic sorrounding windows
        listSorroundWindows = new ArrayList<>();
        for (int i=0; i < LOCATIONS.length; i++)
        {
            listSorroundWindows.add(computeSorroundWindow(getLocationEnum(i)));
        }
        // compute node's sorround window
        updateWindow();
    }    
            
    // obtain location enum for given location ordinal
    public static eLocation getLocationEnum(int value)
    {
        if (value < LOCATIONS.length)
            return LOCATIONS[value];
        else
            return null;
    }

    public int getRow() {return row;}             
    public int getCol() {return col;} 
    public eLocation getLocation() {return location;};
    public Rect getSorroundWindow() {return window;};
    
    // set node position and location, return true if changed
    public boolean set(int row, int col, int location)
    {
        // set only if different
        if (this.row != row || this.col != col)
        {
            this.row = row;
            this.col = col;
            // location changes only if position changes
            this.location = getLocationEnum(location);          
            // compute sorrounding window
            updateWindow();
            return true;  
        }
        else
            return false;
    }
                            
    // compute node's sorround window (in grid coordinates)
    private void updateWindow()
    { 
        // take the proper sorrounding window (in node coordinates) for the node's location
        Rectangle rectangle = new Rectangle(listSorroundWindows.get(location.ordinal()));
        // and put it in grid coordinates
        rectangle.translate(col, row);      
        // transform to openCV rect
        window = Window.rectangleJava2CV(rectangle);
    }
    
    // compute the sorrounding window (in node coordinates) for a given location
    public static Rectangle computeSorroundWindow(eLocation location)
    {
        int wide = 3;
        int limited = 2;
        int neighbour = -1;
        int me = 0;
        switch (location)
        {
            case eLOC_INTERNAL:
                return new Rectangle(neighbour, neighbour, wide, wide);            
            case eLOC_TOP:
                return new Rectangle(neighbour, me, wide, limited);            
            case eLOC_BOTTOM:
                return new Rectangle(neighbour, neighbour, wide, limited);            
            case eLOC_LEFT:
                return new Rectangle(neighbour, neighbour, limited, wide);            
            case eLOC_RIGHT:
                return new Rectangle(me, neighbour, limited, wide);            
            case eLOC_TOPLEFT:
                return new Rectangle(neighbour, me, limited, limited);            
            case eLOC_TOPRIGHT:
                return new Rectangle(me, me, limited, limited);            
            case eLOC_BOTTOMLEFT: 
                return new Rectangle(neighbour, neighbour, limited, limited);            
            case eLOC_BOTTOMRIGHT:
                return new Rectangle(me, neighbour, limited, limited);     
            default:
                return new Rectangle(0, 0, 0, 0);     
        }                    
    }
}
							 
/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Rect;

/**
* Utility class to represent nodes in grids.
* @author albarral
 */

public class Node
{
    // node LOCATIONS in the grid
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
    private List<Rect> listWindows;     // list of sorrounding windows (one for each eLocation)

    private int row;
    private int col;
    private eLocation location;       // node location in the grid
    private Rect window;   // sorrounding window (depends on the location)

    public Node(int row, int col, eLocation location)
    {
        this.row = row;
        this.col = col;
        defineWindows();
        setLocation(location);
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
    public Rect getWindow() {return window;};
        
    // set node position, return true if changed
    public boolean setPosition(int row, int col)
    {
        // set only if different
        if (this.row != row || this.col != col)
        {
            this.row = row;
            this.col = col;
            return true;
        }
        else
            return false;
    }

    // set node location, return true if changed
    public boolean setLocation(eLocation location)
    {
        // set only if different (also update the sorrounding window)
        if (this.location != location)
        {
            this.location = location;
            window = listWindows.get(location.ordinal());
            return true;
        }
        else
            return false;
    }
    
    // update node position and location, return true if changed
    public boolean update(int row, int col, int location)
    {
        if (setPosition(row, col))
        {            
            // location only changes if position changes
            setLocation(getLocationEnum(location));          
            return true;  
        }
        else
            return false;
    }
        
    // define the list of sorrounding windows for a node
    private void defineWindows()
    {
        for (int i=0; i < LOCATIONS.length; i++)
        {
            eLocation location = getLocationEnum(i);
            listWindows.add(computeWindow(location));
        }
    }
    
    // compute sorrounding window for given location
    private static Rect computeWindow(eLocation location)
    {
        int wide = 3;
        int limited = 2;
        int neighbour = -1;
        int me = 0;
        switch (location)
        {
            case eLOC_INTERNAL:
                return new Rect(neighbour, neighbour, wide, wide);            
            case eLOC_TOP:
                return new Rect(neighbour, me, wide, limited);            
            case eLOC_BOTTOM:
                return new Rect(neighbour, neighbour, wide, limited);            
            case eLOC_LEFT:
                return new Rect(neighbour, neighbour, limited, wide);            
            case eLOC_RIGHT:
                return new Rect(me, neighbour, limited, wide);            
            case eLOC_TOPLEFT:
                return new Rect(neighbour, me, limited, limited);            
            case eLOC_TOPRIGHT:
                return new Rect(me, me, limited, limited);            
            case eLOC_BOTTOMLEFT: 
                return new Rect(neighbour, neighbour, limited, limited);            
            case eLOC_BOTTOMRIGHT:
                return new Rect(me, neighbour, limited, limited);     
            default:
                return new Rect(0, 0, 0, 0);     
        }                    
    }
}
							 
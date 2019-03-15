/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import org.opencv.core.Rect;

/**
* Utility class to represent nodes in grids.
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
    private static final eLocation[] locations = eLocation.values();

    private int row;
    private int col;
    private eLocation location;       // node location in the grid
    private Rect window;   // sorrounding window (depends on the location)

    public Node(int row, int col, eLocation location)
    {
        this.row = row;
        this.col = col;
        this.location = location;
        // compute sorrounding window 
        computeWindow();
    }    

    public Node()
    {
        this(0, 0, eLocation.eLOC_TOPLEFT);
    }    
            
    // obtain location enum for given location ordinal
    public static eLocation getLocationEnum(int value)
    {
        if (value < locations.length)
            return locations[value];
        else
            return null;
    }

    public int getRow() {return row;}             
    public int getCol() {return col;} 
    public eLocation getLocation() {return location;};
    public Rect getWindow() {return window;};
        
    // update node position, return true if changed
    public boolean updatePosition(int row, int col)
    {
        // update only if it's a different position
        if (this.row != row || this.col != col)
        {
            this.row = row;
            this.col = col;
            return true;
        }
        else
            return false;
    }

    // update node location, return true if changed
    public boolean updateLocation(eLocation location)
    {
        // update only if it's a different location
        if (this.location != location)
        {
            this.location = location;
            // and recompute the sorrounding window
            computeWindow();
            return true;
        }
        else
            return false;
    }
    
    // update node position and location, return true if changed
    public boolean update(int row, int col, int location)
    {
        if (updatePosition(row, col))
        {            
            // location may stay unchanged even if position changed  
            updateLocation(getLocationEnum(location));          
            return true;  
        }
        else
            return false;
    }
        
    // compute sorrounding window
    private void computeWindow()
    {
        int wide = 3;
        int limited = 2;
        int neighbour = -1;
        int me = 0;
        switch (location)
        {
            case eLOC_INTERNAL:
                window = new Rect(neighbour, neighbour, wide, wide);            
                break;
            case eLOC_TOP:
                window = new Rect(neighbour, me, wide, limited);            
                break;
            case eLOC_BOTTOM:
                window = new Rect(neighbour, neighbour, wide, limited);            
                break;
            case eLOC_LEFT:
                window = new Rect(neighbour, neighbour, limited, wide);            
                break;
            case eLOC_RIGHT:
                window = new Rect(me, neighbour, limited, wide);            
                break;
            case eLOC_TOPLEFT:
                window = new Rect(neighbour, me, limited, limited);            
                break;
            case eLOC_TOPRIGHT:
                window = new Rect(me, me, limited, limited);            
                break;
            case eLOC_BOTTOMLEFT: 
                window = new Rect(neighbour, neighbour, limited, limited);            
                break;
            case eLOC_BOTTOMRIGHT:
                window = new Rect(me, neighbour, limited, limited);            
                break;
        }                    
    }
}
							 
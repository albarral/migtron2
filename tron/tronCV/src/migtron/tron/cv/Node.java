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
    // node location in a grid
    public enum eLocation{
         eLOC_INTERNAL,  // internal
         eLOC_N,    // north border
         eLOC_S,    // south border
         eLOC_E,    // east border
         eLOC_W,    // west border
         eLOC_NE,   // north east corner     
         eLOC_NW,   // north west corner
         eLOC_SE,   // south east corner
         eLOC_SW,  // south west corner
         eLOC_OUT,  // out of grid
    }

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
        this(0, 0, eLocation.eLOC_NE);
    }    
    
    public int getRow() {return row;}             
    public int getCol() {return col;} 
    public eLocation getLocation() {return location;};
    public Rect getWindow() {return window;};
        
    public void set(int row, int col, eLocation location)
    {
        this.row = row;
        this.col = col;
        // only if location changed compute the sorrounding window
        if (location != this.location)
        {
            this.location = location;
            computeWindow();
        }
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
            case eLOC_N:
                window = new Rect(neighbour, me, wide, limited);            
                break;
            case eLOC_S:
                window = new Rect(neighbour, neighbour, wide, limited);            
                break;
            case eLOC_E:
                window = new Rect(neighbour, neighbour, limited, wide);            
                break;
            case eLOC_W:
                window = new Rect(me, neighbour, limited, wide);            
                break;
            case eLOC_NE:
                window = new Rect(neighbour, me, limited, limited);            
                break;
            case eLOC_NW:
                window = new Rect(me, me, limited, limited);            
                break;
            case eLOC_SE: 
                window = new Rect(neighbour, neighbour, limited, limited);            
                break;
            case eLOC_SW:
                window = new Rect(me, neighbour, limited, limited);            
                break;
        }                    
    }
}
							 
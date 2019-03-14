/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.Point;

import migtron.tron.math.Vec3s;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
* Utility class to handle grids. 
* A grid is a sampled representation of an underlying matrix.
* A mapping is created that maps matrix positions to grid positions. The mapping also stores the node location in the grid (see eLocation values in Node class).
* @author albarral
 */

public class Grid
{
    private int w;  // width of represented matrix
    private int h;  // height of represented matrix
    private int GRID_STEP;       // separation between grid nodes (in matrix units)
    private int rows;       // grid rows
    private int cols;       // grid columns
    private Mat map_nodes;   // mapping of matrix positions to grid nodes

    public Grid(int w, int h, int gridStep)
    {
        resize(w, h, gridStep);
    }    

    public Grid(int w, int h)
    {
        // default grid step 10
        this(w, h, 10);
    }    
        
    public int getRows() {return rows;};
    public int geCols() {return cols;};
    public boolean isValid() {return (map_nodes != null);}
    public Mat getMap() {return map_nodes;}             
    
    private boolean resize(int img_w, int img_h, int grid_step)
    {
        // if grid can not be represented with short precision, reject resizing
        int division = Math.max(img_w, img_h) / grid_step;
        if ((short)division != division)
        {
            return false;
        }
        
        w = img_w;
        h = img_h;
        GRID_STEP = grid_step;
        // (+ 1) because the grid must cover the image borders
        rows = img_h/GRID_STEP + 1;	
        cols = img_w/GRID_STEP + 1;        

        map_nodes = new Mat(h, w, CvType.CV_16UC3);     // (row, col, location)

        // build the mapping matrix
        int row, col;
        for (int i=0; i<h; i++)
        {
            row = Math.round((float)i / GRID_STEP);        
            
            for (int j=0; j<w; j++)
            {    
                col = Math.round((float)j / GRID_STEP);    

                short[] data = {(short)row, (short)col, (short)getNodeLocation(row, col).ordinal()};
                map_nodes.put(row, col, data);
            }
        }        
        return true;
    }
    
    public Node focus(int x, int y)
    {    
        if (x < w && y < h)
        {
            short[] data = new short[3];
            map_nodes.get(y, x, data);
            return new Node(data[0], data[1], data[2]);
        }
        else
            return null;
    }

    public Node focus(Point point)
    {
        return focus(point.x, point.y);
    }

    public Rect computeGridWindow(Rect window)
    {
        // check & correct window limits for safe grid computation
        if (window.x + window.width >= w)
            window.width = w - window.x - 1;
        if (window.y + window.height >= h)
            window.height = h - window.y - 1;

        // translate the image window to a grid window
            
        Vec3s node1 = new Vec3s();
        map_nodes.get(window.y, window.x, node1.data);
        Vec3s node2 = new Vec3s();
        map_nodes.get(window.y + window.height, window.x + window.width, node2.data);
        return new Rect(node1.getY(), node1.getX(), node2.getY()-node1.getY(), node2.getX()-node1.getX());        
    }

    // get node location depending on its position in the grid. 
    private Node.eLocation getNodeLocation(int row, int col)
    {        
        Node.eLocation location = null;

        boolean top = (row == 0);
        boolean bottom = (row == rows-1);
        boolean left = (col == 0);
        boolean right = (col == cols-1);

        // if not in border -> internal
        if (!top && !bottom && !left && !right)
            location = Node.eLocation.eLOC_INTERNAL;
        // in a border
        else
        {        
            // TOP border
            if (top)
            {
                if (left) 
                    location = Node.eLocation.eLOC_NW;
                else if (right)
                    location = Node.eLocation.eLOC_NE;
                else
                    location = Node.eLocation.eLOC_N;            
            }
            // BOTTOM border
            else if (bottom)
            {
                if (left) 
                    location = Node.eLocation.eLOC_SW;
                else if (right)
                    location = Node.eLocation.eLOC_SE;
                else
                    location = Node.eLocation.eLOC_S;                        
            }
            // MIDDLE row
            else
            {
                if (left) 
                    location = Node.eLocation.eLOC_W;
                else if (right)
                    location = Node.eLocation.eLOC_E;            
            }
        }   

        return location;
    }
}
							 
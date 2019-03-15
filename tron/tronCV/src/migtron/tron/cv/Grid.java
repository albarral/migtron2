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
* A grid is a reduced representation of an underlying matrix. It's composed by nodes, each representing a specific matrix region.
* The grid granularity (number of nodes) is defined by the underlying matrix size and the specified sampling factor.
* A map is used internally to rapidly map matrix coordinates to grid ones.
* The node location (internal, border or corner) is also stored in the map to facilitate neighborhood related computations.
* @author albarral
 */

public class Grid
{
    private int w;  // width of represented matrix
    private int h;  // height of represented matrix
    private int gridStep;       // separation between grid nodes (in matrix units)
    private Mat mapCoordinates;   // mapping of matrix to grid coordinates
    protected int rows;       // grid rows
    protected int cols;       // grid columns
    protected Node focusNode;   // node presently focused

    public Grid(int w, int h, float samplingFactor)
    {
        // define grid
        define(w, h, samplingFactor);
        // and set focus to topleft corner
        focusNode = new Node(0, 0, computeNodeLocation(0,0));
    }    
        
    public int getRows() {return rows;};
    public int geCols() {return cols;};
    public boolean isValid() {return (mapCoordinates != null);}
    public Mat getMap() {return mapCoordinates;}             
    
    // defines the grid for given matrix size and sampling factor
    private boolean define(int img_w, int img_h, float samplingFactor)
    {
        // safety check
        if (img_w > 0 && img_h > 0 && samplingFactor > 0.0f && samplingFactor < 1.0f)
        {
            w = img_w;
            h = img_h;
            // compute grid step
            gridStep = (int)(1.0f / Math.sqrt(samplingFactor));
            // (+ 1) because the grid must cover the image borders
            rows = h/gridStep + 1;	
            cols = w/gridStep + 1;        

            // check that grid can be represented with short precision
            int maxCoordinate = Math.max(rows, cols);
            if ((short)maxCoordinate != maxCoordinate)
                return false;
        }
        else
            return false;

        //  (row, col, location) with short precision
        mapCoordinates = new Mat(h, w, CvType.CV_16UC3);    

        // build the coordinates mapping
        int row, col;
        for (int i=0; i<h; i++)
        {
            row = Math.round((float)i / gridStep);        
            
            for (int j=0; j<w; j++)
            {    
                col = Math.round((float)j / gridStep);    

                Vec3s vector = new Vec3s((short)row, (short)col, (short)computeNodeLocation(row, col).ordinal());                
                mapCoordinates.put(row, col, vector.data);
            }
        }        
        return true;
    }
    
    // move focused node to specified matrix position
    // returns true if node has changed, false otherwise
    public boolean focus(int x, int y)
    {    
        // safety check
        if (x < w && y < h)
        {
            // get node mapping
            Vec3s vector = new Vec3s();                
            mapCoordinates.get(y, x, vector.data);
            // update focused node
            return focusNode.update((int)vector.getX(), (int)vector.getY(), (int)vector.getZ());
        }
        else
            return false;
    }

    public boolean focus(Point point)
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
        mapCoordinates.get(window.y, window.x, node1.data);
        Vec3s node2 = new Vec3s();
        mapCoordinates.get(window.y + window.height, window.x + window.width, node2.data);
        return new Rect(node1.getY(), node1.getX(), node2.getY()-node1.getY(), node2.getX()-node1.getX());        
    }

    // compute node location depending on its position in the grid. 
    private Node.eLocation computeNodeLocation(int row, int col)
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
                // left corner
                if (left) 
                    location = Node.eLocation.eLOC_TOPRIGHT;
                // right corner
                else if (right)
                    location = Node.eLocation.eLOC_TOPLEFT;
                else
                    location = Node.eLocation.eLOC_TOP;            
            }
            // BOTTOM border
            else if (bottom)
            {
                // left corner
                if (left) 
                    location = Node.eLocation.eLOC_BOTTOMRIGHT;
                // right corner
                else if (right)
                    location = Node.eLocation.eLOC_BOTTOMLEFT;
                else
                    location = Node.eLocation.eLOC_BOTTOM;                        
            }
            // MIDDLE row
            else
            {
                // left border
                if (left) 
                    location = Node.eLocation.eLOC_RIGHT;
                // right border
                else if (right)
                    location = Node.eLocation.eLOC_LEFT;            
            }
        }   

        return location;
    }
}
							 
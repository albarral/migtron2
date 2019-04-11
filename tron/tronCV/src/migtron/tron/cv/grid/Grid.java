/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Point;

import migtron.tron.math.Vec2i;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
* Utility class to handle grids. 
* A grid is a reduced representation of an underlying matrix. It's composed by nodes, each representing a specific matrix region.
* The grid size results from applying a specified reduction factor to the represented matrix size. (the reduction is applied separately to each dimension)
* The grid uses an internal map to quickly map matrix coordinates to grid ones..
* @author albarral
 */

public class Grid extends Matrix implements Cloneable
{
    private int repW;  // width of represented matrix
    private int repH;  // height of represented matrix
    private float reductionFactor; // reduction factor (applied to each dimension)
    private Mat mapCoordinates;   // mapping of matrix to grid coordinates (int precision)

    public Grid(int repW, int repH, float reductionFactor)
    {
        super((int)Math.ceil(repW*reductionFactor), (int)Math.ceil(repH*reductionFactor));

        // safety check
        if (reductionFactor <= 0f || reductionFactor >= 1f)
            throw new java.lang.IllegalArgumentException("Grid reduction factor must be in the (0, 1) range");
        
        this.repW = repW;
        this.repH = repH;
        this.reductionFactor = reductionFactor;        
        // define mapping of matrix to grid coordinates
        mapCoordinates = new Mat(repH, repW, CvType.CV_32SC2);    
        defineMapping();
    }    
        
    @Override
    public Object clone()
    {
        Grid cloned = (Grid)super.clone();
        cloned.mapCoordinates = mapCoordinates.clone();
        return cloned;
    }
    
    public int getRepresentedWidth() {return repW;};
    public int getRepresentedHeight() {return repH;};
    public float getReductionFactor() {return reductionFactor;};
        
    // get the node representing the given matrix position    
    protected Vec2i getNodeMapping(int x, int y)
    {
        if (x < repW && y < repH)
        {
            Vec2i node = new Vec2i();                
            mapCoordinates.get(y, x, node.data);
            return node;
        }
        else
            return null;        
    }
            
    // set focus to the represented matrix position 
    // it maps the given position to a grid node
    // returns true if focus inside limits, false otherwise
    @Override
    public boolean focus(int x, int y)
    {    
        // get equivalent node
        Vec2i node = getNodeMapping(x, y);
        // if found, move focus to it
        if (node != null)
            return super.focus(node.getX(), node.getY());
        else
            return false;
    }

    // set grid focus to a represented matrix position 
    // it maps the given position to a grid node
    // returns true if focus inside limits, false otherwise
    @Override
    public boolean focus(Point point)
    {
        return focus(point.x, point.y);
    }

    // convert given window in matrix units to equivalent window in grid units
    public Rect windowMatrix2GridCoordinates(Rect window)
    {
        // check & correct window limits for safe grid computation
        limitRepresentedWindow(window);

        // translate the image window to a grid window            
        // get tl node
        Vec2i node1 = getNodeMapping(window.x, window.y);
        // get br node
        org.opencv.core.Point br = window.br();
        Vec2i node2 = getNodeMapping((int)br.x, (int)br.y);                                
        if (node1 != null && node2 != null)
            return new Rect(node1.getX(), node1.getY(), node2.getX()-node1.getX(), node2.getY()-node1.getY());        
        else
            return null;
    }
        
    private void limitRepresentedWindow(Rect window)
    {
        // check & correct window limits for safe grid computation
        if (window.br().x > repW-1)
        {
            int excess = (int)window.br().x - repW +1 ;
            window.width -= excess;
        }
        if (window.br().y > repH-1)
        {
            int excess = (int)window.br().y - repH +1 ;
            window.height -= excess;
        }        
    }
    
    // internal map creation for mapping matrix coordinates to grid ones
    private void defineMapping()
    {        
        int x2, y2;
        // walk rows
        for (int y=0; y<repH; y++)
        {
            // avoid rounding (in order not to place nodes in borders)
            y2 = (int)(y*reductionFactor);                    
            // walk columns
            for (int x=0; x<repW; x++)
            {    
                // avoid rounding (in order not to place nodes in borders)
                x2 = (int)(x*reductionFactor);    
                Vec2i vector = new Vec2i(x2, y2);                
                mapCoordinates.put(y, x, vector.data);
            }
        }        
    }
    
    @Override
    public String toString()
    {
        String desc = "Grid [repW x repH = " + String.valueOf(repW) + " x " + String.valueOf(repH) + ", reduction factor = " + String.valueOf(reductionFactor) + "]";
        return desc;
    }
    
}
							 
/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.geom.Point2D.Float;
import java.awt.Point;

import migtron.tron.util.math.Ellipse;
import migtron.tron.util.math.Vec3f;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
* This class represents a 2D mask. 
* It holds a matrix to hold the pixels and a window to locate the mask in the image.
* The mask is automatically cropped to the window when created.
* @author albarral
 */

public class Mask implements Cloneable
{
    private Mat mat;       // mask matrix
    private Rect window;   // body window in image
    public static final int TYPE = CvType.CV_8UC1;  // matrix type

    public Mask(Mat mat, Rect window)
    {
        this.mat = null;
        this.window = null;
        set(mat, window);
    }    

    public Mask(Mat mat)
    {
        this(mat, new Rect(0, 0, mat.width(), mat.height()));
    }    
    
    public Mask(Mask mask)
    {
        this(mask.mat, mask.window);
    }    
    
    @Override
    public Object clone()
    {
        try {
            Mask cloned = (Mask)super.clone();
            cloned.mat = mat.clone();
            cloned.window = window.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }        
    }

    public boolean isValid() {return (mat != null);}
    public Mat getMat() {return mat;}             
    public Rect getWindow() {return window;};
    
    // set mask matrix and window 
    public boolean set(Mat mat, Rect window)
    {
        boolean bok = false;
        // check it's a single channel mask
        if (mat.type() == TYPE)
        {
            // and that mask & window are valid (window is contained in mask)
           if (mat.height() >= window.y+window.height && 
                   mat.width() >= window.x+window.width && 
                   !mat.empty())
            {    
                // the mask is croped to the window
                this.mat = mat.submat(window).clone();
                this.window = window.clone();
                bok = true;
            }
        }
        return bok;
    }
    
    public void clear()
    {
        mat = null;        
        window = new Rect(0,0,0,0);
    }

    // compute mask area
    public int computeMass()
    {
        return Core.countNonZero(mat);
    }

    // compute the elliptic representation a this mask
    public Ellipse computeEllipse()
    {
        // spatial moments
        int m00 = 0;
        int m10 = 0;
        int m01  = 0;
        long m20 = 0;
        long m11 = 0;
        long m02 = 0;

        int w = mat.width();
        int h = mat.height();
        // for each row
        for (int y=0; y<h; y++)
        {
            // convert row to buffer
            MatOfByte matRow = new MatOfByte(mat.row(y));
            byte[] row = matRow.toArray();
            // walk row
            for (int x=0; x<w; x++)
            {
              // if pixel informed update moments   
              if (row[x] != 0)
              {
                m00 ++;
                m10 += x;
                m01 += y;
                m20 += x*x;
                m11 += x*y;
                m02 += y*y;                  
              }
            }
        }

        // centroid (inside mask window)
        float xo = (float)m10/m00;
        float yo = (float)m01/m00;
        // central moments
        double mu20 = m20 - xo*m10;
        double mu02 = m02 - yo*m01;
        double mu11 = m11 - xo*m01;

        if (m00 != 0)
        {
            // compute centroid and covariances from obtained moments
            return new Ellipse(new Float(xo+window.x, yo+window.y),
                    new Vec3f((float)(mu20/m00), (float)(mu02/m00), (float)(mu11/m00)));
        }
        // if empty mask, null size ellipse returned
        else
            return new Ellipse();        
    }

    // extract a level curve from the mask for the given value
    // the level curve is returned as a mask
    public Mat computeLevelCurve(int value)
    {
        Mat mat2 = new Mat(mat.size(), TYPE);
        Core.compare(mat, new Scalar(value), mat2, Core.CMP_EQ); 
        return mat2;
    }

    // merge this mask with another one
    public void merge(Mask mask)
    {
        // get windows union 
        Rect union = Window.getUnion(window, mask.window);     
        // create empty union matrix
        Mat matUnion = Mat.zeros(union.height, union.width, TYPE); 
        // translate both windows to new coordinate system (the union window)
        Point newOrigin = new Point(union.x, union.y);
        Rect window1 = Window.translateAxes(window, newOrigin);
        Rect window2 = Window.translateAxes(mask.window, newOrigin);

        // fill mask with first body (direct copy)
        Mat mat1 = matUnion.submat(window1);    
        mat.copyTo(mat1);

        // fill mask with second body (logical or)
        Mat mat2 = matUnion.submat(window2);        
        Core.bitwise_or(mask.mat, mat2, mat2);

        // finally update this mask with the merge result
        mat = matUnion;
        window = union;
    }
        
    // intersect this mask with another one
    public void intersect(Mask mask)
    {
        // get windows intersection
        Rect intersection = Window.getIntersection(window, mask.window);     
        // create empty intersection matrix
        Mat matIntersection = Mat.zeros(intersection.height, intersection.width, TYPE); 
        // translate intersection window to both coordinate systems 
        Point origin1 = new Point(window.x, window.y);
        Point origin2 = new Point(mask.window.x, mask.window.y);
        Rect window1 = Window.translateAxes(intersection, origin1);
        Rect window2 = Window.translateAxes(intersection, origin2);
        
        Mat mat1 = mat.submat(window1);    
        Mat mat2 = mask.mat.submat(window2);        
        Core.bitwise_and(mat1, mat2, matIntersection);

        // finally update this mask with the intersection result
        mat = matIntersection;
        window = intersection;
    }

    @Override
    public String toString()
    {
        String desc = "Mask [window = (" + String.valueOf(window.x) + "," + String.valueOf(window.y) + "," + String.valueOf(window.width) + "," + String.valueOf(window.height) + ")]";
        return desc;
    }
}
							 
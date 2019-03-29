/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import java.awt.Point;
import java.awt.geom.Point2D;

import migtron.tron.cv.ImageUtils;
import migtron.tron.math.Coordinates;
import migtron.tron.math.Ellipse;
               
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;        
import org.opencv.core.Scalar;

/**
 * Class to draw images of math entities.
 * @author albarral
 */
public class MathDrawer extends Drawer
{
    public MathDrawer(int w, int h)
    {
        super(w, h);
    }        
    
    // draw a vector specified in polar coordinates from an origin cartesian point
    public void drawVector(Point point, float length, float angle)
    {
        // get vector in cartesian form
        Point2D.Float vector = Coordinates.computeCartesian(length, angle);
        // compute cartesian points 
        // important: y sign changed as image y axis is opposite to world y axis
        org.opencv.core.Point origin = new org.opencv.core.Point(point.x, point.y);
        org.opencv.core.Point end = new org.opencv.core.Point(point.x+vector.x, point.y-vector.y);
        Core.line(mat, origin, end, color);                        
    }
    
    // draw an ellipse (curve only)
    public void drawEllipse(Ellipse ellipse)
    {
        Core.ellipse(mat, ImageUtils.ellipse2RotatedRect(ellipse), color);                        
    }

    // draw a filled ellipse (whole surface)
    public void drawFilledEllipse(Ellipse ellipse)
    {
        Core.ellipse(mat, ImageUtils.ellipse2RotatedRect(ellipse), color, Core.FILLED);                        
    }

    // draw a rectangle (perimeter only)
    public void drawRectangle(Rect window)
    {
        Core.rectangle(mat, window.tl(), window.br(), color);        
    }

    // draw a filled rectangle (whole surface)
    public void drawFilledRectangle(Rect window)
    {
        Core.rectangle(mat, window.tl(), window.br(), color, Core.FILLED);        
    }

    // draw a filled rectangle (whole surface) in a given matrix with a given color
    static public void drawFilledRectangle(Mat mat, Rect window, Scalar color)
    {
        Core.rectangle(mat, window.tl(), window.br(), color, Core.FILLED);        
    }
}

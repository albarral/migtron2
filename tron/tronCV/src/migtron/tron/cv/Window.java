/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Rect;

/**
 * Utility class to perform mathematical operations on OpenCV windows 
 * @author albarral
 */
public class Window 
{
    // transform cv rectangle to java form
    public static Rectangle getRectangle(Rect rect)
    {
        return new Rectangle(rect.x, rect.y, rect.width, rect.height);
    }

    // transform java rectangle to cv form
    public static Rect getCVRectangle(Rectangle rectangle)
    {
        return new Rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    // get the rectangle resulting from the intersection of two windows
    public static Rect getIntersection(Rect window1, Rect window2)
    {
        Rectangle rect1 = getRectangle(window1);
        Rectangle rect2 = getRectangle(window2);        
        return getCVRectangle(rect1.intersection(rect2));        
    }

    // get the rectangle resulting from the union of two windows
    public static Rect getUnion(Rect window1, Rect window2)
    {
        Rectangle rect1 = getRectangle(window1);
        Rectangle rect2 = getRectangle(window2);        
        return getCVRectangle(rect1.union(rect2));        
    }

    // translate given window the specified x and y displacement
    public static Rect translate(Rect window, int dx, int dy)
    {
        return new Rect(window.x + dx, window.y + dy, window.width, window.height);        
    }

    // translate given window to new top left point
    public static Rect translateTo(Rect window, Point point)
    {
        return new Rect(point.x, point.y, window.width, window.height);        
    }
    
    // translate given window to new axes origin
    public static Rect translateAxes(Rect window, Point newOrigin)
    {
        return new Rect(window.x - newOrigin.x, window.y - newOrigin.y, window.width, window.height);        
    }
    
    // compute the overlaped area of two windows
    public static int getOverlapArea(Rect window1, Rect window2)
    {
        Rect intersection = getIntersection(window1, window2);
        return (intersection.width * intersection.height);
    }

    // compute the fraction of window1 overlapped by window2
    public static float getOverlapFraction1(Rect window1, Rect window2)
    {
        int overlap =  getOverlapArea(window1, window2);

        if (window1.width > 0 && window1.height > 0)
            return (float)(overlap) / (window1.width * window1.height);
        else
            return 0.0f;
    }

    // compute the fraction of window2 overlapped by window1
    public static float getOverlapFraction2(Rect window1, Rect window2)
    {
        int overlap =  getOverlapArea(window1, window2);

        if (window2.width > 0 && window2.height > 0)
            return (float)(overlap) / (window2.width * window2.height);
        else
            return 0.0f;
    }

    // compute the minimum distance between the vertices of two windows
    public static float getSeparation(Rect window1, Rect window2)
    {
        // get intersection of windows
        Rectangle rect1 = getRectangle(window1);
        Rectangle rect2 = getRectangle(window2);        
        Rectangle intersection = rect1.intersection(rect2);

        // case 1: windows overlap -> separation 0
        if (intersection.width > 0)  
            return 0.0f;
        else
        {
            float sep;
            Rectangle union = rect1.union(rect2);

            // case 2A: windows overlap vertically (in x plane) -> separation = y distance
            if (union.width < window1.width + window2.width)
            {
                int bottom1, top2;
                // window1 is over window2
                if (window2.y > window1.y)
                {
                    bottom1 = window1.y + window1.height;
                    top2 = window2.y;
                }
                // window2 is over window1
                else
                {
                    bottom1 = window2.y + window2.height;
                    top2 = window1.y;
                }
                sep = top2 - bottom1; 
            }
            // case 2B: windows overlap horizontally (in y plane) -> separation = x distance
            else if (union.height < window1.height + window2.height)
            {
                int rightBorder1, leftBorder2;
                // window2 comes after window1
                if (window2.x > window1.x)
                {
                    rightBorder1 = window1.x + window1.width;
                    leftBorder2 = window2.x;
                }
                // window1 comes after window2
                else
                {
                    rightBorder1 = window2.x + window2.width;
                    leftBorder2 = window1.x;
                }
                sep = leftBorder2 - rightBorder1; 
            }        
            // case 3: windows don't overlap in any plane -> separation = minimum distance between vertices
            else
            {        
                List<Point> vertices1 = getVertices(rect1);
                List<Point> vertices2 = getVertices(rect2);

                // compute minimum distance between both windows vertices
                sep = (float)Math.sqrt(getMinSqrDistanceBetweenPoints(vertices1, vertices2));
            }
            
            return sep;
        }
    }

    // get the four vertices of the given window
    public static List<Point> getVertices(Rectangle window)
    {
        int right = window.x + window.width;
        int bottom = window.y + window.height;

        List<Point> vertices = new ArrayList<>();
        vertices.add(new Point (window.x, window.y));
        vertices.add(new Point (right, window.y));
        vertices.add(new Point (window.x, bottom));
        vertices.add(new Point (right, bottom));

        return vertices;
    }
    
    // get the minimum distance between two lists of points
    private static float getMinSqrDistanceBetweenPoints(List<Point> points1, List<Point> points2)
    {
        Float minimum = null;
        float sqrDist;
        for (Point point1 : points1)
            for (Point point2 : points2)
            {
                sqrDist = (float)point1.distanceSq(point2);
                if (minimum == null || sqrDist < minimum)
                    minimum = sqrDist;            
            }

        return minimum;
    }


}

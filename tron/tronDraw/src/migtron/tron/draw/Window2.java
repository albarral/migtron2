/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 * Utility class to perform mathematical operations on OpenCV windows 
 * @author albarral
 */
public class Window2 
{        
    // get the rectangle resulting from the union of two windows
    public static Rect getUnion(Rect window1, Rect window2)
    {
        Rect union = addPoint(window1, window2.tl());
        union = addPoint(union, window2.br());
        return union;
    }

    // add point to window
    private static Rect addPoint(Rect window, Point point)
    {
        // if point out of window, enlarge window
        if (!window.contains(point))
        {
            // compute new top left point
            Point tl = window.tl();            
            Point tl2 = new Point(Math.min(point.x, tl.x), Math.min(point.y, tl.y));
            // compute new bottom right point
            Point br = window.br();
            Point br2 = new Point(Math.max(point.x, br.x), Math.max(point.y, br.y));
            return new Rect(tl2, br2);
        }
        // otherwise do nothing
        else
            return window;
    }

}

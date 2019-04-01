/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.geom.Point2D;
import org.opencv.core.Point;

/**
 * Utility class for conversions between OpenCV and java classes
 * @author albarral
 */
public class Conversions
{
    // convert opencv point to java point (integer version)
    public static java.awt.Point pointCv2Java(Point point)
    {
        return new java.awt.Point((int)point.x, (int)point.y);
    }

    // convert opencv point to java point (float version)
    public static Point2D.Float pointCv2JavaFloat(Point point)
    {
        return new Point2D.Float((float)point.x, (float)point.y);
    }
}

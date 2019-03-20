/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import org.opencv.core.Point;
import org.opencv.core.Point3;


/**
 * Utility class to compute averages on OpenCV points
 * @author albarral
 */
public class AverageCV
{
    // calculates the average of an array of 2D points
    public static Point compute2DAverage(Point[] points)
    {
        Point avg = new Point(0, 0);
        for (Point point : points)
        {
            avg.x += point.x;
            avg.y += point.y;
        }
        
        if (points.length != 0)
        {
            double factor = 1.0/points.length;
            avg.x *= factor;
            avg.y *= factor;
        }
        
        return avg;
    }

    // calculates the weighted average of an array of 2D points
    public static Point compute2DWeightedAverage(Point[] points, int[] weights)
    {
        Point avg = new Point(0, 0);
        // safety check (both arrays must have same size)
        if (points.length != weights.length)
        {
            return avg;            
        }

        long total = 0;
        for (int i=0; i<points.length; i++)
        {
            Point point = points[i];
            int weight = weights[i];
            avg.x += point.x*weight;
            avg.y += point.y*weight;
            total += weight;
        }
        
        if (points.length != 0)
        {
            double factor = 1.0/total;
            avg.x *= factor;
            avg.y *= factor;
        }
        
        return avg;
    }
    
    // calculates the average of an array of 3D points
    public static Point3 compute3DAverage(Point3[] points)
    {
        Point3 avg = new Point3(0, 0, 0);
        for (Point3 point : points)
        {
            avg.x += point.x;
            avg.y += point.y;
            avg.z += point.z;                        
        }
        
        if (points.length != 0)
        {
            double factor = 1.0/points.length;
            avg.x *= factor;
            avg.y *= factor;
            avg.z *= factor;            
        }
        
        return avg;
    }

    // calculates the weighted average of an array of 3D points
    public static Point3 compute3DWeightedAverage(Point3[] points, int[] weights)
    {
        Point3 avg = new Point3(0, 0, 0);
        // safety check (both arrays must have same size)
        if (points.length != weights.length)
        {
            return avg;            
        }

        long total = 0;
        for (int i=0; i<points.length; i++)
        {
            Point3 point = points[i];
            int weight = weights[i];
            avg.x += point.x*weight;
            avg.y += point.y*weight;
            avg.z += point.z*weight;
            total += weight;
        }
        
        if (points.length != 0)
        {
            double factor = 1.0/total;
            avg.x *= factor;
            avg.y *= factor;
            avg.z *= factor;            
        }
        
        return avg;
    }
}

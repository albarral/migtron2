/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import org.opencv.core.Point;
import org.opencv.core.Point3;

/**
 * Utility class to perform mathematical operations on OpenCV points
 * @author albarral
 */
public class Distance 
{
    // calculates the squared Euclidean distance between two points
    public static float getEuclideanSqr(Point p1, Point p2)
    {
        float x = (float)p1.x - (float)p2.x;
        float y = (float)p1.y - (float)p2.y;

        return (x*x + y*y);            
    }

    // calculates the squared Mahalanobis distance between two 2D points, with covariances (cx, cy, cxy)
    public static float getMahalanobisSqr(Point p1, Point p2, Point3 covariances)
    {
        float x = (float)p1.x - (float)p2.x;
        float y = (float)p1.y - (float)p2.y;
        float cx = (float)covariances.x;
        float cy = (float)covariances.y;
        float cxy = (float)covariances.z;

        return ((x*x*cy+ y*y*cx - 2*x*y*cxy) / (cx*cy - cxy*cxy));
    }
    
    
}

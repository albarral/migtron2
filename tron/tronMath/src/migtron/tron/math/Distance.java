/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

import java.awt.geom.Point2D.Float;
import java.awt.Point;

/**
 * Utility class to compute distance between points
 * @author albarral
 */
public class Distance 
{
    // calculates the squared Euclidean distance between two points
    public static float getEuclideanSquare(Float p1, Float p2)
    {
        float x = (float)p1.x - (float)p2.x;
        float y = (float)p1.y - (float)p2.y;

        return (x*x + y*y);            
    }

    // calculates the squared Euclidean distance between two points
    public static float getEuclideanSquare(Point p1, Point p2)
    {
        float x = (float)p1.x - (float)p2.x;
        float y = (float)p1.y - (float)p2.y;

        return (x*x + y*y);            
    }

    // calculates the squared Mahalanobis distance between two 2D points, with covariances (cx, cy, cxy)
    public static float getMahalanobisSquare(Float p1, Float p2, Vec3f covariances)
    {
        float x = (float)p1.x - (float)p2.x;
        float y = (float)p1.y - (float)p2.y;
        float cx = (float)covariances.getX();
        float cy = (float)covariances.getY();
        float cxy = (float)covariances.getZ();

        return ((x*x*cy+ y*y*cx - 2*x*y*cxy) / (cx*cy - cxy*cxy));
    }

    // calculates the squared Mahalanobis distance between two 2D points, with covariances (cx, cy, cxy)
    public static float getMahalanobisSquare(Point p1, Point p2, Vec3f covariances)
    {
        float x = (float)p1.x - (float)p2.x;
        float y = (float)p1.y - (float)p2.y;
        float cx = (float)covariances.getX();
        float cy = (float)covariances.getY();
        float cxy = (float)covariances.getZ();

        return ((x*x*cy+ y*y*cx - 2*x*y*cxy) / (cx*cy - cxy*cxy));
    }        
}

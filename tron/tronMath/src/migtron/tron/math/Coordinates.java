/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

import java.awt.Point;
import java.awt.geom.Point2D.Float;

/**
 * Utility class to perform conversions of spatial coordinates
 * @author albarral
 */
public class Coordinates 
{
    // transform position from cartesian to polar: (x, y) ->  (magnitude, angle)
    public static Float computePolar(float x, float y)
    {
        float magnitude = (float)Math.sqrt(x*x + y*y);        
        float radians = (float)Math.atan2(y, x);
        float angle = (float)Math.toDegrees(radians);       
        return new Float(magnitude, angle);
    }

    // transform position from polar to cartesian: (magnitude, angle) -> (x, y)
    public static Float computeCartesian(float magnitude, float angle)
    {
        float radians = (float)Math.toRadians(angle);       
        float x = magnitude*(float)Math.cos(radians);
        float y = magnitude*(float)Math.sin(radians);        
        return new Float(x, y);
    }
    
    // transform position from cartesian to polar: (x, y) ->  (magnitude, angle)
    public static Float computePolar(Float cartesianPos)
    {
        return computePolar(cartesianPos.x, cartesianPos.y);
    }

    // transform position from polar to cartesian: (magnitude, angle) -> (x, y)
    public static Float computeCartesian(Float polarPos)
    {
        return computeCartesian(polarPos.x, polarPos.y);
    }
   
    // transform position from cartesian (integer values) to polar: (x, y) ->  (magnitude, angle)
    public static Float computePolar(Point cartesianPos)
    {
        return computePolar((float)cartesianPos.x, (float)cartesianPos.y);
    }

    // transform position from polar (integer values) to cartesian: (magnitude, angle) -> (x, y)
    public static Float computeCartesian(Point polarPos)
    {
        return computeCartesian((float)polarPos.x, (float)polarPos.y);
    }
    
    // transform position from cartesian to polar (both integer values): (x, y) ->  (magnitude, angle)
    public static Point computePolarPoint(Point cartesianPos)
    {
        Float polarPos = computePolar(cartesianPos);
        return float2Point(polarPos);
    }
    
    // transform position from polar to cartesian (both integer values): (magnitude, angle) -> (x, y)
    public static Point computeCartesianPoint(Point polarPos)
    {
        Float cartesianPos = computeCartesian(polarPos);
        return float2Point(cartesianPos);
    }
    
//    // convert integer point to float point
//    private static Float point2Float(Point point)
//    {
//        return new Float((float)point.x, (float)point.y);
//    }

    // convert float point to integer point
    public static Point float2Point(Float floatPoint)
    {
        return new Point((int)floatPoint.x, (int)floatPoint.y);
    }
}

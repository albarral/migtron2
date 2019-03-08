/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Class to represent a 3D vector with integer precision.
 * Direct access granted to vector components.
 * @author albarral
 */
public class Vec3i
{
    public static final int SIZE = 3;
    public int[] data;
    
    public Vec3i(int x, int y, int z)
    {
        data = new int[SIZE];
        data[0] = x;
        data[1] = y;
        data[2] = z;
    }
    
    public Vec3i(Vec3i vector2)
    {
        this(vector2.getX(), 
                vector2.getY(), 
                vector2.getZ());
    }
    
    public int getX() {return data[0];};
    public int getY() {return data[1];};
    public int getZ() {return data[2];};
    public void setX(int value) {data[0] = value;};
    public void setY(int value) {data[1] = value;};
    public void setZ(int value) {data[2] = value;};
    
    public void set(Vec3i vector2)
    {
        data[0] = vector2.getX();
        data[1] = vector2.getY();
        data[2] = vector2.getZ();        
    }

    public float getEuclideanSqrDistance(Vec3i vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];
        float z = data[2] - vector2.data[2];

        return (x*x + y*y + z*z);        
    }
    
    public float getMahalanobisSqrDistance(Vec3i vector2, Vec3f covariance)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];
        float z = data[2] - vector2.data[2];

        return ((x*x/covariance.data[0]) + (y*y/covariance.data[1]) + (z*z/covariance.data[2]));
    }

    @Override
    public String toString()
    {
        String desc = "(" + String.valueOf(data[0]) + "," + String.valueOf(data[1]) + "," + String.valueOf(data[2]) + ")";
        return desc;
    }    
}

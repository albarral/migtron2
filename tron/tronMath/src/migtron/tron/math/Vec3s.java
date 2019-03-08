/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Class to represent a 3D vector with short precision.
 * Direct access granted to vector components.
 * @author albarral
 */
public class Vec3s
{
    public static final int SIZE = 3;
    public short[] data;
    
    public Vec3s(short x, short y, short z)
    {
        data = new short[SIZE];
        data[0] = x;
        data[1] = y;
        data[2] = z;
    }
    
    public Vec3s(Vec3s vector2)
    {
        this(vector2.getX(), 
                vector2.getY(), 
                vector2.getZ());
    }
    
    public short getX() {return data[0];};
    public short getY() {return data[1];};
    public short getZ() {return data[2];};
    public void setX(short value) {data[0] = value;};
    public void setY(short value) {data[1] = value;};
    public void setZ(short value) {data[2] = value;};
    
    public void set(Vec3s vector2)
    {
        data[0] = vector2.getX();
        data[1] = vector2.getY();
        data[2] = vector2.getZ();        
    }
    
    public float getEuclideanSqrDistance(Vec3s vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];
        float z = data[2] - vector2.data[2];

        return (x*x + y*y + z*z);        
    }
    
    public float getMahalanobisSqrDistance(Vec3s vector2, Vec3f covariance)
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

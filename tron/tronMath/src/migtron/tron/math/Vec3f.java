/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Class to represent a 3D vector with float precision.
 * Direct access granted to vector components.
 * @author albarral
 */
public class Vec3f implements Cloneable
{
    public static final int SIZE = 3;
    public float[] data;
    
    public Vec3f(float x, float y, float z)
    {
        data = new float[SIZE];
        data[0] = x;
        data[1] = y;
        data[2] = z;
    }

    public Vec3f()
    {
        this(0.0f, 0.0f, 0.0f);
    }    

    public Vec3f(Vec3f vector2)
    {
        this(vector2.getX(), 
                vector2.getY(), 
                vector2.getZ());
    }

    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        Vec3f cloned = (Vec3f)super.clone();
        cloned.data = this.data.clone();
        return cloned;
    }
    
    public void assign(Vec3f vector2)
    {
        data[0] = vector2.getX();
        data[1] = vector2.getY();
        data[2] = vector2.getZ();        
    }
    
    public float getX() {return data[0];};
    public float getY() {return data[1];};
    public float getZ() {return data[2];};
    public void setX(float value) {data[0] = value;};
    public void setY(float value) {data[1] = value;};
    public void setZ(float value) {data[2] = value;};
    
    public float getEuclideanSqrDistance(Vec3f vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];
        float z = data[2] - vector2.data[2];

        return (x*x + y*y + z*z);        
    }
    
    public float getMahalanobisSqrDistance(Vec3f vector2, Vec3f covariance)
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

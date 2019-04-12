/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

import java.util.Arrays;


/**
 * Class to represent a 3D vector with integer precision.
 * Direct access granted to vector components.
 * @author albarral
 */
public class Vec3i implements Cloneable
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

    public Vec3i()
    {
        data = new int[SIZE];
    }
    
    // create from float vector
    public Vec3i(Vec3f vector2)
    {
        this((int)vector2.getX(), (int)vector2.getY(), (int)vector2.getZ());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        Vec3i cloned = (Vec3i)super.clone();
        cloned.data = this.data.clone();
        return cloned;
    }
    
    @Override
    public boolean equals(Object o) 
    {
        if (this == o) 
            return true;
        
        if (o == null || getClass() != o.getClass()) 
            return false;
        
        Vec3i vec3i = (Vec3i)o;
        return Arrays.equals(data, vec3i.data);
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

    // get euclidean square distance to an integer vector
    public float getEuclideanSquareDistance(Vec3i vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];
        float z = data[2] - vector2.data[2];

        return (x*x + y*y + z*z);        
    }

    // get euclidean square distance to a short vector
    public float getEuclideanSquareDistance(Vec3s vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];
        float z = data[2] - vector2.data[2];

        return (x*x + y*y + z*z);        
    }
    
    // get euclidean distance to an integer vector
    public float getEuclideanDistance(Vec3i vector2)
    {
        return (float)Math.sqrt(getEuclideanSquareDistance(vector2));
    }

    // get euclidean distance to a short vector
    public float getEuclideanDistance(Vec3s vector2)
    {
        return (float)Math.sqrt(getEuclideanSquareDistance(vector2));
    }
    
    @Override
    public String toString()
    {
        String desc = "(" + String.valueOf(data[0]) + ", " + String.valueOf(data[1]) + ", " + String.valueOf(data[2]) + ")";
        return desc;
    }    
}

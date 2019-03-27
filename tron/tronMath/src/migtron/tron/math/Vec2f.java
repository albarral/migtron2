/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

import java.util.Arrays;


/**
 * Class to represent a 2D vector with float precision.
 * Direct access granted to vector components.
 * @author albarral
 */
public class Vec2f implements Cloneable
{
    public static final int SIZE = 2;
    public float[] data;
    
    public Vec2f(float x, float y)
    {
        data = new float[SIZE];
        data[0] = x;
        data[1] = y;
    }

    public Vec2f()
    {
        data = new float[SIZE];
    }
    
    public Vec2f(Vec2f vector2)
    {
        this(vector2.getX(), 
                vector2.getY());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        Vec2f cloned = (Vec2f)super.clone();
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
        
        Vec2f vec2f = (Vec2f)o;
        return Arrays.equals(data, vec2f.data);
    }
    
    public float getX() {return data[0];};
    public float getY() {return data[1];};
    public void setX(float value) {data[0] = value;};
    public void setY(float value) {data[1] = value;};
    
    public void set(Vec2f vector2)
    {
        data[0] = vector2.getX();
        data[1] = vector2.getY();
    }

    // get euclidean square distance to an float vector
    public float getEuclideanSquareDistance(Vec2f vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];

        return (x*x + y*y);        
    }
    
    // get euclidean distance to an float vector
    public float getEuclideanDistance(Vec2f vector2)
    {
        return (float)Math.sqrt(getEuclideanSquareDistance(vector2));
    }
    
    @Override
    public String toString()
    {
        String desc = "(" + String.valueOf(data[0]) + ", " + String.valueOf(data[1]) + ")";
        return desc;
    }    
}

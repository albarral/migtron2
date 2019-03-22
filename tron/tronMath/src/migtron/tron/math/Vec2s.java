/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Class to represent a 2D vector with short precision.
 * Direct access granted to vector components.
 * @author albarral
 */
public class Vec2s implements Cloneable
{
    public static final int SIZE = 2;
    public short[] data;
    
    public Vec2s(short x, short y)
    {
        data = new short[SIZE];
        data[0] = x;
        data[1] = y;
    }
    
    public Vec2s()
    {
        data = new short[SIZE];
    }

    public Vec2s(Vec2s vector2)
    {
        this(vector2.getX(), 
                vector2.getY());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        Vec2s cloned = (Vec2s)super.clone();
        cloned.data = this.data.clone();
        return cloned;
    }
    
    public short getX() {return data[0];};
    public short getY() {return data[1];};
    public void setX(short value) {data[0] = value;};
    public void setY(short value) {data[1] = value;};
    
    public void set(Vec2s vector2)
    {
        data[0] = vector2.getX();
        data[1] = vector2.getY();
    }

    // get euclidean square distance to an short vector
    public float getEuclideanSquareDistance(Vec2s vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];

        return (x*x + y*y);        
    }
    
    // get euclidean distance to an short vector
    public float getEuclideanDistance(Vec2s vector2)
    {
        return (float)Math.sqrt(getEuclideanSquareDistance(vector2));
    }
    
    @Override
    public String toString()
    {
        String desc = "(" + String.valueOf(data[0]) + "," + String.valueOf(data[1]) + ")";
        return desc;
    }    
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Class to represent a 2D vector with integer precision.
 * Direct access granted to vector components.
 * @author albarral
 */
public class Vec2i implements Cloneable
{
    public static final int SIZE = 2;
    public int[] data;
    
    public Vec2i(int x, int y)
    {
        data = new int[SIZE];
        data[0] = x;
        data[1] = y;
    }
    
    public Vec2i()
    {
        data = new int[SIZE];
    }

    public Vec2i(Vec2i vector2)
    {
        this(vector2.getX(), 
                vector2.getY());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        Vec2i cloned = (Vec2i)super.clone();
        cloned.data = this.data.clone();
        return cloned;
    }
    
    public int getX() {return data[0];};
    public int getY() {return data[1];};
    public void setX(int value) {data[0] = value;};
    public void setY(int value) {data[1] = value;};
    
    public void set(Vec2i vector2)
    {
        data[0] = vector2.getX();
        data[1] = vector2.getY();
    }

    // get euclidean square distance to an integer vector
    public float getEuclideanSquareDistance(Vec2i vector2)
    {
        float x = data[0] - vector2.data[0];
        float y = data[1] - vector2.data[1];

        return (x*x + y*y);        
    }
    
    // get euclidean distance to an integer vector
    public float getEuclideanDistance(Vec2i vector2)
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

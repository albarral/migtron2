/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.data;

import java.awt.geom.Point2D.Float;
import java.awt.Point;

import migtron.tron.math.Ellipse;
import migtron.tron.math.Vec3f;

/**
* This class represents a blob, the basic visual entity in the tron system.
* A blob is represented by an ellipse with a mass.
* @author albarral
 */

public class Blob extends Ellipse implements Cloneable
{
    protected int mass;                  // area in pixels
    protected float shapeFactor;      // automatically computed  
    
    public Blob(Point pos, Vec3f covs, int mass)
    {
        super(new Float(pos.x, pos.y), covs);
        this.mass = mass;    
        updateShapeFactor();        
    }    

    public Blob()
    {
        this(new Point(0, 0), new Vec3f(0, 0, 0), 0);
    }

    public Blob(Blob blob)
    {
        this(blob.getPointPosition(), blob.getCovariances(), blob.getMass());
    }    
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        return (Blob)super.clone();
    }
    
    public int getMass() {return mass;};
    public float getShapeFactor() {return shapeFactor;};    
    public void setMass(int value) {mass = value;};

    // automatic computation of the shape factor from the ellipse main axes
    protected void updateShapeFactor()
    {
        shapeFactor = computeShapeFactor();        
    }
    
    // merge this blob with another blob
    public void merge(Blob blob2)
    {
        int newMass = mass + blob2.getMass();
        if (newMass != 0)
        {
            float w1 = (float)mass / newMass;
            float w2 = (float)mass / newMass;
            // main axes are internally recomputed
            super.mergeEllipse(blob2, w1, w2);
            mass = newMass;      
            updateShapeFactor();
        }
    }
    
    @Override
    public void clear()
    {
        super.clear();
        mass = 0;
        shapeFactor = 0f; 
    }

    @Override
    public String toString()
    {
        String desc = "Blob [mass = " + String.valueOf(mass) + ", shape_factor = " + String.valueOf(shapeFactor) + "] " + super.toString();
        return desc;
    }

    public String shortDesc()
    {
        String desc = "Blob [mass = " + String.valueOf(mass) + ", pos = (" + String.valueOf(pos.x) + "," + String.valueOf(pos.y) + ")]";
        return desc;
    }
}

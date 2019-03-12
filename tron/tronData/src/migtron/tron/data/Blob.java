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
    
    public Blob(Ellipse ellipse, int mass)
    {
        super(ellipse.getPosition(), ellipse.getCovariances());
        this.mass = mass;   
        // shape factor automatically computed  
        updateShapeFactor();        
    }    

    public Blob(Point pos, Vec3f covs, int mass)
    {
        this(new Ellipse(pos, covs), mass);
    }    
            
    public Blob()
    {
        this(new Ellipse(), 0);
    }

    public Blob(Blob blob)
    {
        super((Ellipse)blob);
        mass = blob.mass;
        shapeFactor = blob.shapeFactor;
    }    
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        // all members automatically copied
        return (Blob)super.clone();
    }
    
    public void copy(Blob blob)
    {
        super.copy((Ellipse)blob);
        mass = blob.mass;
        shapeFactor = blob.shapeFactor;
    }
    
    public int getMass() {return mass;};
    public float getShapeFactor() {return shapeFactor;};    
    public void setMass(int value) {mass = value;};
    
    // merge this blob with another blob
    public void merge(Blob blob2)
    {
        int newMass = mass + blob2.getMass();
        if (newMass != 0)
        {
            // merge ellipses
            float w1 = (float)mass / newMass;
            float w2 = (float)mass / newMass;
            super.mergeEllipse(blob2, w1, w2);
            // update mass and shape factor
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

    // automatic computation of the shape factor from the ellipse main axes
    protected void updateShapeFactor()
    {
        shapeFactor = computeShapeFactor();        
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

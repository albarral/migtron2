/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.data;

import java.awt.geom.Point2D.Float;
import java.awt.Point;

import migtron.tron.math.Ellipse;
import migtron.tron.math.Vec3f;
import migtron.tron.math.color.Color;

/**
* This class represents a blob, the basic visual entity in the tron vision system.
* A blob is represented by an ellipse and has a given mass and color. Its shape factor is computed from its underlying ellipse.
* @author albarral
 */

public class Blob implements Cloneable
{
    protected Ellipse ellipse;
    protected int mass;                  // area in pixels
    protected float shapeFactor;      // automatically computed  
    protected Color color;
    
    public Blob(Ellipse ellipse, int mass, Color color)
    {
        this.ellipse = (Ellipse)ellipse.clone();
        // shape factor automatically computed  
        updateShapeFactor();        
        this.mass = mass;   
        this.color = (Color)color.clone();
    }    

    public Blob(Point pos, Vec3f covs, int mass, Vec3f rgbColor)
    {
        this(new Ellipse(pos, covs), mass, new Color(rgbColor));
    }    
                
    @Override
    public Object clone() // throws CloneNotSupportedException 
    {
        try {
            // all members automatically copied
            // then class members cloned for deep copy
            Blob cloned = (Blob)super.clone();
            cloned.ellipse = (Ellipse)ellipse.clone();
            cloned.color = (Color)color.clone();
            return cloned;        
        }
        catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }        
    }
    
    public void copy(Blob blob)
    {
        ellipse = (Ellipse)blob.ellipse.clone();
        mass = blob.mass;
        shapeFactor = blob.shapeFactor;
        color = (Color)blob.color.clone();
    }
    
    public Ellipse getEllipse() {return ellipse;}
    public int getMass() {return mass;}
    public float getShapeFactor() {return shapeFactor;}
    public Color getColor() {return color;}
    
    public void setEllipse(Ellipse ellipse)
    {
        this.ellipse = (Ellipse)ellipse.clone();
        // shape factor automatically computed  
        updateShapeFactor();        
    }    

    public void setMass(int value) {mass = value;}

    public void setColor(Color color)
    {
        this.color = (Color)color.clone();
    }
    
    // merge this blob with another blob
    public void merge(Blob blob2)
    {
        int newMass = mass + blob2.mass;
        if (newMass != 0)
        {
            // merge ellipses
            float w1 = (float)mass / newMass;
            float w2 = (float)blob2.mass / newMass;
            ellipse.merge(blob2.ellipse, w1, w2);
            // update mass and shape factor
            updateShapeFactor(); 
            mass = newMass;      
            // merge colors
            color.merge(blob2.color, mass, blob2.mass);
        }
    }
    
    public void clear()
    {
        ellipse.clear();
        mass = 0;
        shapeFactor = 0f; 
    }

    // automatic computation of the shape factor from the ellipse main axes
    private void updateShapeFactor()
    {
        shapeFactor = ellipse.computeShapeFactor();        
    }

    @Override
    public String toString()
    {
        String desc = "Blob [mass = " + String.valueOf(mass) + ", shape_factor = " + String.valueOf(shapeFactor) + ellipse.toString() + color.toString() + "]";
        return desc;
    }

    public String shortDesc()
    {
        Float pos = ellipse.getPosition();
        String desc = "Blob [mass = " + String.valueOf(mass) + ", pos = (" + String.valueOf(pos.x) + "," + String.valueOf(pos.y) + ")" + color.toString() + "]";
        return desc;
    }
}

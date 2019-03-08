/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.vision.features;

import migtron.tron.cv.Mask;
import migtron.tron.data.ColorBlob;
import migtron.tron.math.Ellipse;

import org.opencv.core.Mat;

/**
* This class represents a 2D body. 
* It's a ColorBlob with a mask and a location window (locates the body in the image)
* @author albarral
 */

public class Body extends ColorBlob implements Cloneable
{
    public static final short BODY_VALUE = 255;
    public static final short BORDER_VALUE = 1;
    protected Mask mask;       // body mask

    public Body(ColorBlob colorBlob, Mask mask)
    {
        super(colorBlob);        
        this.mask = new Mask(mask);
    }    
        
    public Body(Body body)
    {
        this((ColorBlob)body, 
                body.getMask());
    }    
    
    @Override
    public Object clone()
    {
        try {
            Body cloned = (Body)super.clone();
            cloned.mask = (Mask)mask.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }                
    }
    
    public Mask getMask() {return mask;}             

    @Override
    public void clear()
    {
        super.clear();
        mask.clear();
    }

    // new mask's area is computed
    public void computeMass()
    {
        setMass(mask.computeMass());
    }

    public void computeShape()
    {
        Ellipse ellipse = mask.computeEllipse();
        this.copy(ellipse);
        updateShapeFactor();
    }

    public Mat computeBorderMask()
    {
        Mat maskBorder = mask.computeLevelCurve(BORDER_VALUE);
        return maskBorder;    
    }

    public void merge(Body oBody)
    {
        // merge color blob part
        super.merge((ColorBlob)oBody);
        // merge mask    
        mask.merge(oBody.mask);
    }

    // compute the overlapped area between this body and another one
    public int computeOverlap(Body body)
    {
        Mask mask1 = (Mask)mask.clone();
        mask1.intersect(body.getMask());
        return mask1.computeMass();
    }

    @Override
    public String toString()
    {
        String desc = "Body [" + mask.toString() + " " + super.toString() + "]";
        return desc;
    }

    @Override
    public String shortDesc()
    {
        String desc = "Body [" + mask.toString() + " " + super.shortDesc() + "]";
        return desc;
    }
}
							 
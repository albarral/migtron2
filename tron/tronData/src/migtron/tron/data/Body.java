/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.data;

import migtron.tron.cv.Mask;
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
        super((Blob)colorBlob, colorBlob.rgbColor);        
        this.mask = (Mask)mask.clone();
    }    
                
    @Override
    public Object clone()
    {
        // all members automatically copied
        // then class members cloned for deep copy
        Body cloned = (Body)super.clone();
        cloned.mask = (Mask)mask.clone();
        return cloned;
    }
    
    public Mask getMask() {return mask;}    
    public void setMask(Mask mask)
    {
        this.mask = (Mask)mask.clone();
        // recompute blob from new mask
        updateBlob();
    }

    @Override
    public void clear()
    {
        super.clear();
        mask.clear();
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
        // no need to update the blob (it's been merged internally)
    }

    // compute the overlapped area between this body and another one
    public int computeOverlap(Body body)
    {
        Mask mask1 = (Mask)mask.clone();
        mask1.intersect(body.getMask());
        return mask1.computeMass();
    }

    // recomputes the blob from the body's mask
    private void updateBlob()
    {
        Ellipse ellipse = mask.computeEllipse();
        super.copy(ellipse);
        setMass(mask.computeMass());
        updateShapeFactor();
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
							 
/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.data;

import java.awt.Point;
import migtron.tron.math.color.RGBColor;
import migtron.tron.math.Vec3f;

/**
* This class represents a colored blob.
* @author albarral
 */

public class ColorBlob extends Blob implements Cloneable
{
    protected Vec3f rgbColor;     // color in RGB space
    protected Vec3f hsvColor;     // color in HSV space (automatically computed)
    
    public ColorBlob(Point pos, Vec3f covs, int mass, Vec3f rgbColor)
    {
        super(pos, covs, mass);
        this.rgbColor = new Vec3f(rgbColor);
        updateHSVColor();
    }    
    
//    public ColorBlob()
//    {
//        this(new Point(0, 0), 
//                new Vec3f(0, 0, 0), 
//                0, 
//                new Vec3f(0, 0, 0));
//    }

    public ColorBlob(ColorBlob colorBlob)
    {
        this(colorBlob.getPointPosition(), 
                colorBlob.getCovariances(), 
                colorBlob.getMass(), 
                colorBlob.getRGB());
    }    

    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        ColorBlob cloned = (ColorBlob)super.clone();
        cloned.rgbColor = (Vec3f)rgbColor.clone();
        cloned.hsvColor = (Vec3f)hsvColor.clone();
        return cloned;
    }

    public Vec3f getRGB() {return rgbColor;};
    public Vec3f getHSV() {return hsvColor;};    
    // set the rgb color (the hsv color is automatically updated)
    public void setRGB(Vec3f color) 
    {
        rgbColor.assign(color);
        updateHSVColor();
    }

    // automatic computation of the HSV color from the RGB color
    private void updateHSVColor()
    {
        hsvColor.assign(RGBColor.toHSV(rgbColor));       
    }

    // merge this color blob with another color blob
    public void merge(ColorBlob colorBlob2)
    {
        // merge blobs
        super.merge(colorBlob2);
        // merge colors
        Vec3f newRGB = RGBColor.mergeValues(rgbColor, colorBlob2.getRGB(), mass, colorBlob2.getMass());
        // update rgb (hsv automatically updated)
        setRGB(newRGB);            
    }
                  
    @Override
    public String toString()
    {
        String desc = "ColorBlob [rgb = " + rgbColor.toString() + ", hsv = " + hsvColor.toString() + "] " + super.toString();
        return desc;
    }

    @Override
    public String shortDesc()
    {
        String desc = "ColorBlob [hsv = " + hsvColor.toString() + "] " + super.shortDesc();
        return desc;
    }
}

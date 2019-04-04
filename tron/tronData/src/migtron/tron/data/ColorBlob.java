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
    
    public ColorBlob(Blob blob, Vec3f rgbColor)
    {
        super(blob);
        this.rgbColor = new Vec3f(rgbColor);
        hsvColor = new Vec3f();
        updateHSVColor();
    }    

    public ColorBlob(Point pos, Vec3f covs, int mass, Vec3f rgbColor)
    {
        this(new Blob(pos, covs, mass), rgbColor);
    }    
    
    public ColorBlob()
    {
        this(new Blob(), new Vec3f(0, 0, 0));
    }

    public ColorBlob(ColorBlob colorBlob)
    {
        super((Blob)colorBlob);
        rgbColor = new Vec3f(colorBlob.rgbColor);
        hsvColor = new Vec3f(colorBlob.hsvColor);
    }    

    @Override
    public Object clone() //throws CloneNotSupportedException 
    {
        // all members automatically copied
        // then class members cloned for deep copy
        ColorBlob cloned = (ColorBlob)super.clone();
        cloned.rgbColor = (Vec3f)rgbColor.clone();
        cloned.hsvColor = (Vec3f)hsvColor.clone();
        return cloned;
    }
        
    public void copy(ColorBlob colorBlob)
    {
        super.copy((Blob)colorBlob);
        rgbColor = new Vec3f(colorBlob.rgbColor);
        hsvColor = new Vec3f(colorBlob.hsvColor);
    }

    public Vec3f getRGB() {return rgbColor;};
    public Vec3f getHSV() {return hsvColor;};    
    // set the rgb color (the hsv color is automatically updated)
    public void setRGB(Vec3f color) 
    {
        rgbColor.set(color);
        updateHSVColor();
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
                  
    // automatic computation of the HSV color from the RGB color
    private void updateHSVColor()
    {
        hsvColor.set(RGBColor.toHSV(rgbColor));       
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

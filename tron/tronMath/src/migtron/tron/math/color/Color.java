/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math.color;

import migtron.tron.math.Vec3f;

/**
* This class represents a color.
* It manages the color value in RGB and HSV spaces.
* @author albarral
 */

public class Color implements Cloneable
{
    protected Vec3f rgbColor;     // color in RGB space
    protected Vec3f hsvColor;     // color in HSV space (automatically computed)
    
    public Color(Vec3f rgbColor)
    {
        this.rgbColor = (Vec3f)rgbColor.clone();
        hsvColor = new Vec3f();
        computeHSV();
    }    
    
    @Override
    public Object clone() //throws CloneNotSupportedException 
    {
        try {
            // all members automatically copied
            // then class members cloned for deep copy
            Color cloned = (Color)super.clone();
            cloned.rgbColor = (Vec3f)rgbColor.clone();
            cloned.hsvColor = (Vec3f)hsvColor.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }        
    }
        
    public Vec3f getRGB() {return rgbColor;};
    public Vec3f getHSV() {return hsvColor;};    

    // set the rgb color (the hsv color is automatically updated)
    public void setRGB(Vec3f color) 
    {
        rgbColor.set(color);
        computeHSV();
    }

    // merge this color with another one using the specified quantities
    public void merge(Color color2, int q1, int q2)
    {
        // merge colors
        Vec3f newRGB = RGBColor.mergeValues(rgbColor, color2.rgbColor, q1, q2);
        // update rgb (hsv automatically updated)
        setRGB(newRGB);            
    }
                  
    // automatic computation of the HSV color from the RGB color
    private void computeHSV()
    {
        hsvColor.set(RGBColor.toHSV(rgbColor));       
    }

    public String toString()
    {
        String desc = "Color [rgb = " + rgbColor.toString() + ", hsv = " + hsvColor.toString() + "]";
        return desc;
    }
}

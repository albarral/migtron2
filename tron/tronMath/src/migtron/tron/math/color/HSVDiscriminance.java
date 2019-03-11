/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math.color;

import migtron.tron.math.Vec3f;

/**
* Utility class to compute HSV discriminance for color comparison.
* @author albarral
 */

public class HSVDiscriminance
{
    public static final float MIN_SAT_DISC = 25.0f;  // minimum discriminable sat difference
    public static final float MIN_VAL_DISC= 25.0f;   // minimum discriminable value difference
    private float hueDisc;          // maximum hue difference allowed for a SAME color condition
    private float satTolerance;  // tolerance allowed in saturation for a SAME color condition
    private float valTolerance;  // tolerance allowed in value for a SAME color condition
        
    public HSVDiscriminance(float hueDisc, float satTolerance, float valTolerance)
    {
        this.hueDisc = hueDisc;
        this.satTolerance = satTolerance;
        this.valTolerance = valTolerance;
    }

    public HSVDiscriminance(HSVDiscriminance hsvDisc)
    {
        this(hsvDisc.hueDisc, hsvDisc.satTolerance, hsvDisc.valTolerance);
    }

    public float getHueDisc() {return hueDisc;};
    public float getSaturationTolerance() {return satTolerance;};
    public float getValueTolerance() {return valTolerance;};
    
    public void setHueDisc(float value)  {hueDisc = value;}
    public void setSaturationTolerance(float value) {satTolerance = value;}
    public void setValueTolerance(float value) {valTolerance = value;}
    
    // compute HSV discriminance when comparing two HSV colors
    // hue discriminance is fixed, the other result from applying a tolerance to their maximum values
    public Vec3f getDiscriminance(Vec3f color1, Vec3f color2)
    {
        float maxSat = Math.max(color1.getY(), color2.getY());
        float maxVal = Math.max(color1.getZ(), color2.getZ());

        return new Vec3f(hueDisc, getSaturationDisc(maxSat), getValueDisc(maxVal));
    }

    // compute HSV discriminance for a given HSV color
    // hue discriminance is fixed, the other result from applying a tolerance
    public Vec3f getDiscriminance(Vec3f color)
    {
        return new Vec3f(hueDisc, getSaturationDisc(color.getY()), getValueDisc(color.getZ()));
    }
    
    private float getSaturationDisc(float sat)
    {
        // limit lower saturation discriminance
        return Math.max(sat * satTolerance, MIN_SAT_DISC);
    }

    private float getValueDisc(float val)
    {
        // limit lower value discriminance
        return Math.max(val * valTolerance, MIN_VAL_DISC);
    }
}

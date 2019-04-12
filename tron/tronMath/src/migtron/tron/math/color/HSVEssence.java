/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math.color;

import migtron.tron.math.Angle;
import migtron.tron.math.Vec3f;

/**
* Utility class to manage the HSV color essence of a given entity. The color essence is classified as colored, grey or dark.
* It allows comparisons between the color essence and other hsv colors using different measures depending on the color essence:
* colored: maximum of hue, saturation & value differences
* grey: maximum of saturation & value differences (hue ignored)
* dark: value difference (hue & saturation ignored)
* DRAWBACKS: sharp changes on the type classification produce discontinuities in color comparisons. 
* @author albarral
 */

public class HSVEssence
{    
    // types of color essence    
    public enum eType {
        eTYPE_COLOR,
        eTYPE_GREY,
        eTYPE_DARK
    };
    
    Vec3f hsvColor;    // hsv color
    eType type;         // color type
    HSVDiscriminance hsvDisc; // discriminance 
                
    public HSVEssence(Vec3f hsvColor)
    {
        this.hsvColor = (Vec3f)hsvColor.clone();
        // automatic type computation           
        computeType();
        // high hue discriminance, but low saturation and value discriminances
        hsvDisc = new HSVDiscriminance(10.0f, 0.50f, 0.50f);
    }
    
    public Vec3f getColor() {return hsvColor;};
    public eType getType() {return type;};
    public HSVDiscriminance getDiscriminance() {return hsvDisc;};

    public void setColor(Vec3f hsvColor2)
    {
        hsvColor.set(hsvColor2);
        // automatic type computation
        computeType();
    }
    
    public void setDiscriminance (HSVDiscriminance disc)
    {
        hsvDisc = new HSVDiscriminance(disc);        
    }   

    private void computeType()
    {
        // low value -> dark essence
        if (hsvColor.getZ() < HSVColor.VAL_DARK)
            type = eType.eTYPE_DARK;
        // low saturation -> grey essence
        else if (hsvColor.getY() < HSVColor.SAT_GRAY)
            type = eType.eTYPE_GREY;
        // else -> color essence
        else
            type = eType.eTYPE_COLOR;      
    }

    float getDistance(Vec3f hsvColor2)
    {
        // obtain discriminance components
        Vec3f disc = hsvDisc.getDiscriminance(hsvColor);
        
        float dist;
        switch (type)
        {
            case eTYPE_COLOR:
            {
                float hueDif = Angle.cyclicDifference(hsvColor.getX() - hsvColor2.getX()); // cyclic hue correction                                
                hueDif = hueDif / disc.getX();
                float satDif = Math.abs(hsvColor.getY() - hsvColor2.getY()) / disc.getY();
                float valDif = Math.abs(hsvColor.getZ() - hsvColor2.getZ()) / disc.getZ();
                // maximum of the 3 distances
                dist = Math.max(satDif, valDif);
                dist = Math.max(hueDif, dist);
            }
                break;

            case eTYPE_GREY:
            {
                float satDif = Math.abs(hsvColor.getY() - hsvColor2.getY()) / disc.getY();
                float valDif = Math.abs(hsvColor.getZ() - hsvColor2.getZ()) / disc.getZ();
                // maximum of saturation and value distances
                dist = Math.max(satDif, valDif);
            }
                break;

            case eTYPE_DARK:
            {
                float valDif = Math.abs(hsvColor.getZ() - hsvColor2.getZ()) / disc.getZ();
                // value distance
                dist = valDif;
            }
                break;

            default:
                dist = 0;
                break;
        }

        return dist;    
    }
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math.color;

import migtron.tron.math.Angle;
import migtron.tron.math.Vec3f;

/**
* Utility class to implement HSV color comparison and conversion to other color spaces.
* @author albarral
 */

public class HSVColor
{    
    public static final float HUE_SECTOR = 60.0f;
    public static final int SAT_RANGE = 256;
    public static final int VAL_RANGE = 256;
    public static final int SAT_GRAY = 50;	// under this saturation all colors are considered grey
    public static final int VAL_DARK = 50; 	// under this value all colors are considered black
    public static final float DIST_SAME_COLOR = 1.0f;  // maximum distance at which 2 HSV colors can be considered the same
    // discriminance level for color comparison
    public enum eDiscriminance {
     eDISC_HIGH,
     eDISC_LOW,
    };
    
    eDiscriminance discLevel;
    HSVDiscriminance hsvDisc;
    private float tab_gray_correction[];    // color correction table for grey saturations
    private float tab_dark_correction[];	 // color correction table for dark values
                
    public HSVColor(eDiscriminance discLevel)
    {
        this.discLevel = discLevel;
        switch (discLevel)
        {
            case eDISC_HIGH:
                hsvDisc = new HSVDiscriminance(10.0f, 0.25f, 0.25f);
                break;
            case eDISC_LOW:
                hsvDisc = new HSVDiscriminance(20.0f, 0.50f, 0.50f);
                break;
        }
        
        tab_gray_correction = new float[SAT_RANGE];
        tab_dark_correction = new float[VAL_RANGE];
        initCorrectionTables();       
    }

    public HSVColor()
    {
        // high discriminance used by default
        this(eDiscriminance.eDISC_HIGH);
    }
    
    public eDiscriminance getDiscriminanceLevel() {return discLevel;};
    public HSVDiscriminance getDiscriminance() {return hsvDisc;};
    

    // precalculate the values of the color correction tables
    private void initCorrectionTables()
    {
        float Kgray;
        for (int i=0; i<tab_gray_correction.length;  i++)
        {
            if (i < SAT_GRAY) 
                Kgray = 0.0f;
            else
            {
                Kgray = (float)((i - SAT_GRAY) / 50.0);
                // limit to 1
                if (Kgray > 1.0f)
                    Kgray = 1.0f;
            }

            tab_gray_correction[i] = Kgray;
        }

        float Kdark;
        for (int i=0; i<tab_dark_correction.length;  i++)
        {
            if (i < VAL_DARK) 
                Kdark = 0.0f;
            else
            {
                Kdark = (float)((i - VAL_DARK) / 50.0);
                // limit to 1
                if (Kdark > 1.0f)
                    Kdark = 1.0f;
            }

            tab_dark_correction[i] = Kdark;
        }
    }
    
    // compute the Mahalanobis distance between 2 HSV colors using HSV color discriminance
    public float getDistance(Vec3f color1, Vec3f color2)
    {
        float minSat = Math.min(color1.getY(), color2.getY());
        float minVal = Math.min(color1.getZ(), color2.getZ());

        float Kgray = tab_gray_correction[(int)minSat];  // reduce H influence if gray region or pixel
        float Kdark = tab_dark_correction[(int)minVal];  // reduce H and S influence if dark region or pixel  

        // obtain discriminance components
        Vec3f disc = hsvDisc.getDiscriminance(color1, color2);

        // use discriminance and correction factors
        float hueDif = Angle.cyclicDifference(color1.getX() - color2.getX()); // cyclic hue correction
        hueDif = Kgray * Kdark * hueDif / disc.getX();
        float satDif = Kdark * (color1.getY() - color2.getY()) / disc.getY();
        float valDif = (color1.getZ() - color2.getZ()) / disc.getZ();

        // mahalanobis distance (with null cross-covariances)
        float dist = (float)Math.sqrt(hueDif*hueDif + satDif*satDif + valDif*valDif);  
        return dist;
    }
    
    // converts given HSV color to RGB color
    public static Vec3f toRGB(Vec3f hsvColor)
    {
        // saturated colors
        if (hsvColor.getY() != 0.0f)      
        {
            float hueFactor = hsvColor.getX() / HUE_SECTOR;
            int hueSector = (int)hueFactor;
            float x = hueFactor - hueSector;
            float S = hsvColor.getY()/SAT_RANGE;
            float V = hsvColor.getZ()/VAL_RANGE;
            float v1 = V * ( 1.0f - S );
            float v2 = V * ( 1.0f - S*x);
            float v3 = V * ( 1.0f - S*(1.0f-x));
            float r, g, b;

            switch (hueSector)
            {
                // red dominance
                case 5:
                    r = V;
                    g = v1;
                    b = v2;
                    break;
                case 0:
                    r = V;
                    g = v3; 
                    b = v1; 
                    break;
                // green dominance
                case 1:
                    r = v2;
                    g = V;
                    b = v1; 
                    break;
                case 2:
                    r = v1;
                    g = V;
                    b = v3;
                    break;
                // blue dominance
                case 3:
                    r = v1;
                    g = v2;
                    b = V;
                    break;
                case 4:
                    r = v3;
                    g = v1;
                    b = V;
                    break;
                default:
                    r = g = b = 0.0f;
            }
            
            return new Vec3f(r * 255, g * 255, b * 255);
        }
        // pure grey colors
        else
        {
            float value = hsvColor.getZ();
            return new Vec3f(value, value, value);
        }
    }
}

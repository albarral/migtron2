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
        float dif_hue = Angle.cyclicDifference(color1.getX() - color2.getX()); // cyclic hue correction
        dif_hue = Kgray * Kdark * dif_hue / disc.getX();
        float dif_sat = Kdark * (color1.getY() - color2.getY()) / disc.getY();
        float dif_val = (color1.getZ() - color2.getZ()) / disc.getZ();

        // mahalanobis distance (with null cross-covariances)
        float dist = (float)Math.sqrt(dif_hue*dif_hue + dif_sat*dif_sat + dif_val*dif_val);  
        return dist;
    }
}

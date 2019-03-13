/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math.color;

import migtron.tron.math.Vec3f;
import migtron.tron.math.Vec3s;

/**
* Utility class to perform color similarity computations using local RGB color and global hsv essence.
* @author albarral
 */

public class ColorSimilarity
{    
    private float SAME_RGB_LOCAL;       // required RGB similarity vs local color
    private float SAME_HSV_GLOBAL;     // required HSV similarity vs global color     public static final float HUE_SECTOR = 60.0f;
                
    public ColorSimilarity(float sameLocalRGB, float sameGlobalHSV)
    {
        this.SAME_RGB_LOCAL = sameLocalRGB;
        this.SAME_HSV_GLOBAL = sameGlobalHSV;
    }

    public ColorSimilarity()
    {
        this(1.0f, 1.0f);
    }
    
    public float getRGBSimilarity() {return SAME_RGB_LOCAL;};
    public float getHSVSimilarity() {return SAME_HSV_GLOBAL;};
    
    public boolean checkSameColor(Vec3f rgb1, Vec3s rgb2, HSVEssence hsvEssence, Vec3f hsv2)
    {
        return ((rgb1.getEuclideanDistance(rgb2) < SAME_RGB_LOCAL) && 
                hsvEssence.getDistance(hsv2) < SAME_HSV_GLOBAL);
    }
}

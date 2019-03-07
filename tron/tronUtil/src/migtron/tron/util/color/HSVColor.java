/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.color;

import migtron.tron.util.math.Vec3f;

/**
* Utility class to implement HSV color comparison and conversion to other color spaces.
* @author albarral
 */

public class HSVColor
{
    private int sameDist;                      // RGB distance at which two colors are considered the same
    private int similarDist;                    // RGB distance at which two colors are considered similar
    private int sameDistSquare;                // square of sameDist
    private int similarDistSquare;              // square of similarDist
    public static final float RED_INTENSITY = 0.2989f;
    public static final float GREEN_INTENSITY = 0.5870f;
    public static final float BLUE_INTENSITY = 0.1140f;
    
    public HSVColor()
    {
        // we use 20 as the default RGB color tolerance
        this(20, 20);
    }
    
    public HSVColor(int sameDist, int similarDist)
    {
        this.sameDist = sameDist;
        this.similarDist = similarDist;
        updateSquares();
    }

    public int getSameDist() {return sameDist;};
    public int getSimilarDist() {return similarDist;};
    public int getSameDistSqr() {return sameDistSquare;};
    public int getSimilarDistSqr() {return similarDistSquare;};
    
    public void setSameDist(int value)
    {
        sameDist = value;
        updateSquares();
    }

    public void setSimilarDist(int value)
    {
        similarDist = value;
        updateSquares();
    }

    private void updateSquares()
    {
        sameDistSquare = sameDist*sameDist;
        similarDistSquare = similarDist*similarDist;        
    }
    
    // merge two RGB colors with specified weight, the resulting color is returned
    public static Vec3f mergeValues (Vec3f color1, Vec3f color2, int q1, int q2)
    {
        if (q1 != 0 && q2 != 0)
        {
            float alpha = (float)q2/(q1+q2);
            float r = color1.data[0] + alpha * (color2.data[0] - color1.data[0]);
            float g = color1.data[1] + alpha * (color2.data[1] - color1.data[1]);
            float b = color1.data[2] + alpha * (color2.data[2] - color1.data[2]);
            return new Vec3f(r, g, b);
        }
        else if (q1 == 0)
            return new Vec3f(color2);
        else if (q2 == 0)
            return new Vec3f(color1);            
        else
            return new Vec3f();
    }

    // get the grayscale intensity of given RGB color.
    public static float getIntensity(Vec3f rgbColor)
    {	            
        return (RED_INTENSITY * rgbColor.getX() + GREEN_INTENSITY * rgbColor.getY() + BLUE_INTENSITY * rgbColor.getZ());
    }
    
    // converts given RGB color to HSV color
    public static Vec3f toHSV(Vec3f rgbColor)
    {
        float r = rgbColor.getX();
        float g = rgbColor.getY();
        float b = rgbColor.getZ();

        float min = Math.min(r, g);
        min = Math.min(min, b);
        float max = Math.max(r, g);
        max = Math.max(max, b);

        float hue, sat;
        float value = max;	

        if (max == 0)
        {
            hue = 0;
            sat = 0;
        }
        else
        {
            float delta = max - min;
            sat = 255 * delta / max;  

            if (delta == 0)
            {
                hue  = 0;
            }
            else
            {
                if (max == r) 
                    hue = 60 * (g - b) / delta;	// between yellow & magenta
                else if (max == g) 
                    hue = 60 * (2 + (b - r) / delta);	// between cyan & yellow
                else 
                    hue = 60 * (4 + (r - g) / delta);	// between magenta & cyan
                
                if( hue < 0)                
                    hue += 360;
            }		
        }		
        
        return new Vec3f(hue, sat, value);
    }    
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import migtron.tron.math.Vec3f;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
* Extended SampleGrid used for color samplings. 
* It uses a matrix to store the sampled color of each node.
* @author albarral
 */

public class ColorGrid extends SampleGrid
{
    protected Mat matColor;   // RGB color matrix (float precision)

    public ColorGrid(int w, int h, float reductionFactor)
    {
        super(w, h, reductionFactor);
        // create grid sized matrix to store the node colors
        matColor = Mat.zeros(rows, cols, CvType.CV_32FC3);    
    }    
        
    public Mat getColorMatrix() {return matColor;}
    
    // set color of the focused node
    public void setNodeColor(Vec3f color)
    {
        matColor.put(focusNode.getRow(), focusNode.getCol(), color.data);        
    }

    // get the color of the focused node
    public Vec3f getNodeColor()
    {
        Vec3f color = new Vec3f();
        matColor.get(focusNode.getRow(), focusNode.getCol(), color.data);        
        return color;
    }    
    
    // clear the color grid
    public void clear()
    {
        super.clearSamples();
        if (!matColor.empty())            
        {
           matColor.setTo(new Scalar(0.0));
        }
    }
}
							 
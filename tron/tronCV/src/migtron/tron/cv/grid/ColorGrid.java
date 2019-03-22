/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import migtron.tron.cv.AverageCV;
import migtron.tron.math.Average3f;
import migtron.tron.math.Vec3f;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point3;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
* Extended SampleGrid used for color samplings. 
* It uses a matrix to store the color of each node.
* A node's color is the average of all the color samples that fall in that node.
* @author albarral
 */

public class ColorGrid extends SampleGrid
{
    protected Mat matColor;   // RGB color matrix (float precision)

    public ColorGrid(int repW, int repH, float reductionFactor)
    {
        super(repW, repH, reductionFactor);
        // create grid sized matrix to store the node colors
        matColor = Mat.zeros(h, w, CvType.CV_32FC3);    
    }    
        
    public Mat getColorMatrix() {return matColor;}
    
    // get the color of the focused node
    public Vec3f getNodeColor()
    {
        Vec3f color = new Vec3f();
        matColor.get(focus.y, focus.x, color.data);        
        return color;
    }    
    
    // set color of the focused node (node color is the average of all its samples)
    public void updateNodeColor(Vec3f color)
    {
        // set the node's average color with the new sample
        Average3f avgColor = new Average3f(getNodeColor(), getNodeSamples());
        avgColor.updateWithSample(color);
        matColor.put(focus.y, focus.x, avgColor.data);        
        // and set the number of samples in the node
        addNodeSample();
    }

    // get the grid's local color, the average color of the node's neighbourhood
    public Vec3f getLocalColor()
    {
        Rect windowNeighbourhood = getWindowCV();                
        MatOfPoint3f colors = new MatOfPoint3f(matColor.submat(windowNeighbourhood));
        MatOfInt samples = new MatOfInt(matSamples.submat(windowNeighbourhood));
        
        Point3 average = AverageCV.compute3DWeightedAverage(colors.toArray(), samples.toArray());
        Vec3f color = new Vec3f((float)average.x, (float)average.y, (float)average.z);
        matColor.put(focus.y, focus.x, color.data);        
        return color;
    }    
    
    // clear the color grid
    @Override
    public void clear()
    {
        super.clear();
        if (!matColor.empty())            
        {
           matColor.setTo(new Scalar(0.0));
        }
    }
}
							 
/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
* Utility class to handle sampling grids. 
* It stores the number of samples used for each node in the grid.
* @author albarral
 */

public class SampledGrid extends Grid
{
    private Mat matSamples;   // samples grid

    public SampledGrid(int w, int h, float samplingFactor)
    {
        super(w, h, samplingFactor);
        // create grid sized matrix to store the node samples
        matSamples = Mat.zeros(rows, cols, CvType.CV_16UC1);    
    }    
        
    public void setNodeSamples(int samples)
    {
        short[] data = {(short)samples};
        matSamples.put(focusNode.getRow(), focusNode.getCol(), data);        
    }

    public short getNodeSamples()
    {
        short[] data = new short[1];
        matSamples.get(focusNode.getRow(), focusNode.getCol(), data);        
        return data[0];
    }
    
    public void updateNodeSamples(int samples)
    {
        short prevSamples = getNodeSamples();
        setNodeSamples(prevSamples + samples);
    }
    
    public void clearSamples()
    {
        if (!matSamples.empty())            
           matSamples.setTo(new Scalar(0.0));
    }
}
							 
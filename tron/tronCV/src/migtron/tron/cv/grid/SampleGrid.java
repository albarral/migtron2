/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
* Extended Grid used for matrix samplings. 
* It holds info about the number of samples used for each grid node (in a grid sized matrix).
* @author albarral
 */

public class SampleGrid extends Grid
{
    protected Mat matSamples;   // samples matrix (short precision)

    public SampleGrid(int w, int h, float reductionFactor)
    {
        super(w, h, reductionFactor);
        // create grid sized matrix to store the node samples
        matSamples = Mat.zeros(rows, cols, CvType.CV_16UC1);    
    }    
        
    public Mat getSamplesMatrix() {return matSamples;}
    
    // set the number of samples of the focused node
    public void setNodeSamples(int samples)
    {
        short[] data = {(short)samples};
        matSamples.put(focusNode.getRow(), focusNode.getCol(), data);        
    }

    // get the number of samples of the focused node
    public short getNodeSamples()
    {
        short[] data = new short[1];
        matSamples.get(focusNode.getRow(), focusNode.getCol(), data);        
        return data[0];
    }
    
    // add a number of samples to the focused node
    public void addNodeSamples(int samples)
    {
        short prevSamples = getNodeSamples();
        setNodeSamples(prevSamples + samples);
    }
    
    // clear the samples of the whole grid
    public void clearSamples()
    {
        if (!matSamples.empty())            
           matSamples.setTo(new Scalar(0.0));
    }
}
							 
/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Rectangle;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
* Extended Grid used for matrix samplings. 
* It has a samples matrix to store the number of samples represented by each node. 
* It also has a sampled window to track the sampled area of the grid.

* @author albarral
 */

public class SampleGrid extends Grid
{
    protected Mat matSamples;   // samples matrix (short precision)
    protected Rectangle sampledWindow;   // sampled window (in grid units)

    public SampleGrid(int repW, int repH, float reductionFactor)
    {
        super(repW, repH, reductionFactor);
        // create samples matrix
        matSamples = Mat.zeros(h, w, CvType.CV_16UC1);    
        // create sampled window (negative values for non-existant window)
        sampledWindow = new Rectangle(0, 0, -1, -1);    
    }    
        
    public Mat getSamplesMatrix() {return matSamples;}
    public Rectangle getSampledWindow() {return sampledWindow;}
    
    // get the number of samples of the focused node
    public short getNodeSamples()
    {
        short[] data = new short[1];
        matSamples.get(focus.y, focus.x, data);        
        return data[0];
    }
    
    // add a new sample to the focused node
    public void addNodeSample()
    {
        short prevSamples = getNodeSamples();
        setNodeSamples(prevSamples + 1);
        // increase the sampled window
        sampledWindow.add(focus);
    }
    
    // set the number of samples of the focused node
    private void setNodeSamples(int samples)
    {
        short[] data = {(short)samples};
        matSamples.put(focus.y, focus.x, data);                
    }
    
    // clear sample grid 
    public void clear()
    {
        if (!matSamples.empty())          
        {
            matSamples.setTo(new Scalar(0.0));
            sampledWindow = new Rectangle(0, 0, -1, -1);    // negative values for non-existant window
        }
    }
}
							 
/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Point;
import java.awt.Rectangle;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
* Extended Grid used for matrix samplings. 
* It uses a samples matrix (of grid size) to store the number of samples in each node. 
* It also uses a sampled window that reflects the part of the grid that has been sampled.
* @author albarral
 */

public class SampleGrid extends Grid
{
    protected Mat matSamples;   // samples matrix (short precision)
    protected Rectangle sampledWindow;   // sampled window (in grid units)

    public SampleGrid(int w, int h, float reductionFactor)
    {
        super(w, h, reductionFactor);
        // create grid sized matrix to store the node samples
        matSamples = Mat.zeros(rows, cols, CvType.CV_16UC1);    
        // create empty sampled window (negative values for non-existant window)
        sampledWindow = new Rectangle(0, 0, -1, -1);    
    }    
        
    public Mat getSamplesMatrix() {return matSamples;}
    public Rectangle getSampledWindow() {return sampledWindow;}
    
    // get the number of samples of the focused node
    public short getNodeSamples()
    {
        short[] data = new short[1];
        matSamples.get(focusNode.getRow(), focusNode.getCol(), data);        
        return data[0];
    }
    
    // add a new sample to the focused node
    public void addNodeSample()
    {
        short prevSamples = getNodeSamples();
        setNodeSamples(prevSamples + 1);
        // increase the sampled window
        sampledWindow.add(focusNode.getCol(), focusNode.getRow());
    }
    
    // set the number of samples of the focused node
    private void setNodeSamples(int samples)
    {
        short[] data = {(short)samples};
        matSamples.put(focusNode.getRow(), focusNode.getCol(), data);                
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
							 
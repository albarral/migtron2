/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Point;
import migtron.tron.cv.Window;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
* Extended Grid used for matrix samplings. 
* It has a samples matrix to store the number of samples represented by each node. 
* It also has a sampled window to track the sampled area of the grid.

* @author albarral
 */

public class SampleGrid extends Grid implements Cloneable
{
    protected Mat matSamples;   // samples matrix (short precision)
    protected Rect sampledWindow;   // sampled window (in grid units)
    protected short focusSamples;     // samples in the focused node

    public SampleGrid(int repW, int repH, float reductionFactor)
    {
        super(repW, repH, reductionFactor);
        // create samples matrix
        matSamples = Mat.zeros(h, w, CvType.CV_16UC1);    
        // create sampled window (negative values for non-existant window)
        sampledWindow = new Rect();    
        focusSamples = 0;
    }    

    @Override
    public Object clone()
    {
        SampleGrid cloned = (SampleGrid)super.clone();
        cloned.matSamples = matSamples.clone();
        cloned.sampledWindow = sampledWindow.clone();
        return cloned;
    }
    
    public Mat getSamplesMatrix() {return matSamples;}
    /**
     * Gets the sampled window 
     * @return the sampledWindow 
     */
    public Rect getSampledWindow() {return sampledWindow;}
    // get the number of samples of the focused node
    public short getFocusSamples() {return focusSamples;}
    
    // set grid focus to a represented matrix position 
    // it internally gets the number of samples in the focused node
    // returns true if focus inside limits, false otherwise
    @Override
    public boolean focus(int x, int y)
    {   
        boolean bok = super.focus(x, y);
        
        if (bok)
        {
            short[] data = new short[1];
            matSamples.get(focus.y, focus.x, data);        
            focusSamples = data[0];            
        }
        return bok;
    }

    // set grid focus to a represented matrix position 
    // it internally gets the number of samples in the focused node
    // returns true if focus inside limits, false otherwise
    @Override
    public boolean focus(Point point)
    {
        return focus(point.x, point.y);
    }
    
    // add a new sample to the focused node
    // it internally updates the sampled window
    public void addSample()
    {
        short[] data = {++focusSamples};
        matSamples.put(focus.y, focus.x, data);                
        sampledWindow = Window.addPoint(sampledWindow, focus);
    }
    
    /**
     * Merge this sample grid with another one (of the same size). 
     * It adds both sampled windows and both samples matrices. Only done if both grids have the same size.
     * @return true if merge done, false otherwise
     */
    public boolean merge(SampleGrid sampleGrid)
    {
        // skip if grids have different sizes
        if (!matSamples.size().equals(sampleGrid.matSamples.size()))
            return false;

        // compute union of sampled windows
        sampledWindow = Window.getUnion(sampledWindow, sampleGrid.sampledWindow);

        // roi both samples matrices
        Mat matSamples1 = matSamples.submat(sampledWindow);
        Mat matSamples2 = sampleGrid.matSamples.submat(sampledWindow);

        // add both samples matrices (leaving result in this grid)
        Core.add(matSamples1, matSamples2, matSamples1);
        return true;
    }
    
    // clear sample grid 
    public void clear()
    {
        if (!matSamples.empty())          
        {
            matSamples.setTo(new Scalar(0.0));
            sampledWindow = new Rect(); 
            focusSamples = 0;
        }
    }

    @Override
    public String toString()
    {
        String desc = "SampleGrid [ matSamples:\n" + matSamples.dump() + "\nsampled window = " + sampledWindow.toString() + "\n" + super.toString() + "\n]";
        return desc;
    }
}
							 
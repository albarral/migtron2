/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Class to represent an average with float precision.
 * @author albarral
 */
public class Average
{
    private float value;
    private int numSamples;
    
    public Average(float val, int samples)
    {
        value = val;
        numSamples = samples;
    }
            
    public Average(float val)
    {
        this(val, 1);
    }

    public float getValue() {return value;};
    public int getNumSamples() {return numSamples;};
    
    // update average with new sampled value (and new samples number)
    public void updateWithSample(float newValue, int newSamples)
    {
        numSamples += newSamples;
        value += (newValue - value) / numSamples;
    }

    // update average with new sampled value
    public void updateWithSample(float newValue)
    {
        updateWithSample(newValue, 1);
    }
}

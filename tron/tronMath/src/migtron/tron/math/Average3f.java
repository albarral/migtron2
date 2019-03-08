/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Class to represent a 3D average with float precision.
 * @author albarral
 */
public class Average3f extends Vec3f
{
    private int numSamples;
    
    public Average3f(Vec3f vector, int numSamples)
    {
        super(vector.data[0], vector.data[1], vector.data[2]);        
        this.numSamples = numSamples;
    }

    public Average3f(Vec3f vector)
    {
        this(vector, 1);
    }
    
    public int getNumSamples() {return numSamples;};
    
    // update average with new sampled value (and new samples number)
    public void updateWithSample(Vec3f newValue, int newSamples)
    {
        numSamples += newSamples;
        double alpha = 1.0 / numSamples;        
        for (int i=0; i<SIZE; i++)
            data[i] += alpha * (newValue.data[i] - data[i]);
    }

    // update average with new sampled value
    public void updateWithSample(Vec3f newValue)
    {
        updateWithSample(newValue, 1);
    }
}

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
    
    // update average with new sampled value representing a number of samples
    public void updateWithSample(Vec3f newValue, int newSamples)
    {
        numSamples += newSamples;
        double alpha = 1.0 / numSamples;        
        for (int i=0; i<SIZE; i++)
            data[i] += alpha * (newValue.data[i] - data[i]);
    }

    // update average with new sampled value representing a number of samples
    public void updateWithSample(Vec3i newValue, int newSamples)
    {
        numSamples += newSamples;
        double alpha = 1.0 / numSamples;        
        for (int i=0; i<SIZE; i++)
            data[i] += alpha * (newValue.data[i] - data[i]);
    }

    // update average with new sampled value representing one sample
    public void updateWithSample(Vec3f newValue)
    {
        updateWithSample(newValue, 1);
    }

    // update average with new sampled value representing one sample
    public void updateWithSample(Vec3i newValue)
    {
        updateWithSample(newValue, 1);
    }
    
   // calculates the average of 2 vectors
    public static Vec3f computeAverage(Vec3f vec1, Vec3f vec2)
    {
        Vec3f avg = new Vec3f();
        for (int i=0; i<SIZE; i++)
            avg.data[i] = (vec1.data[i] + vec2.data[i]) * 0.5f;

        return avg;
    }
    
   // calculates the average of 2 vectors
    public static Vec3f computeAverage(Vec3f vec1, int numSamples, Vec3f vec2)
    {
        Vec3f avg = (Vec3f)vec1.clone();
        double alpha = 1.0 / (numSamples + 1);        
        for (int i=0; i<SIZE; i++)
            avg.data[i] += alpha * (vec2.data[i] - vec1.data[i]);

        return avg;
    }
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;


/**
 * Utility class for statistical analysis of samples.
 * @author albarral
 */
public class Sampling
{
    // compute the average of an array of floats
    public static double computeAverage(float[] samples)
    {
        // safety check
        if (samples.length == 0)
            return 0f;
        
        // compute sum of elements
        double sum = 0f;
        for (int i=0; i<samples.length; i++)
            sum += samples[i];
        
        return sum/samples.length;
    }

    // compute the average of an array of integers
    public static double computeAverage(int[] samples)
    {
        // safety check
        if (samples.length == 0)
            return 0f;
        
        // compute sum of elements
        long sum = 0;
        for (int i=0; i<samples.length; i++)
            sum += samples[i];
        
        return (double)sum/samples.length;
    }

    // compute the variance of an array of floats
    public static double computeVariance(float[] samples)
    {
        // safety check
        if (samples.length == 0)
            return 0f;
        
        // compute sum of element squares
        double sumSqr = 0f;
        for (int i=0; i<samples.length; i++)
            sumSqr += samples[i]*samples[i];
        
        double average = computeAverage(samples);
        
        return sumSqr/samples.length - average*average;
    }

    // compute the variance of an array of integers
    public static double computeVariance(int[] samples)
    {
        // safety check
        if (samples.length == 0)
            return 0f;
        
        // compute sum of element squares
        long sumSqr = 0;
        for (int i=0; i<samples.length; i++)
            sumSqr += (long)samples[i]*(long)samples[i];
        
        double average = computeAverage(samples);
        
        return (double)sumSqr/samples.length - average*average;
    }
}

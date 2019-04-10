/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author albarral
 */
public class SamplingTest 
{
    private int[] array;
    
    public SamplingTest() {
        array = new int[101];
    }
        
    @Before
    public void setUp() 
    {
        int value = 0;
        for (int i=0; i<array.length; i++)
            array[i] = value++;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of computeAverage method, of class Sampling.
     */
    @Test
    public void testComputeAverage() 
    {
        System.out.println("computeAverage");
        
        double avg = Sampling.computeAverage(array);
        double expResult = 50.0;

        System.out.println("array = " + Arrays.toString(array));
        System.out.println("average = " + avg);
        
        assertEquals(expResult, avg, 0.1);
    }

    /**
     * Test of computeVariance method, of class Sampling.
     */
    @Test
    public void testComputeVariance() {
        System.out.println("computeVariance");

        double variance = Sampling.computeVariance(array);
        double expResult = 850.0;

        System.out.println("array = " + Arrays.toString(array));
        System.out.println("variance = " + variance);
        
        assertEquals(expResult, variance, 0.1);
    }
    
}

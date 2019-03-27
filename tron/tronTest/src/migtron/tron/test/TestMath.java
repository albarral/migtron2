/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.test;

import java.awt.geom.Point2D.Float;
import java.awt.Point;
import java.util.Arrays;

import migtron.tron.math.Sampling;

/**
* Test class for migron.tron.math package.
* @author albarral
 */

public class TestMath
{
    String modName;
    
    public TestMath()
    {
        modName = "TestMath";
    }

    public void makeTest()
    {
        System.out.println(modName  + ": test start");
        testSampling();
        System.out.println(modName  + ": test end");
    }

    private void testSampling()
    {
        System.out.println(modName  + ": testSampling()");

        // build array
        int[] array = new int[201];
        int value = 1;
        for (int i=0; i<array.length; i++)
            array[i] = value++;
        
        double avg = Sampling.computeAverage(array);
        double variance = Sampling.computeVariance(array);

        System.out.println("array = " + Arrays.toString(array));
        System.out.println("average = " + avg);
        System.out.println("variance = " + variance);
    }
}

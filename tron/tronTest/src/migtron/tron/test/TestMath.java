/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.test;

import java.awt.geom.Point2D.Float;
import java.awt.Point;
import java.util.Arrays;

import migtron.tron.math.Coordinates;       
import migtron.tron.math.Ellipse;
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
        //testCoordinates();
        //testEllipse();
        testSampling();
        System.out.println(modName  + ": test end");
    }

    private void testCoordinates()
    {
        System.out.println(modName  + ": testCoordinates()");

        Point pos = new Point(50, 50);                
        Point polar = Coordinates.computePolarPoint(pos);
        Point posBack = Coordinates.computeCartesianPoint(polar);

        System.out.println("pos = " + pos.toString());
        System.out.println("polar = " + polar.toString());
        System.out.println("cartesian = " + posBack.toString());
        
        Float pos2 = new Float(50.0f, 50.0f);                
        Float polar2 = Coordinates.computePolar(pos2);
        Float posBack2 = Coordinates.computeCartesian(polar2);
        
        System.out.println("pos2 = " + pos2.toString());
        System.out.println("polar2 = " + polar2.toString());
        System.out.println("cartesian2 = " + posBack2.toString());
    }
    
    private void testEllipse()
    {
        System.out.println(modName  + ": testEllipse()");

        Ellipse ellipse = new Ellipse();
        
        System.out.println(ellipse.toString());
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

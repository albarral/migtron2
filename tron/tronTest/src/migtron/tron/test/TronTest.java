/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.test;

import migtron.tron.cv.NativeOpenCV;

/**
 *
 * @author albarral
 */
public class TronTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        NativeOpenCV.load();

        TestData testData = new TestData();
        //testData.makeTest();

        TestMasks2 testMasks2 = new TestMasks2();
        testMasks2.makeTest();
        
        TestMath testMath = new TestMath();
        //testMath.makeTest();

        TestVision testVision = new TestVision();
        //testVision.makeTest();
    }
    
}

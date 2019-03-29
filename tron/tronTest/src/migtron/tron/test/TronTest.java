/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.test;

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
        TestData testData = new TestData();
        //testData.makeTest();

        TestMasks testMasks = new TestMasks();
        testMasks.makeTest();

        TestMath testMath = new TestMath();
        //testMath.makeTest();

        TestVision testVision = new TestVision();
        //testVision.makeTest();
    }
    
}

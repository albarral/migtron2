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
        TestCV testCV = new TestCV();
        //testCV.makeTest();

        TestData testData = new TestData();
        //testData.makeTest();

        TestUtilDisplay testUtilDisplay = new TestUtilDisplay();
        //testUtilDisplay.makeTest();                

        TestMath testMath = new TestMath();
        //testMath.makeTest();

        TestVision testVision = new TestVision();
        testVision.makeTest();
    }
    
}

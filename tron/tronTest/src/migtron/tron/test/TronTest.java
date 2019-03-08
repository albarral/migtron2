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
        testCV.makeTest();

        TestUtilDisplay testUtilDisplay = new TestUtilDisplay();
        //testUtilDisplay.makeTest();                

        TestMath testUtilMath = new TestMath();
        //testUtilMath.makeTest();
    }
    
}

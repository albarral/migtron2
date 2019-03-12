/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.test;

import migtron.tron.data.Blob;


/**
* Test class for migron.tron.data package.
* @author albarral
 */

public class TestData
{
    String modName;
    
    public TestData()
    {
        modName = "TestData";
    }

    public void makeTest()
    {
        System.out.println(modName  + ": test start");
        testBlob();
        System.out.println(modName  + ": test end");
    }

    private void testBlob()
    {
        System.out.println(modName  + ".testBlob() ...");

        Blob blob = new Blob();        
        System.out.println(blob.toString());
    }

}

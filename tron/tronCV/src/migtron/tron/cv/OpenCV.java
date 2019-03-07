/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import org.opencv.core.Core;

/**
 * Utility class to load openCV native library for use with java
 * @author albarral
 */
public class OpenCV 
{
    static boolean loaded = false;      // flag indicating when native library is loaded
    
    // activate OpenCV by loading its native library
    // does nothing if already activated
    public static void activate()
    {
        if (!loaded)
        {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
            loaded = true;
            System.out.println("OpenCV native library loaded");
        }
    }

}

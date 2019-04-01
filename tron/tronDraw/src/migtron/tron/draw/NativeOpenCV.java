/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import org.opencv.core.Core;

/**
 * Utility class to load openCV native library for use with java
 * @author albarral
 */
public class NativeOpenCV 
{
    static boolean loaded = false;      // flag indicating when native library is loaded
    
    // load NativeOpenCV by loading its native library
    // does nothing if already activated
    public static void load()
    {
        if (!loaded)
        {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
            loaded = true;
            System.out.println("OpenCV native library loaded");
        }
    }

}

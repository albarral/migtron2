/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.box;

/**
 * Utility class to manage operating system calls
 * @author albarral
 */
public class Environment 
{
    // obtains user's home path
    public static String getHomePath()
    {
        return System.getenv("HOME");
    }    
}

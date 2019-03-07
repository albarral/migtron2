/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// Class to implement a implements a Brooks sensor for transmission of boolean info.
// Thread safety implemented.
* @author albarral
 */
public class SensorB
{
    private boolean value;      // sensor value

    public SensorB()
    {
        value = false;
    }
                            
    // set the sensor info
    public synchronized void set(boolean val) {value = val;}
 
   // get the sensor info
    public synchronized boolean get() {return value;}
    
}

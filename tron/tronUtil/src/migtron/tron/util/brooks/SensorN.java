/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// Class to implement a implements a Brooks sensor for transmission of integer info.
// Thread safety implemented.
* @author albarral
 */
public class SensorN
{
    private int value;      // sensor value

    public SensorN()
    {
        value = 0;
    }
                            
    // set the sensor info
    public synchronized void set(int val) {value = val;}
 
   // get the sensor info
    public synchronized int get() {return value;}    
}

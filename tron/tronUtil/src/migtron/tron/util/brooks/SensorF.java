/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// Class to implement a implements a Brooks sensor for transmission of float info.
// Thread safety implemented.
* @author albarral
 */
public class SensorF
{
    private float value;      // sensor value

    public SensorF()
    {
        value = 0.0f;
    }
                            
    // set the sensor info
    public synchronized void set(float val) {value = val;}
 
   // get the sensor info
    public synchronized float get() {return value;}    
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// Class to implement a implements a Brooks sensor for transmission of generic info.
// Thread safety implemented.
* @author albarral
 */
public class SensorT<T>
{
    private T value;      // sensor value

    public SensorT()
    {
        value = null;
    }
                            
    // set the sensor info
    public synchronized void set(T val) {value = val;}
 
   // get the sensor info
    public synchronized T get() {return value;}    
}

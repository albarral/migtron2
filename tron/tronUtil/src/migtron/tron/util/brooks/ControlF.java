/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// This class implements a Brooks control connector for transmission of float commands.
// Thread safety implemented.
* @author albarral
 */
public class ControlF extends Control
{
    private float value;    // command value

    public ControlF()
    {
        super();
        value = 0.0f;
    }

    public synchronized float getValue() {return value;}
                        
    // request a control command (float value) with given priority, returns true if the request is accepted
    public synchronized boolean requestP(float value, int priority)                
    {
        // if this request wins, the control value is updated
        if (super.requestP(priority))
        {
            this.value = value;    
            return true;
        }
        else 
            return false;          
    }
                        
    public synchronized boolean request(float value)                
    {
        return requestP(value, 0);
    }
}

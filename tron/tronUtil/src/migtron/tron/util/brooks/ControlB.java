/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// This class implements a Brooks control connector for transmission of boolean commands.
// Thread safety implemented.
* @author albarral
 */
public class ControlB extends Control
{
    private boolean value;    // command value

    public ControlB()
    {
        super();
        value = false;
    }

    public synchronized boolean getValue() {return value;}
                        
    // request a control command (boolean value) with given priority, returns true if the request is accepted
    public synchronized boolean requestP(boolean value, int priority)                
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
        
    public synchronized boolean request(boolean value)                
    {
        return requestP(value, 0);
    }
}

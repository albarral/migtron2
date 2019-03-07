/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// This class implements a Brooks control connector for transmission of integer commands.
// Thread safety implemented.
* @author albarral
 */
public class ControlN extends Control
{
    private int value;    // command value

    public ControlN()
    {
        super();
        value = 0;
    }

    public synchronized int getValue() {return value;}
                        
    // request a control command (integer value) with given priority, returns true if the request is accepted
    public synchronized boolean requestP(int value, int priority)                
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
        
    public synchronized boolean request(int value)                
    {
        return requestP(value, 0);
    }
}

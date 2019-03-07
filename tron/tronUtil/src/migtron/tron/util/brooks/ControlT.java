/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// This class implements a Brooks control connector for transmission of generic commands.
// Thread safety implemented.
* @author albarral
 */
public class ControlT<T> extends Control
{
    private T value;    // command value

    public ControlT()
    {
        super();
        value = null;
    }

    public synchronized T getValue() {return value;}
                        
    // request a control command (generic type) with given priority, returns true if the request is accepted
    public synchronized boolean requestP(T value, int priority)                
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
                        
    public synchronized boolean request(T value)                
    {
        return requestP(value, 0);
    }
}

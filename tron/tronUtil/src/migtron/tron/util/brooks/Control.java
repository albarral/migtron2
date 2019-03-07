/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// Class to implement a basic control command inside a Brooks control architecture.
// A flag is raised whenever the command is requested, and it is lowered down whenever the command state is checked. 
// It allows for suppression mechanism (Brooks subsumption architecture) by allowing requests with different priorities, always executing the request of highest priority.
// Thread safety implemented.
* @author albarral
 */
public class Control
{
    protected boolean brequested;      // command requested flag        
    protected int priority;                    // active priority (highest of received requests)

    public Control()
    {
        brequested = false;
        priority = 0;        
    }
                        
    public synchronized int getPriority() {return priority;};
    
    // request a control command with given priority, returns true if the request is accepted
    public synchronized boolean requestP(int priority)
    {
        // raise flag if down or if this request has higher priority than previous winner request
        if (!brequested || priority >= this.priority)
        {
            brequested = true;
            this.priority = priority;        
            return true;        
        }
        else 
            return false;                      
    }
    
    // checks if a command request is pending to be executed.
    // After the check, the request is considered not pending anymore.
    public synchronized boolean checkRequested()
    {
        if (brequested)
        {
            brequested = false;
            return true;
        }
        else
            return false;        
    }
}

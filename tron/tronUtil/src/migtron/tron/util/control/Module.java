/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.control;

/**
// Class designed to run a behaviour inside its own thread.
// Methods to start & stop the module:
// - start() -> starts the module.
// - off()  -> asks the module to stop.
// Thread safety implemented.    
* @author albarral
 */
public class Module extends Thread
{
    private boolean on;        // flag indicating module running
    private boolean offRequested;    // flag indicating thread stop was requested
    private float frequency;    // loop execution frequency
    private int period;           // period (milliseconds) between loop executions (inverse of the frequency)
    private Behaviour behaviour;        // implemented behaviour

    public Module(Behaviour behaviour)
    {
        on = false;
        offRequested = false;
        setFrequency(1.0f);  // default frequency        
        this.behaviour = behaviour;
    }
                                
    // checks if module is on
    public boolean isOn() {return on;};
    // checks if off was requested
    private synchronized boolean isOffRequested() {return offRequested;}
    // gets the loop frequency 
    public float getFrequency() {return frequency;}
    // gets the loop period (microsecs)
    public int getPeriod() {return period;}

    // set the desired loop frequency (Hz)
    public void setFrequency(float freq)
    {
        if (freq > 0.0)
        {
            frequency = freq;
            period = (int)(1000f/freq);    // in milliseconds
        }
    }

    // asks the thread to stop
    public synchronized void off() {offRequested = true;}

    // method running inside the thread 
    @Override
    public void run()
    {
        on = true; 
        behaviour.start();
        while (!isOffRequested())
        {
            behaviour.preLoop();
            behaviour.sense();
            // actuate if not inhibited
            if (!behaviour.isInhibited())
                behaviour.actuate();

            try {sleep(period);}
            catch (InterruptedException e) {break;}
        }
        behaviour.end();
        on = false;        
    }        
}

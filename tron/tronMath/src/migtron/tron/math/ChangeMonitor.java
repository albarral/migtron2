/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.math;

/**
 * Utility class to monitor changes in a variable. 
 * A warning is raised when the accumulated changes exceed some threshold values. The amount of change to be detected is configurable.
 * @author albarral
 */
public class ChangeMonitor
{
    private float changeFactor;             // proportional change that will raise a warning
    private float minimumChange;         // minimum detectable change
    private float upperThreshold;           // next upper threshold, raise warning if exceded
    private float lowerThreshold;           // next lower threshold, raise warning if exceded (fallen below)

    public float getChangeFactor() {return changeFactor;};
    public float getMinimumChange() {return minimumChange;};
    public float getUpperThreshold() {return upperThreshold;};
    public float getLowerThreshold() {return lowerThreshold;};    
  
    public ChangeMonitor(float changeFactor, float minChange)
    {
        this.changeFactor = changeFactor;
        this.minimumChange = minChange;
    }
    
    // default monitor constructor (10% change factor, 100 minimum detectable change)
    public ChangeMonitor()
    {
        this(0.1f, 100f);
    }
    
    // set new warning thresholds from new given variable value
    public void reset(float presentValue)
    {    
        // set next warning values
        float change = changeFactor * presentValue;
        // limit the mimum detectable change
        if (change < minimumChange)
            change = minimumChange;
        
        upperThreshold = presentValue + change;
        lowerThreshold = presentValue - change;
        if (lowerThreshold < 0)
            lowerThreshold = 0;
    }
      
    // check if the accumulated change since last reset exceeds any warning threshold
    public boolean check(float presentValue) 
    {
        return (presentValue > upperThreshold || presentValue < lowerThreshold);
    }
}

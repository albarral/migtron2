/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.box;

import java.time.Instant;
import java.time.Duration;

/**
* Utility class for computing time intervals.
* On start() the start time point is set.
* On read() the elapsed time from the start is computed
* @author albarral
 */

public class Click
{
    private Instant t1;
    private long millis;     // last measure in milliseconds
    private long micros;   // last measure in microseconds
    
    public Click()
    {
        t1 = null;
        millis = micros = 0;    
    }

    // returns the elapsed time in milliseconds
    public long getMillis() {return millis;};
    // returns the elapsed time in microseconds
    public long getMicros() {return micros;};

    // stores initial time point 
    public void start()
    {
        t1 = Instant.now();
    }

    // measures the elapsed time since last start 
    void read()
    {
        if (t1 != null)
        {
            Duration duration = Duration.between(t1, Instant.now());
            millis = duration.toMillis();
            micros = duration.toNanos() / 1000;
        }
    }
    
    public void reset()
    {
        t1 = null;
        millis = micros = 0;    
    }
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.brooks;

/**
// This class implements the addition operator for 3 brooks control connectors.
// Thread safety implemented.
* @author albarral
 */
public class ControlFAdder3
{
    private ControlF control1;    // access to 1st control connector
    private ControlF control2;    // access to 2nd control connector
    private ControlF control3;    // access to 3rd control connector
    private boolean benabled;
    private float value;                  // sum of control values

    public ControlFAdder3()
    {          
        control1 = null; 
        control2 = null; 
        control3 = null; 
        benabled = false;
        value = 0.0f;
    }

    public float getValue() {return value;};
    
    public void connect(int num, ControlF control)
    {
        switch (num)
        {
            case 1: 
                control1 = control;
                break;
            case 2: 
                control2 = control;
                break;
            case 3: 
                control3 = control;
                break;
        }

        benabled = (control1 != null) && (control2 != null) && (control3 != null);
    }

    public boolean checkRequested()
    {	
        // check if some control was requested, update the sum in this case
        if (benabled &&             
                (control1.checkRequested() || control2.checkRequested() || control3.checkRequested()))
        {
            value = control1.getValue() + control2.getValue() + control3.getValue();
            return true;
        }
        else
            return false;
    }
}





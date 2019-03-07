/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.control;

import java.util.ArrayList;
import java.util.List;

/**
// Base class for implementing robot behaviours.
// Designed to implement a finite state machines architecture.
* @author albarral
*/
public abstract class Behaviour 
{
    protected String name;   // behaviour name            
    private int state;             // present state
    private int prevState;       // previous state
    private boolean binhibited;        // inhibited flag (when true methods do nothing)
    List<String> listStateNames;    // state names 

    public Behaviour(String name)
    {
        this.name = name;
        prevState = state = -1;
        binhibited = false;
        listStateNames = new ArrayList<>();
    }
      
    // TO IMPLEMENT BY REAL BEHAVIOURS ...        
    // things to do when the behavior starts
    abstract public void start();
    // behavior sense phase
    abstract public void sense();            
    // behavior actuate phase
    abstract public void actuate();            
    // things to do when the behavior ends
    abstract public void end();                       

    // get behavior name
    public String getName() {return name;}
    // get behavior state
    public int getState() {return state;}
    // get behavior previous state
    public int getPrevState() {return prevState;}
    // checks if state has changed in last loop
    public boolean isStateChanged() {return (state != prevState);}
    // get inhibited flag
    public boolean isInhibited() {return binhibited;}
    // get state names
    public List<String> getStateNames() {return listStateNames;}
    // get name of present behavior state
    public String getStateName()
    {
        // get name of given state
        if (state < listStateNames.size())
            return listStateNames.get(state);
        // return null if invalid state
        else
            return null;            
    }

    // set behavior state 
    public void setState(int value) {state = value;}
    // inhibit behavior
    public void inhibit(boolean value) {binhibited = value;}
    // add state name
    public void addStateName(String name)
    {
        listStateNames.add(name);            
    }
        
    // done before each loop iteration
    public void preLoop()
    {
        // store previous state
        prevState = state;        
    }
}

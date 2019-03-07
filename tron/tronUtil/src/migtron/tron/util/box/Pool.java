/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.util.box;

import java.util.Queue;
import java.util.ArrayDeque;

/**
* Utility class to use a pool of available elements that can be fetched and freed.
* It works as a FIFO list, using the oldest of the unused ones.
* On refill() the list is filled with all available elements.
* On fetch() the first available element is fetched (removed from the list).
* On free() the given element is made available again (added back to the list).    
* @author albarral
 */
public class Pool
{
    private int maxSize;                // maximum number of elements
    Queue<Integer> queue;    // sequence of available elements (FIFO queue)
    
    public Pool(int size)
    {          
        queue = new ArrayDeque();
        init(size); 
    }

    public void init(int size)
    {	
        maxSize = size;

        // first, clear list
        queue.clear();
        // then fill list with all elements
        for (int i=1; i<=maxSize; i++) 
            queue.add(i);
    }

    public int fetch()
    {
        // take only if elements available
        if (!queue.isEmpty())
            return queue.remove();
        else
            return -1;
    }

    public void push(int element)
    {
        // push only if size available
        if (queue.size() < maxSize)
            queue.add(element);
    }
}

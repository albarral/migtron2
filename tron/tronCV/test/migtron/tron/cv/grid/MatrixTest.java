/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author albarral
 */
public class MatrixTest {
    
    public MatrixTest() {
    }
        
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setFocus method, of class Matrix.
     */
    @Test
    public void testSetFocus() 
    {
        System.out.println("setFocus");
    
        Matrix matrix = new Matrix(100, 100);
        
        int oks = 0;
        int kos = 0;
        int smalls = 0;
        for (int y=0; y<105; y++)
        {
            if (matrix.setFocus(50, y))
            {
                oks++;
                if (matrix.getWindow().height < 3)
                    smalls++;                

                System.out.println("focus = " + matrix.getFocus() + ", window = " + matrix.getWindow());                    
            }
            else
                kos++;            
        }
        
        int expOks = 100;
        int expSmalls = 2;
        Assert.assertTrue((oks == expOks) && (smalls == expSmalls));
    }
}

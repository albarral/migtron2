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
        
        int w = 100;
        int h = 50;
        Matrix matrix = new Matrix(w, h);
        
        int oks = 0;
        int kos = 0;
        int smalls = 0;
        int x = 50;
        for (int y=0; y<55; y++)
        {
            if (matrix.setFocus(x, y))
            {
                oks++;
                if (matrix.getWindow().height < 3)
                    smalls++;                
            }
            else
                kos++;            

            System.out.println("(x,y) = " + x + "," + y + ", focus = " + matrix.getFocus().toString() + ", window = " + matrix.getWindow());                    
        }
        
        int expOks = h;
        int expSmalls = 2;
        Assert.assertTrue((oks == expOks) && (smalls == expSmalls));
    }
}

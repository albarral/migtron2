/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import org.opencv.core.Rect;

/**
* Class to draw images with rectangular blocks.
* It draws rectangular blocks in n x n rectangular grid configurations.
* @author albarral
 */

public class BlocksDrawer extends Drawer
{
    private int n;  // granularity of the grid configuration
    private int blockWidth;
    private int blockHeight;
    
    public BlocksDrawer(int w, int h, int n)
    {
        super(w, h);
        // safety check
        if (n < w && n < h)
            this.n = n;
        else
            this.n = 1;

        // compute blocks size
        blockWidth = w/n;
        blockHeight = h/n;
    }

    public BlocksDrawer(int w, int h)
    {
        // 2x2 default granularity
        this(w, h, 2);
    }
      
    public int getGranularity() {return n;}
    
    // fill block at specified position
    public boolean fillBlock(int row, int col)
    {
        // safety check
        if (row < n && col < n)
        {
            int x = col * blockWidth;
            int y = row * blockHeight;
            Rect window = new Rect(x, y, blockWidth, blockHeight);
            MathDrawer.drawFilledRectangle(mat, window, color);
            return true;
        }
        else
        {
            System.out.println("BlocksDrawer: fillBlock() failed, wrong cell specified (" + row + "," + col + ")");           
            return false;
        }
    }

    // fill all top blocks
    public void fillTop()
    {
        for (int col=0; col<n; col++)
            fillBlock(0, col);
    }

    // fill all bottom blocks
    public void fillBottom()
    {
        for (int col=0; col<n; col++)
            fillBlock(n-1, col);
    }

    // fill all left blocks
    public void fillLeft()
    {
        for (int row=0; row<n; row++)
            fillBlock(row, 0);
    }

    // fill all right blocks
    public void fillRight()
    {
        for (int row=0; row<n; row++)
            fillBlock(row, n-1);
    }
    
    // fill block in top left corner
    public void fillTopLeft()
    {
        fillBlock(0, 0);
    }

    // fill block in top right corner
    public void fillTopRight()
    {
        fillBlock(0, n-1);
    }

    // fill block in bottom left corner
    public void fillBottomLeft()
    {
        fillBlock(n-1, 0);
    }

    // fill block in bottom right corner
    public void fillBottomRight()
    {
        fillBlock(n-1, n-1);
    }    
}

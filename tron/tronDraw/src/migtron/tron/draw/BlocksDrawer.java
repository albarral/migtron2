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
    private int granularity;  // granularity of the grid configuration
    private int blockWidth;
    private int blockHeight;
    private Rect drawnWindow;   // window enclosig all drawn blocks
    
    public BlocksDrawer(int w, int h, int granularity)
    {
        super(w, h);
        // safety check
        if (granularity < w && granularity < h)
            this.granularity = granularity;
        else
            this.granularity = 1;

        // compute blocks size
        blockWidth = w/granularity;
        blockHeight = h/granularity;
        drawnWindow = null;
    }

    public BlocksDrawer(int w, int h)
    {
        // 2x2 default granularity
        this(w, h, 2);
    }
      
    public int getGranularity() {return granularity;}
    
    public Rect getDrawnWindow() {return drawnWindow;}
    
    // fill block at specified position
    public boolean fillBlock(int row, int col)
    {
        // safety check
        if (row < granularity && col < granularity)
        {
            int x = col * blockWidth;
            int y = row * blockHeight;
            Rect window = new Rect(x, y, blockWidth, blockHeight);
            MathDrawer.drawFilledRectangle(mat, window, color);
            // update drawn window with new block
            updateDrawnWindow(window);
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
        for (int col=0; col<granularity; col++)
            fillBlock(0, col);
    }

    // fill all bottom blocks
    public void fillBottom()
    {
        for (int col=0; col<granularity; col++)
            fillBlock(granularity-1, col);
    }

    // fill all left blocks
    public void fillLeft()
    {
        for (int row=0; row<granularity; row++)
            fillBlock(row, 0);
    }

    // fill all right blocks
    public void fillRight()
    {
        for (int row=0; row<granularity; row++)
            fillBlock(row, granularity-1);
    }
    
    // fill block in top left corner
    public void fillTopLeft()
    {
        fillBlock(0, 0);
    }

    // fill block in top right corner
    public void fillTopRight()
    {
        fillBlock(0, granularity-1);
    }

    // fill block in bottom left corner
    public void fillBottomLeft()
    {
        fillBlock(granularity-1, 0);
    }

    // fill block in bottom right corner
    public void fillBottomRight()
    {
        fillBlock(granularity-1, granularity-1);
    }    
    
    private void updateDrawnWindow(Rect window)
    {
        if (drawnWindow == null)
            drawnWindow = window.clone();
        else
            drawnWindow = Window2.getUnion(drawnWindow, window);
    }

    @Override
    public void clear()
    {
        super.clear();
        drawnWindow = null;
    }
}

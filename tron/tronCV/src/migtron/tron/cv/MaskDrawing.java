/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;


/**
* Utility class for drawing simple masks with rectangular blocks.
* A mask is divided in four equal sized blocks (2 up and 2 down).
* @author albarral
 */

public class MaskDrawing
{
    private Mat mat;        // the mask
    private int w;  // mask width
    private int h;  // mask height
    private int xcenter;
    private int ycenter;
    private int blockWidth;
    private int blockHeight;
    
    public MaskDrawing(int width, int height)
    {
        w = width;
        h = height;
        xcenter = w/2;
        ycenter = h/2;
        blockWidth = w/2;
        blockHeight = h/2;
        mat = Mat.zeros(h, w, CvType.CV_8UC1);
    }

    public MaskDrawing()
    {
        // default size
        this(200, 100);
    }
        
    public Mat getMat() {return mat;};

    public void fillTop()
    {
        fillSector(1);
        fillSector(2);
    }

    public void fillBottom()
    {
        fillSector(3);
        fillSector(4);
    }

    public void fillLeft()
    {
        fillSector(1);
        fillSector(3);
    }

    public void fillRight()
    {
        fillSector(2);
        fillSector(4);
    }
    
    public void fillTopLeft()
    {
        fillSector(1);
    }

    public void fillTopRight()
    {
        fillSector(2);
    }

    public void fillBottomLeft()
    {
        fillSector(3);
    }

    public void fillBottomRight()
    {
        fillSector(4);
    }
    
    public void clear()
    {
        mat.setTo(new Scalar(0));
    }
    
    private boolean fillSector(int sector)
    {
        Rect window = null;
        switch (sector)
        {
            case 1:
                window = new Rect(0, 0, blockWidth, blockHeight);
                break;
            case 2:
                window = new Rect(xcenter, 0, blockWidth, blockHeight);
                break;
            case 3:
                window = new Rect(0, ycenter, blockWidth, blockHeight);
                break;
            case 4:
                window = new Rect(xcenter, ycenter, blockWidth, blockHeight);
                break;               
        }
        if (window != null)
        {
            ImageUtils.drawFilledRectangle(mat, window, ImageUtils.eColor.eCOLOR_WHITE);
            return true;
        }
        else
        {
            System.out.println("MaskDrawing: fillSector() failed, wrong sector specified");           
            return false;
        }
    }
}

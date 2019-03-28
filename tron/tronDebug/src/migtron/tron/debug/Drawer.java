/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.debug;

import java.awt.image.BufferedImage;

import migtron.tron.cv.ImageUtils;
import migtron.tron.math.Ellipse;
import migtron.tron.math.color.Colors;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Class to draw images to be displayed.
 * Implements the iDrawer interface.
 * @author albarral
 */
public class Drawer implements iDrawer
{
    private Mat mat;       // image matrix    
    private boolean additive;     //  additive mode
    private Colors.eColor color; 
    
    public Drawer(int w, int h)
    {
        mat = Mat.zeros(h, w, CvType.CV_8UC1);
        additive = false;
        color = Colors.eColor.eCOLOR_WHITE;
    }        

    public Mat getMat() {return mat;}
    
    // set the drawing color for a display window
    @Override
    public void setColor(Colors.eColor color)
    {
        this.color = color;
    }

    // set the additive mode for a display window (when active, new images are supperposed to previsous ones)
    @Override
    public void setAdditive(boolean mode)
    {
        additive = mode;
    }

    // draw given mask in a display window
    @Override
    public void drawMask(Mat mat)
    {
        this.mat = mat.clone();
    }

    // draw given ellipse in a display window
    @Override
    public void drawEllipse(Ellipse ellipse)
    {
        if (!additive)
            clear();
        
        ImageUtils.drawEllipse(mat, ellipse, color);
    }
        
    // get drawn image
    @Override
    public BufferedImage getImage()
    {
        return ImageUtils.cvMask2Java(mat);
    }

    private void clear()
    {
        mat = Mat.zeros(mat.size(), mat.type());        
    }
}

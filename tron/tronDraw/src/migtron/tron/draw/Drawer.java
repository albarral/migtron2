/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import java.awt.image.BufferedImage;

import migtron.tron.math.Vec3i;
import migtron.tron.math.color.Colors;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 * Base class to draw images.
 * @author albarral
 */
public class Drawer
{
    protected Mat mat;       // image matrix    
    protected Scalar color;   // real Color   
    private Colors.eColor stdColor; // standard color
    
    public Drawer(int w, int h)
    {
        mat = Mat.zeros(h, w, CvType.CV_8UC1);
        setStandardColor(Colors.eColor.eCOLOR_WHITE);
    }        
        
    // get drawn image in cv format
    public Mat getMat()
    {
        return mat;
    }

    // get drawn image in java format
    public BufferedImage getImage()
    {
        return DrawUtils.cvMask2Java(mat);
    }

    // set the base image for drawing
    public void setBase(Mat mat)
    {
        this.mat = mat.clone();
    }
    
    // set the real color for drawing
    public void setColor(Vec3i color)
    {
        this.color = new Scalar(color.getX(), color.getY(), color.getZ());
    }

    // set the standard color for drawing
    public void setStandardColor(Colors.eColor ecolor)
    {
        this.stdColor = ecolor;
        setColor(Colors.getRGB(ecolor));
    }
    
    public void clear()
    {
        mat = Mat.zeros(mat.size(), mat.type());        
    }
}

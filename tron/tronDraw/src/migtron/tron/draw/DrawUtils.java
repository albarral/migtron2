/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.draw;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;

import migtron.tron.math.Ellipse;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;


/**
 * Utility class for drawing
 * @author albarral
 */
public class DrawUtils
{
    // convert opencv mask to java image
    public static BufferedImage cvMask2Java(Mat mat)
    {                
        if (mat.type() == CvType.CV_8UC1)
        {
            try 
            {
                // convert mat to buffer
                MatOfByte mat2 = new MatOfByte();
                Highgui.imencode(".jpg", mat, mat2); 
                byte[] byteArray = mat2.toArray();

                // convert buffer to java image 
                InputStream in = new ByteArrayInputStream(byteArray);
                return ImageIO.read(in);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                return null;
            }            
        }
        else
        {
            System.out.println("DrawUtils: cvMask2Java() failed. Input mat is not a mask");                 
            return null;
        }
    }

    // convert ellipse (tron.math form) to opencv rotated window
    public static RotatedRect ellipse2RotatedRect(Ellipse ellipse)
    {
        // angle sign changed (as image y axis is opposite to world y axis)
        return new RotatedRect(new Point(ellipse.getPosition().x, ellipse.getPosition().y), new Size(2*ellipse.getWidth(), 2*ellipse.getHeight()), -ellipse.getAngle());
    }
}

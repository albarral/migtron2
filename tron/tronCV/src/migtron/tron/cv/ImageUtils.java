/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;

import migtron.tron.util.math.Ellipse;
import migtron.tron.util.math.Coordinates;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;


/**
 * Utility class to convert OpenCV images to java
 * @author albarral
 */
public class ImageUtils
{
    public enum eColor{
        eCOLOR_BLACK, 
        eCOLOR_WHITE,
        eCOLOR_GREY      
    }
    
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
            System.out.println("ImageUtils: cvMask2javaImage() failed. Input mat is not a mask");                 
            return null;
        }
    }

    // draw a line (from origin, with length and angle)
    public static void drawLine(Mat mat, int x, int y, float length, float angle, eColor color)
    {
        // get vector to second point
        Float vector = Coordinates.computeCartesian(length, angle);
        // vector.y sign changed (as image y axis is opposite to world y axis)
        Core.line(mat, new Point(x, y), new Point(x+vector.x, y-vector.y), getColor(color));                        
    }
    
    // draw an ellipse (perimeter only)
    public static void drawEllipse(Mat mat, int x, int y, int w, int h, float angle, eColor color)
    {
        // angle sign changed (as image y axis is opposite to world y axis)
        RotatedRect rotWindow = new RotatedRect(new Point(x, y), new Size(2*w, 2*h), -angle);
        Core.ellipse(mat, rotWindow, getColor(color));                        
    }

    // draw a filled ellipse (whole surface)
    public static void drawFilledEllipse(Mat mat, int x, int y, int w, int h, float angle, eColor color)
    {
        // angle sign changed (as image y axis is opposite to world y axis)
        RotatedRect rotWindow = new RotatedRect(new Point(x, y), new Size(2*w, 2*h), -angle);
        Core.ellipse(mat, rotWindow, getColor(color), Core.FILLED);                        
    }

    // draw an ellipse (perimeter only)
    public static void drawEllipse(Mat mat, Ellipse ellipse, eColor color)
    {
        Core.ellipse(mat, ellipse2RotatedRect(ellipse), getColor(color));                        
    }

    // draw a filled ellipse (whole surface)
    public static void drawFilledEllipse(Mat mat, Ellipse ellipse, eColor color)
    {
        Core.ellipse(mat, ellipse2RotatedRect(ellipse), getColor(color), Core.FILLED);                        
    }
    
    // draw a rectangle (perimeter only)
    public static void drawRectangle(Mat mat, Rect window, eColor color)
    {
        Core.rectangle(mat, window.tl(), window.br(), getColor(color));        
    }

    // draw a filled rectangle (whole surface)
    public static void drawFilledRectangle(Mat mat, Rect window, eColor color)
    {
        Core.rectangle(mat, window.tl(), window.br(), getColor(color), Core.FILLED);        
    }

    // convert tron ellipse to opencv rotated window
    public static RotatedRect ellipse2RotatedRect(Ellipse ellipse)
    {
        // angle sign changed (as image y axis is opposite to world y axis)
        return new RotatedRect(new Point(ellipse.getPosition().x, ellipse.getPosition().y), new Size(2*ellipse.getWidth(), 2*ellipse.getHeight()), -ellipse.getAngle());
    }

    // convert color enum to opencv color
    private static Scalar getColor(eColor ecolor)
    {
        Scalar color; 
        switch (ecolor)
        {
            case eCOLOR_BLACK:
                color = new Scalar(0); 
                break;

            case eCOLOR_WHITE:
                color = new Scalar(255); 
                break;
                
            case eCOLOR_GREY:
                color = new Scalar(128); 
                break;
                
            default: 
                color = new Scalar(0); // default is black
        }
        return color;        
    }
}

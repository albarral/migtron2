/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.debug;

import java.awt.image.BufferedImage;
import migtron.tron.math.Ellipse;

import migtron.tron.math.color.Colors;
import org.opencv.core.Mat;

/**
  * Interface for displaying images in a display.
  * A display is a graphical window composed by various sub-windows, each identified by a unique name.
* @author albarral
 */
public interface iDisplayer
{
    // set the drawing color for a display window
    void setColor(String windowName, Colors.eColor color);

    // set the additive mode for a display window (when active, new images are supperposed to previsous ones)
    void setAdditive(String windowName, boolean mode);

    // show the image in given path in a display window
    void showImage(String windowName, String imagePath);

    // show given image in a display window
    void showImage(String windowName, BufferedImage image);
            
    // show given mask in a display window
    void showMask(String windowName, Mat mat);

    // show given ellipse in a display window
    void showEllipse(String windowName, Ellipse ellipse);               
}

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
  * Interface for drawing images in a display.
* @author albarral
 */
public interface iDrawer
{
    // set the drawing color for a display window
    void setColor(Colors.eColor color);

    // set the additive mode (when active, new drawings are supperposed to previous images)
    void setAdditive(boolean mode);
        
    // draw given mask in a display window
    void drawMask(Mat mat);

    // draw given ellipse in a display window
    void drawEllipse(Ellipse ellipse);
    
    // get drawn image
    BufferedImage getImage();
}

/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Point;
import migtron.tron.cv.AverageCV;
import migtron.tron.cv.Mask;
import migtron.tron.math.Average3f;
import migtron.tron.math.Vec3f;
import migtron.tron.math.Vec3i;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point3;
import org.opencv.core.Scalar;

/**
* Extended SampleGrid used for color samplings. 
* It uses a matrix to store the color of each node.
* A node's color is the average of all the color samples that fall in that node.
* @author albarral
 */

public class ColorGrid extends SampleGrid implements Cloneable
{
    protected Mat matColor;   // RGB color matrix (float precision)
    private Vec3f focusColor;     // RGB color in the focused node
    private final int TYPE = CvType.CV_32FC3;  // float 3 channel matrix

    public ColorGrid(int repW, int repH, float reductionFactor)
    {
        super(repW, repH, reductionFactor);
        // create grid sized matrix to store the node colors
        matColor = Mat.zeros(h, w, TYPE);    
        focusColor = new Vec3f();
    }    

    @Override
    public Object clone()
    {
        ColorGrid cloned = (ColorGrid)super.clone();
        cloned.matColor = matColor.clone();
        cloned.focusColor = (Vec3f)focusColor.clone();
        return cloned;
    }

    public Mat getColorMatrix() {return matColor;}    
    // get the color of the focused node
    public Vec3f getFocusColor() {return focusColor;}
    
    // set grid focus to a represented matrix position 
    // it internally gets the color of the focused node
    // returns true if focus inside limits, false otherwise
    @Override
    public boolean focus(int x, int y)
    {   
        boolean bok = super.focus(x, y);
        
        if (bok)
            matColor.get(focus.y, focus.x, focusColor.data);        

        return bok;
    }

    // set grid focus to a represented matrix position 
    // it internally gets the color of the focused node
    // returns true if focus inside limits, false otherwise
    @Override
    public boolean focus(Point point)
    {
        return focus(point.x, point.y);
    }
    
    // add a new color sample to the focused node
    // the average node color is updated with the given sample
    public void addColorSample(Vec3i color)
    {
        // set the node's average color with the new sample
        Average3f avgColor = new Average3f(focusColor, focusSamples);
        avgColor.updateWithSample(color);
        focusColor = (Vec3f)avgColor;
        matColor.put(focus.y, focus.x, focusColor.data);        
        // and update the node samples number
        addSample();
    }

    // get the grid's local color, the average color of the node's neighbourhood
    public Vec3f getLocalColor()
    {
        MatOfPoint3f colors = new MatOfPoint3f(matColor.submat(focusWindow));
        MatOfInt samples = new MatOfInt(matSamples.submat(focusWindow));
        
        Point3 average = AverageCV.compute3DWeightedAverage(colors.toArray(), samples.toArray());
        Vec3f color = new Vec3f((float)average.x, (float)average.y, (float)average.z);
        matColor.put(focus.y, focus.x, color.data);        
        return color;
    }    
    
    // get the grid's global color, the average color of all grid nodes
    public Vec3f getGlobalColor()
    {
        MatOfPoint3f colors = new MatOfPoint3f(matColor.submat(sampledWindow));
        MatOfInt samples = new MatOfInt(matSamples.submat(sampledWindow));
        
        Point3 average = AverageCV.compute3DWeightedAverage(colors.toArray(), samples.toArray());
        return new Vec3f((float)average.x, (float)average.y, (float)average.z);
    }    

    /**
     * Merge this color grid with another one (of the same size). 
     * It merges both color matrices, averaging the coincident color nodes. It also merges the internal sample grids. Only done if both grids have the same size.
     * @return true if merge done, false otherwise
     */
    public boolean merge(ColorGrid colorGrid)
    {
        // get masks of both grids
//        Mask mask1 = new Mask(getSamplesMask(), sampledWindow);
//        Mask mask2 = new Mask(colorGrid.getSamplesMask(), colorGrid.sampledWindow);

        // skip if grids have different sizes
        if (super.merge(colorGrid))
        {
            // roi both color matrices with merged sampled window
            Mat matColor1 = matColor.submat(sampledWindow);
            Mat matColor2 = colorGrid.matColor.submat(sampledWindow);

            // compute average of both color matrices (leaving result in this grid)
            Core.add(matColor1, matColor2, matColor1);
            Core.multiply(matColor1, new Scalar(0.5, 0.5, 0.5), matColor1);
            return true;
        }
        else
            return false;
    }
    
    // clear the color grid
    @Override
    public void clear()
    {
        super.clear();
        if (!matColor.empty())            
           matColor.setTo(new Scalar(0.0));
        focusColor = new Vec3f();
    }

    @Override
    public String toString()
    {
        String desc = "ColorGrid [ matColor:\n" + matColor.dump() + "\n" + super.toString() + "\n]";
        return desc;
    }
}
							 
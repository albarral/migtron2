/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv.grid;

import java.awt.Point;
import migtron.tron.cv.AverageCV;
import migtron.tron.math.Average3f;
import migtron.tron.math.Vec3f;
import org.opencv.core.Core;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point3;
import org.opencv.core.Rect;
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

    public ColorGrid(int repW, int repH, float reductionFactor)
    {
        super(repW, repH, reductionFactor);
        // create grid sized matrix to store the node colors
        matColor = Mat.zeros(h, w, CvType.CV_32FC3);    
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
    public void addColorSample(Vec3f color)
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
        Rect neighbourhood = getWindowCV();                
        MatOfPoint3f colors = new MatOfPoint3f(matColor.submat(neighbourhood));
        MatOfInt samples = new MatOfInt(matSamples.submat(neighbourhood));
        
        Point3 average = AverageCV.compute3DWeightedAverage(colors.toArray(), samples.toArray());
        Vec3f color = new Vec3f((float)average.x, (float)average.y, (float)average.z);
        matColor.put(focus.y, focus.x, color.data);        
        return color;
    }    
    
    // get the grid's global color, the average color of all grid nodes
    public Vec3f getGlobalColor()
    {
        Rect sampled = getSampledWindowCV();                
        MatOfPoint3f colors = new MatOfPoint3f(matColor.submat(sampled));
        MatOfInt samples = new MatOfInt(matSamples.submat(sampled));
        
        Point3 average = AverageCV.compute3DWeightedAverage(colors.toArray(), samples.toArray());
        return new Vec3f((float)average.x, (float)average.y, (float)average.z);
    }    

    /**
     * Merge this color grid with another one (of the same size). 
     * It adds both color matrices and the inherited sample grids. Only done if both grids have the same size.
     * @return true if merge done, false otherwise
     */
    public boolean merge(ColorGrid colorGrid)
    {
        // skip if grids have different sizes
        if (super.merge(colorGrid))
        {
            // roi both color matrices
            Rect union = getSampledWindowCV();
            Mat matColor1 = matColor.submat(union);
            Mat matColor2 = colorGrid.matColor.submat(union);

            // compute average of both color matrices (leaving result in this grid)
            Core.add(matColor1, matColor2, matColor1);
            Core.multiply(matColor1, new Scalar(0.5), matColor1);
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
}
							 
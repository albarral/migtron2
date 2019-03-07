/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.cv;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
  * Utility class to perform mathematical operations on OpenCV matrices
 * @author albarral
 */
public class MatrixUtils 
{
    // compute correspondences between the elements (rows vs cols) of a given matrix  (float single channel)
    // only the values above the specified threshold are considered
    public static List<Point> getCorrespondences(Mat mat, float threshold)
    {
        List<Point> listCorrespondences = new ArrayList<>();
        
        // clone input matrix, to avoid altering it
        Mat mat2 = mat.clone();
        Scalar zero = new Scalar(0.0);

        // searching until no more correspondences found
        boolean bsearch = true;
        while (bsearch)
        {
            // find maximum value
            MinMaxLocResult result = Core.minMaxLoc(mat2);

            // if value found above the threshold
            if (result.maxVal > threshold)
            {
                int row = (int)result.maxLoc.y;   
                int col = (int)result.maxLoc.x;   

                // add correspondence
                listCorrespondences.add(new Point(row, col));

                // and clear other matrix relations of both elements
                mat2.row(row).setTo(zero);                
                mat2.col(col).setTo(zero);                
            }                
            // otherwise, finish search
            else
                bsearch = false;
        }     
        return listCorrespondences;
    }        
}

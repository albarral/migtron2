/*
 *  Copyright (C) 2019 by Migtron Robotics   
 *  albarral@migtron.com
 */
package migtron.tron.data;

import migtron.tron.cv.grid.ColorGrid;
import migtron.tron.math.Vec3f;

/**
* This class represents a 2D colored body. 
* It's a Body with a color grid (a reduced representation of the body color)
* @author albarral
 */

public class ColorBody extends Body implements Cloneable
{
    protected ColorGrid colorGrid;       // color grid

    public ColorBody(Body body, ColorGrid colorGrid)
    {
        super((ColorBlob)body, body.mask);        
        this.colorGrid = (ColorGrid)colorGrid.clone();
    }    
            
    @Override
    public Object clone()
    {
        // all members automatically copied
        // then class members cloned for deep copy
        ColorBody cloned = (ColorBody)super.clone();
        cloned.colorGrid = (ColorGrid)colorGrid.clone();
        return cloned;
    }
    
    public ColorGrid getColorGrid() {return colorGrid;}    
    public void setColorGrid(ColorGrid colorGrid)
    {
        this.colorGrid = (ColorGrid)colorGrid.clone();
        // recompute blob color from new color grid
        updateBlobColor();
    }

    @Override
    public void clear()
    {
        super.clear();
        colorGrid.clear();
    }

    public void merge(ColorBody colorBody)
    {
        // merge body part
        super.merge((Body)colorBody);
        // merge color grid
        colorGrid.merge(colorBody.colorGrid);
        // recompute blob color from new color grid
        updateBlobColor();
    }

    // recompute the blob color from the color grid
    private void updateBlobColor()    
    {
        Vec3f meanColor = colorGrid.getGlobalColor();
        this.setRGB(meanColor);
    }
    
    @Override
    public String toString()
    {
        String desc = "ColorBody [" + super.toString() + "]";
        return desc;
    }

    @Override
    public String shortDesc()
    {
        String desc = "ColorBody [" + super.shortDesc() + "]";
        return desc;
    }
}
							 
package com.example.smilinknight.pictureplay.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;

/**
 * Created by smilinknight on 2018-02-01.
 */

public class SwirlFilter implements Filter {

    public Bitmap filter(Bitmap original, Bitmap image) {

        // This code originates from supercomputingblog.com
        // credit goes to supercomputingblog.com
        // http://supercomputingblog.com/openmp/image-twist-and-swirl-algorithm/

        double factor = 0.00005;
        double cX = original.getWidth()/2.0f;
        double cY = original.getHeight()/2.0f;

        int nPixels = original.getHeight()*original.getWidth();

        for (int i=0; i < original.getHeight(); i++)
        {
            double relY = cY-i;
            for (int j=0; j < original.getWidth(); j++)
            {
                double relX = j-cX;
                // relX and relY are points in our UV space
                // Calculate the angle our points are relative to UV origin. Everything is in radians.
                double originalAngle;
                if (relX != 0)
                {
                    originalAngle = Math.atan(Math.abs(relY)/Math.abs(relX));
                    if ( relX > 0 && relY < 0) originalAngle = 2.0f*Math.PI - originalAngle;
                    else if (relX <= 0 && relY >=0) originalAngle = Math.PI-originalAngle;
                    else if (relX <=0 && relY <0) originalAngle += Math.PI;
                }
                else
                {
                    // Take care of rare special case
                    if (relY >= 0) originalAngle = 0.5f * Math.PI;
                    else originalAngle = 1.5f * Math.PI;
                }
                // Calculate the distance from the center of the UV using pythagorean distance
                double radius = Math.sqrt(relX*relX + relY*relY);
                // Use any equation we want to determine how much to rotate image by
                //double newAngle = originalAngle + factor*radius; // a progressive twist
                double newAngle = originalAngle + 1/(factor*radius+(4.0f/Math.PI));
                // Transform source UV coordinates back into bitmap coordinates
                int srcX = (int)(Math.floor(radius * Math.cos(newAngle)+0.5f));
                int srcY = (int)(Math.floor(radius * Math.sin(newAngle)+0.5f));
                srcX += cX;
                srcY += cY;
                srcY = original.getHeight() - srcY;
                // Clamp the source to legal image pixel
                if (srcX < 0) srcX = 0;
                else if (srcX >= original.getWidth()) srcX = original.getWidth()-1;
                if (srcY < 0) srcY = 0;
                else if (srcY >= original.getHeight()) srcY = original.getHeight()-1;
                // Set the pixel color
                image.setPixel(j, i, original.getPixel(srcX, srcY));
            }
        }

        return image;
    }

}

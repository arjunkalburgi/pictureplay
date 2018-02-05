package com.example.smilinknight.pictureplay.filter;

import android.graphics.Bitmap;

/**
 * Created by smilinknight on 2018-02-01.
 */

public class SwirlFilter implements Filter {

    public Bitmap filter(Bitmap original, Bitmap image) {

        // This code originates from supercomputingblog.com
        // with minor adaptions by myself to create the desired effect.
        // Credit goes to supercomputingblog.com
        // http://supercomputingblog.com/openmp/image-twist-and-swirl-algorithm/

        double factor = 0.03;
        double cW = (original.getWidth()-1)/2.0;
        double cH = (original.getHeight()-1)/2.0;

        for (int h=0; h < original.getHeight(); h++)
        {
            double relH = cH-h;
            for (int w=0; w < original.getWidth(); w++)
            {
                double relW = w-cW;
                // relW and relH are points in our UV space
                // Calculate the angle our points are relative to UV origin. Everything is in radians.
                double originalAngle;
                if (relW != 0)
                {
                    originalAngle = Math.atan(Math.abs(relH)/Math.abs(relW));
                    if (relW > 0 && relH < 0) originalAngle = 2.0*Math.PI - originalAngle;
                    else if (relW <= 0 && relH >=0) originalAngle = Math.PI-originalAngle;
                    else if (relW <=0 && relH <0) originalAngle += Math.PI;
                }
                else
                {
                    // Take care of rare special case
                    if (relH >= 0) originalAngle = 0.5 * Math.PI;
                    else originalAngle = 1.5 * Math.PI;
                }
                // Calculate the distance from the center of the UV using pythagorean distance
                double radius = Math.sqrt(relW*relW + relH*relH);
                // Use any equation we want to determine how much to rotate image by
                double newAngle = originalAngle + factor*radius; // a progressive twist
                // Transform source UV coordinates back into bitmap coordinates
                int srcW = (int) (Math.cos(newAngle) * radius);
                int srcH = (int) (Math.sin(newAngle) * radius);
                srcW += cW;
                srcH += cH;
                srcH = original.getHeight() - srcH;
                // Clamp the source to legal image pixel
                if (srcW < 0) srcW = 0;
                else if (srcW >= original.getWidth()) srcW = original.getWidth()-1;
                if (srcH < 0) srcH = 0;
                else if (srcH >= original.getHeight()) srcH = original.getHeight()-1;
                // Set the pixel color
                image.setPixel(w, h, original.getPixel(srcW, srcH));
            }
        }

        return image;
    }

}

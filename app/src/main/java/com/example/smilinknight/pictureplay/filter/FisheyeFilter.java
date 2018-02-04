package com.example.smilinknight.pictureplay.filter;

import android.graphics.Bitmap;

/**
 * Created by smilinknight on 2018-02-02.
 */

public class FisheyeFilter implements Filter {
    public Bitmap filter(Bitmap original, Bitmap image) {

        // create the result data
        // for each row
        for (int y=0;y<original.getHeight();y++) {
            // normalize y coordinate to -1 ... 1
            double ny = ((2.0*y)/original.getHeight())-1;
            // pre calculate ny*ny
            double ny2 = ny*ny;
            // for each column
            for (int x=0;x<original.getWidth();x++) {
                // normalize x coordinate to -1 ... 1
                double nx = ((2.0*x)/original.getWidth())-1;
                // pre calculate nx*nx
                double nx2 = nx*nx;
                // calculate distance from center (0,0)
                // this will include circle or ellipse shape portion
                // of the image, depending on image dimensions
                // you can experiment with images with different dimensions
                double r = Math.sqrt(nx2+ny2);
                // discard pixels outside from circle!
                if (0.0<=r&&r<=1.0) {
                    double nr = Math.sqrt(1.0-r*r);
                    // new distance is between 0 ... 1
                    nr = (r + (1.0-nr)) / 2.0;
                    // discard radius greater than 1.0
                    if (nr<=1.0) {
                        // calculate the angle for polar coordinates
                        double theta = Math.atan2(ny,nx);
                        // calculate new x position with new distance in same angle
                        double nxn = nr*Math.cos(theta);
                        // calculate new y position with new distance in same angle
                        double nyn = nr*Math.sin(theta);
                        // map from -1 ... 1 to image coordinates
                        int x2 = (int)(((nxn+1)*original.getWidth())/2.0);
                        // map from -1 ... 1 to image coordinates
                        int y2 = (int)(((nyn+1)*original.getHeight())/2.0);
                        // find (x2,y2) position from source pixels
                        int srcpos = (int)(y2*original.getWidth()+x2);
                        // make sure that position stays within arrays
                        if (srcpos>=0 & srcpos < original.getWidth()*original.getHeight()) {
                            // get new pixel (x2,y2) and put it to target array at (x,y)
                            image.setPixel(x, y, original.getPixel(x2, y2));
                        }
                    }
                }
            }
        }
        //return result pixels
        return image;
    }

}
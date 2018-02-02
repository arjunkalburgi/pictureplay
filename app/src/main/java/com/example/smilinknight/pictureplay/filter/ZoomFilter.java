package com.example.smilinknight.pictureplay.filter;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by smilinknight on 2018-02-02.
 */

public class ZoomFilter implements Filter {

    int newImageWidth;
    int newImageHeight;

    private int mapPixelValue(Bitmap o, int w, int h) {
        int mappedw = (int) Math.floor(o.getWidth()*w/newImageWidth);
        int mappedh = (int) Math.floor(o.getHeight()*h/newImageHeight);
        return o.getPixel(mappedw, mappedh);
    }

    public Bitmap filter(Bitmap original, Bitmap image) {
        double zoomLevel = 0.1;
        newImageWidth = (int) Math.floor(original.getWidth() * zoomLevel);
        newImageHeight = (int) Math.floor(original.getHeight() * zoomLevel);
        image = Bitmap.createBitmap(newImageWidth, newImageHeight, Bitmap.Config.ARGB_8888);
//        Log.d("ZoomFilter", "original: " + original.getWidth() + "\n image: " + image.getWidth());

        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                image.setPixel(w, h, mapPixelValue(original, w, h));
            }
        }

        return image;
    }
}

package com.example.smilinknight.pictureplay.filter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by smilinknight on 2018-01-24.
 */

public class NoiseReducer implements Filter {

    public Bitmap filter(Bitmap image, int h, int w) {
        int pixel = image.getPixel(w, h);
        int R = Color.red(pixel);
        int G = Color.green(pixel);
        int B = Color.blue(pixel);
        if (R < 162 && G < 162 && B < 162)
            image.setPixel(w, h, Color.BLACK);
        else if (R > 162 && G > 162 && B > 162)
            image.setPixel(w, h, Color.WHITE);

        return image;
    }
}

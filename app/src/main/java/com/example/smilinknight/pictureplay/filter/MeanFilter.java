package com.example.smilinknight.pictureplay.filter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by smilinknight on 2018-01-24.
 */

public class MeanFilter implements Filter {

    public Bitmap filter(Bitmap original, Bitmap image) {

        for (int h = 0; h < original.getHeight(); h++) {
            for (int w=0; w<original.getWidth(); w++) {
                int pixel = original.getPixel(w, h);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (R < 100 && G < 100 && B < 100)
                    image.setPixel(w, h, Color.BLACK);
                else if (R > 100 && G > 100 && B > 100)
                    image.setPixel(w, h, Color.WHITE);
            }
        }

        return image;
    }
}

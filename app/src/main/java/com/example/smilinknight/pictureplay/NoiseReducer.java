package com.example.smilinknight.pictureplay;

import android.graphics.Color;

/**
 * Created by smilinknight on 2018-01-24.
 */

public class NoiseReducer implements Filter {

    public int filter(int[] pixels) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int alpha = 0;
        for(int i : pixels) {
            alpha += Color.alpha(i);
            blue += Color.blue(i);
            green += Color.green(i);
            red += Color.red(i);
        }
        return Color.argb(alpha/pixels.length, red/pixels.length, green/pixels.length, blue/pixels.length);

    }

}

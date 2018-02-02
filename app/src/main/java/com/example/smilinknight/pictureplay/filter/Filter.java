package com.example.smilinknight.pictureplay.filter;

import android.graphics.Bitmap;

/**
 * Created by smilinknight on 2018-01-24.
 */

public interface Filter {
    Bitmap filter(Bitmap original, Bitmap image);
}

package com.example.smilinknight.pictureplay.filter;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by smilinknight on 2018-02-01.
 */

public class MedianFilter implements Filter {

    public static int median(List<Integer> m) {
        int middle = m.size()/2;
        if (m.size()%2 == 1) {
            return m.get(middle);
        } else {
            return (int) Math.floor((m.get(middle-1) + m.get(middle))/2.0);
        }
    }


    public Bitmap filter(Bitmap original, Bitmap image) {

        for (int h = 0; h < original.getHeight(); h++) {
            for (int w = 0; w < original.getWidth(); w++) {

                List<Integer> R = new ArrayList<Integer>();
                List<Integer> G = new ArrayList<Integer>();
                List<Integer> B = new ArrayList<Integer>();
                List<Integer> A = new ArrayList<Integer>();

                for (int i = h - 1; i < h + 1; i++) {
                    for (int j = w - 1; j < w + 1; j++) {
                        try {
                            int pixel = original.getPixel(j, i);
                            R.add(Color.red(pixel));
                            B.add(Color.blue(pixel));
                            G.add(Color.green(pixel));
                            A.add(Color.alpha(pixel));
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
                Collections.sort(R);
                Collections.sort(B);
                Collections.sort(G);
                Collections.sort(A);
                image.setPixel(w, h, Color.argb(median(A), median(R),
                                                median(G), median(B)));

            }
        }

        return image;
    }
}

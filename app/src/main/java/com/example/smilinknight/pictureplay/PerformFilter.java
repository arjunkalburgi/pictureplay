package com.example.smilinknight.pictureplay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by smilinknight on 2018-01-23.
 */

public class PerformFilter extends AsyncTask<Bitmap, Integer, Bitmap> {

    private ImageView image;
    private Filter filter;
    private int filterSize;
    private Context ctx;

    public PerformFilter(ImageView image, Filter filter, int size, Context context) {
        super();
        this.image = image;
        this.filter = filter;
        this.filterSize = size;
        ctx = context;
    }


    @Override
    protected void onPreExecute() {
        final PerformFilter task = this;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... params) {
        Bitmap image = params[0];
        Bitmap newMap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
        int[] pixels = new int[image.getWidth() * image.getHeight()];

        // do something

        return newMap;
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap newImage) {
        image.setImageBitmap(newImage);
    }

}

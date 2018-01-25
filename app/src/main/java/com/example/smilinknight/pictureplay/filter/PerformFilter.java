package com.example.smilinknight.pictureplay.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by smilinknight on 2018-01-23.
 */

public class PerformFilter extends AsyncTask<Bitmap, Integer, Bitmap> {

    private ImageView image;
    private Filter filter;
    private int filterSize;
    private Context ctx;
    public AsyncResponse delegate = null;

    public PerformFilter(ImageView image, Filter filter, AsyncResponse delegate, Context context) {
        super();
        this.image = image;
        this.filter = filter;
        this.ctx = context;
        this.delegate = delegate;
    }


    @Override
    protected void onPreExecute() {
        final PerformFilter task = this;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... params) {
        Bitmap image = params[0];
        Bitmap newMap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
        int[] pixels = new int[image.getHeight() * image.getHeight()];
        filterSize = image.getHeight();


        return newMap;
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap newImage) {
        Log.d("PerformFilter", "do in background filter");
        delegate.processFinish(newImage);
    }

}

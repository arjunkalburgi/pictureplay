package com.example.smilinknight.pictureplay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smilinknight.pictureplay.filter.AsyncResponse;
import com.example.smilinknight.pictureplay.filter.Filter;
import com.example.smilinknight.pictureplay.filter.FisheyeFilter;
import com.example.smilinknight.pictureplay.filter.MedianFilter;
import com.example.smilinknight.pictureplay.filter.PerformFilter;
import com.example.smilinknight.pictureplay.filter.MeanFilter;
import com.example.smilinknight.pictureplay.filter.SwirlFilter;
import com.example.smilinknight.pictureplay.filter.ZoomFilter;
import com.example.smilinknight.pictureplay.helpers.MegaGestureListener;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class PicturePlayer extends AppCompatActivity {

    Context PicturePlayerContext = this;
    private static final String TAG = "PicturePlayer";

    ImageView image;
    int undo_allowance;
    Deque<Bitmap> versions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Intent i = getIntent();
        Uri image_uri = i.getData();
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageURI(image_uri);
        undo_allowance = i.getIntExtra("undo_allowance", 1);
        versions = new LinkedBlockingDeque<>();

        Log.d(TAG, "the undo allowance is: " + undo_allowance);

        image.setOnTouchListener(new MegaGestureListener(this) {
            @Override
            public void onSwipeDown() {
                Toast.makeText(PicturePlayer.this, "Saving image...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                if (versions.size()>0) {
                    Toast.makeText(PicturePlayer.this, "Undoing last action...", Toast.LENGTH_SHORT).show();
                    image.setImageBitmap(versions.removeLast());
                } else {
                    Toast.makeText(PicturePlayer.this, "No more undo's available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeUp() {
                Toast.makeText(PicturePlayer.this, "Zooming...", Toast.LENGTH_SHORT).show();
                applyFilter(new ZoomFilter());
            }

            @Override
            public void onDoubleTap(MotionEvent e) {
                Toast.makeText(PicturePlayer.this, "Swirling...", Toast.LENGTH_SHORT).show();
                applyFilter(new SwirlFilter());
            }

            @Override
            public void onLongPress (MotionEvent event) {
                Toast.makeText(PicturePlayer.this, "Making FishEye...", Toast.LENGTH_SHORT).show();
                applyFilter(new FisheyeFilter());
            }
        });

    }

    public void applyFilter(Filter filter) {
        Log.d(TAG, "Apply filter " + filter.getClass().getName());
        saveVersion();
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        (new PerformFilter(image, filter, new AsyncResponse() {
                @Override
                public void processFinish(Bitmap output) {
                    image.setImageBitmap(output);
                }
            }, this)).execute(drawable.getBitmap());
    }

    public void  saveVersion() {
        Log.d(TAG, "version's size: " + versions.size());
        if (versions.size() >= undo_allowance) versions.removeFirst();
        versions.addLast(((BitmapDrawable) image.getDrawable()).getBitmap());
    }

}

package com.example.smilinknight.pictureplay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smilinknight.pictureplay.filter.AsyncResponse;
import com.example.smilinknight.pictureplay.filter.Filter;
import com.example.smilinknight.pictureplay.filter.MedianFilter;
import com.example.smilinknight.pictureplay.filter.PerformFilter;
import com.example.smilinknight.pictureplay.filter.MeanFilter;
import com.example.smilinknight.pictureplay.filter.SwirlFilter;
import com.example.smilinknight.pictureplay.helpers.MegaGestureListener;

public class PicturePlayer extends AppCompatActivity {

    Context PicturePlayerContext = this;
    private static final String TAG = "PicturePlayer";

    ImageView image;
    int undo_allowance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Intent i = getIntent();
        Uri image_uri = i.getData();
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageURI(image_uri);
        undo_allowance = i.getIntExtra("undo_allowance", 1);
        Log.d(TAG, "the undo allowance is: " + undo_allowance);

        image.setOnTouchListener(new MegaGestureListener(this) {
            @Override
            public void onSwipeDown() {
                Toast.makeText(PicturePlayer.this, "Down, this will save the image.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                Toast.makeText(PicturePlayer.this, "This will make an undo.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeUp() {
                Toast.makeText(PicturePlayer.this, "This will zoom in.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleTap(MotionEvent e) {
                Toast.makeText(PicturePlayer.this, "Wanna do swirl", Toast.LENGTH_SHORT).show();
                applyFilter(new SwirlFilter());
            }

            @Override
            public void onLongPress (MotionEvent event) {
                Toast.makeText(PicturePlayer.this, "Wanna do fisheye", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void applyFilter(Filter filter) {
        Log.d(TAG, "Apply filter");
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        (new PerformFilter(image, filter, new AsyncResponse() {
                @Override
                public void processFinish(Bitmap output) {
                    image.setImageBitmap(output);
                }
            }, this)).execute(drawable.getBitmap());
    }

}

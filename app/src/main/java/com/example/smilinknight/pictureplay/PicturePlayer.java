package com.example.smilinknight.pictureplay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smilinknight.pictureplay.filter.AsyncResponse;
import com.example.smilinknight.pictureplay.filter.Filter;
import com.example.smilinknight.pictureplay.filter.PerformFilter;
import com.example.smilinknight.pictureplay.filter.NoiseReducer;

public class PicturePlayer extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
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

        Button first_button = (Button) findViewById(R.id.first_button);
        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "first_button pressed.");
                image.clearColorFilter();
                image.setColorFilter(R.color.colorPrimary);
            }
        });

        Button second_button = (Button) findViewById(R.id.second_button);
        second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "second_button pressed.");
                image.clearColorFilter();
                applyFilter(new NoiseReducer());
            }
        });

        Button third_button = (Button) findViewById(R.id.third_button);
        third_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "third_button pressed.");
                image.clearColorFilter();
            }
        });

    }

    public void applyFilter(Filter filter) {
        Log.d(TAG, "Apply filter");
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        (new PerformFilter(image, filter, new AsyncResponse() {
                @Override
                public void processFinish(Bitmap output) {
//                    image.setImageBitmap(output);
                    image.clearColorFilter();
                }
            }, this)).execute(drawable.getBitmap());
    }

}

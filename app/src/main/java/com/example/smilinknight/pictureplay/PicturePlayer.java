package com.example.smilinknight.pictureplay;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
                applyFilter();
            }
        });

        Button third_button = (Button) findViewById(R.id.third_button);
        third_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "third_button pressed.");
                image.clearColorFilter();
//                image.setColorFilter(R.color.colorAccent);
            }
        });

    }

    public void applyFilter() {
        Log.d(TAG, "Apply filter");
//        SharedPreferences pref = getPreferences(MODE_PRIVATE);
//        Filter filter = new MedianFilter();
//        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
//        (new FilterTask(filter, 1, image, this)).execute(drawable.getBitmap());
    }

}

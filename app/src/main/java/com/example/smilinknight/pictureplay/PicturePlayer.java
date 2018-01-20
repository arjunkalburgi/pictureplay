package com.example.smilinknight.pictureplay;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PicturePlayer extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Uri image_uri = getIntent().getData();

        image = (ImageView) findViewById(R.id.imageView);
        image.setImageURI(image_uri);

    }
}

package com.example.smilinknight.pictureplay;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PictureSelector extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final String TAG = "ImageSelector";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Image selector button pressed.");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Image selection returned");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE:
                Uri image = data.getData();
                Log.d(TAG, "Image at " + image.toString() + " chosen");
                Log.d(TAG, "Passing image to next activity");
                Intent intent = new Intent();
                intent.setClass(this, PicturePlayer.class);
                intent.setData(image);
                startActivity(intent);
        }
    }
}

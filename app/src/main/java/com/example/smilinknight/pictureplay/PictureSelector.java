package com.example.smilinknight.pictureplay;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureSelector extends AppCompatActivity {

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_PICKER = 2;
    private static final String TAG = "PictureSelector";

    Bundle bundle = new Bundle();
    Context PictureSelectorContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);

        bundle.putString("test", "testing");

        NumberPicker np = (NumberPicker) findViewById(R.id.undonum);
        np.setMinValue(1);
        np.setMaxValue(10);
        np.setWrapSelectorWheel(true);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                bundle.putInt("undo_allowance", newVal);
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Image selector button pressed.");
                dispatchPickPictureIntent();
            }
        });

        Button buttontake = (Button) findViewById(R.id.buttontake);
        buttontake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Picture taker button pressed.");
                Log.d(TAG, "the undo allowance is: " + bundle.getInt("undo_allowance") + ". CAMERA TIME");
                if (ContextCompat.checkSelfPermission(PictureSelectorContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    ActivityCompat.requestPermissions((Activity)PictureSelectorContext,
                            new String[Manifest.permission.CAMERA.length()],
                            1111);
                }
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
            case REQUEST_IMAGE_PICKER:
                Uri image = data.getData();
                Log.d(TAG, "Image at " + image.toString() + " chosen");
                Log.d(TAG, "Passing image to next activity");
                Intent select_intent = new Intent();
                select_intent.setClass(this, PicturePlayer.class);
                select_intent.setData(image);
                select_intent.putExtras(bundle);
                startActivity(select_intent);
                break;
            case REQUEST_TAKE_PHOTO:
                Intent capture_intent = new Intent();
                capture_intent.setClass(this, PicturePlayer.class);
                capture_intent.setData(Uri.parse("file:" + mCurrentPhotoPath));
                capture_intent.putExtras(bundle);
                startActivity(capture_intent);
                break;
        }
    }

    // ref: https://developer.android.com/training/camera/photobasics.html
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void dispatchPickPictureIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        Log.d(TAG, "the undo allowance is: " + bundle.getInt("undo_allowance") + ". PHOTO TIME");
        startActivityForResult(intent, REQUEST_IMAGE_PICKER);
    }
}

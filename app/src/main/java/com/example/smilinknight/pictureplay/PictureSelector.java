package com.example.smilinknight.pictureplay;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class PictureSelector extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final String TAG = "PictureSelector";


    Intent intent = new Intent();
    Bundle bundle = new Bundle();

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
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                Log.d(TAG, "the undo allowance is: " + bundle.getInt("undo_allowance"));
                intent.putExtras(bundle);
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

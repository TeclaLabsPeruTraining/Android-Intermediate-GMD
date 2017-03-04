package com.gmd.lessons.multimedia;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CameraIntentActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final String TAG = "CameraIntentA";
    private final int ACTION_TAKE_PHOTO= 100;
    private final int ACTION_GALLERY_PHOTO= 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        findViewById(R.id.btnCamera).setOnClickListener(this);
        findViewById(R.id.btnGallery).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCamera:
                gotoCamera();
                break;
            case R.id.btnGallery:
                gotoGallery();
                break;
        }
    }

    private void gotoCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO);
    }


    private void gotoGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(galleryIntent, ACTION_GALLERY_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case ACTION_TAKE_PHOTO:
                    if(resultCode==RESULT_OK){
                        Log.v(TAG, "take photo "+data);
                    }
                break;
            case ACTION_GALLERY_PHOTO:
                    if(requestCode==RESULT_OK){
                        Log.v(TAG, "gallery photo "+data);
                    }
                break;
        }
    }
}

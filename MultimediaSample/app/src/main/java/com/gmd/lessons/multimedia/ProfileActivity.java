package com.gmd.lessons.multimedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    private final int REQUEST_CAMERA=1;
    private View iviPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ui();
    }

    private void ui() {
        iviPhoto= findViewById(R.id.iviPhoto);
        iviPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCamera();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void gotoCamera() {
        startActivityForResult(new Intent(this,CameraActivity.class),REQUEST_CAMERA);
    }
}

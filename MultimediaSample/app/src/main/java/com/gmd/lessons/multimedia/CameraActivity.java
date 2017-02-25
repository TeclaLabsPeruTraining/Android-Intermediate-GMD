package com.gmd.lessons.multimedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmd.lessons.multimedia.fragments.OnCamerActionListener;

public class CameraActivity extends AppCompatActivity implements OnCamerActionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }
}

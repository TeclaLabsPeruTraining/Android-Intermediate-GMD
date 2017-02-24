package com.gmd.lessons.asynchronous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thread);
        app();
        
    }

    private void app() {
    }
}

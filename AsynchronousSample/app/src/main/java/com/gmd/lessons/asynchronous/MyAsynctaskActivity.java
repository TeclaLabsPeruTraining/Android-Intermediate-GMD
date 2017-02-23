package com.gmd.lessons.asynchronous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyAsynctaskActivity extends AppCompatActivity {

    private SleepAsynctask sleepAsynctask;
    private SleepAsynctask sleepAsynctask2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_asynctask);
        app();
    }

    private void app() {
        sleepAsynctask= new SleepAsynctask(1);
        sleepAsynctask2= new SleepAsynctask(2);

        sleepAsynctask.execute(2000);
        sleepAsynctask2.execute(4000);
    }
}

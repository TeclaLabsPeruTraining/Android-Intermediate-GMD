package com.gmd.lessons.asynctaskloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gmd.lessons.asynctaskloader.model.UserEntity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        app();
    }

    private void app() {
        if(getIntent()!=null && getIntent().getExtras()!=null){
            UserEntity user=(UserEntity) getIntent().getExtras().getSerializable("USER");

            ((TextView)findViewById(R.id.tviUser)).setText(user.toString());

        }
    }
}

package com.gmd.lessons.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;

public class ImageTaskActivity extends AppCompatActivity {

    private ImageView iviLogo;
    private ImageTask imageTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_task);
        iviLogo= (ImageView)findViewById(R.id.iviLogo);

        downloadImage();
    }

    private void downloadImage() {
        //https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg
        try{
            URL url = new URL("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg");
            imageTask = new ImageTask(ImageTaskActivity.this, iviLogo);
            imageTask.execute(url);
        }catch (Exception e){
            Log.v("Error",e.toString());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(imageTask !=null && imageTask.getStatus()!= AsyncTask.Status.FINISHED){
            imageTask.cancel(true);
        }
    }
}

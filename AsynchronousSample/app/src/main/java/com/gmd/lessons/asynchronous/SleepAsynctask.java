package com.gmd.lessons.asynchronous;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by emedinaa on 22/02/17.
 */

public class SleepAsynctask extends AsyncTask<Integer,Void,Void>{

    private final String TAG= "CONSOLE";
    private int id;

    public SleepAsynctask(int id) {
        this.id = id;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        try {
            Log.v(TAG,"Job task#" + id + " has started");
            Thread.sleep(params[0]);
            Log.v(TAG,"Job task#" + id +" has finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

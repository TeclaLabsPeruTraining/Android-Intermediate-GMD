package com.gmd.lessons.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gmd.lessons.fcm.model.MessageChat;
import com.gmd.lessons.fcm.storage.model.BaseResponse;
import com.gmd.lessons.fcm.storage.model.NotificationRaw;
import com.gmd.lessons.fcm.storage.rest.ApiClient;
import com.gmd.lessons.fcm.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNotificationActivity extends AppCompatActivity {

    private static final String TAG = "MyNotificationA";
    private View btnSend;
    private EditText eteMessage;
    private RecyclerView rviMessages;
    private String currentMessage;
    private MessageAdapter messageAdapter;
    private List<MessageChat> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);
        extras();
        ui();
        messages= new ArrayList<>();
        messageAdapter= new MessageAdapter(messages);
        rviMessages.setAdapter(messageAdapter);

        LocalBroadcastManager.getInstance(this).
                registerReceiver(broadcastReceiverCallback, new IntentFilter("update-message"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(getIntent()!=null){
            Bundle extras = getIntent().getExtras();
            if(extras!=null) currentMessage= extras.getString("MESSAGE");
        }else{
            currentMessage="";
        }

        Log.v(TAG, "onNewIntent "+currentMessage);
    }

    private void extras() {
        if(getIntent()!=null){
            Bundle extras = getIntent().getExtras();
            if(extras!=null) currentMessage= extras.getString("MESSAGE");
        }else{
            currentMessage="";
        }
        Log.v(TAG, "currentMessage "+currentMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "OnResume....");
        //extras();
        //addMessage();
    }

    private void addMessage() {
        if(messageAdapter!=null){
            if(currentMessage!=null){
                Log.v(TAG, "add message "+currentMessage);
                MessageChat message= new MessageChat();
                message.setValue(currentMessage);
                messageAdapter.addMessage(message);
            }
        }
    }

    private void ui() {
        btnSend= findViewById(R.id.btnSend);
        eteMessage= (EditText) findViewById(R.id.eteMessage);
        rviMessages= (RecyclerView) findViewById(R.id.rviMessages);

        //recyclerview
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        rviMessages.setHasFixedSize(false);
        rviMessages.setLayoutManager(mLayoutManager);
        
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {

        String message= eteMessage.getText().toString();
        String title = (message.length()>=50)?(message.substring(0,50)):(message);

        NotificationRaw notificationRaw= new NotificationRaw();
        notificationRaw.setMessage(message);
        notificationRaw.buildHeader(title,message,message);
        notificationRaw.setPushSinglecast(new String[]{"0a4d4b1e-a041-4ff9-a536-fd995fc22ac0"});

        Call<BaseResponse> callback= ApiClient.getMyApiClient().sendNotification(notificationRaw);

        callback.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

        eteMessage.setText(null);
        eteMessage.requestFocus();
    }

    private BroadcastReceiver broadcastReceiverCallback = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "onReceive ... ");
            if(intent!=null && intent.getExtras()!=null){
                currentMessage= intent.getExtras().getString("message");
                addMessage();
            }
            Log.v(TAG, "onReceive ... message "+currentMessage);
        }
    };
}

package com.gmd.lessons.fcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gmd.lessons.fcm.storage.model.BaseResponse;
import com.gmd.lessons.fcm.storage.model.DeviceRaw;
import com.gmd.lessons.fcm.storage.rest.ApiClient;
import com.gmd.lessons.fcm.utils.DeviceInformation;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private View btnRegister,btnStart;
    private DeviceInformation deviceInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceInformation= new DeviceInformation();
        ui();
    }

    private void ui() {
        btnRegister= findViewById(R.id.btnRegister);
        btnStart= findViewById(R.id.btnStart);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDevice();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRoom();
            }
        });

    }

    private void gotoRoom() {
        startActivity(new Intent(this,MyNotificationActivity.class));
    }

    private void registerDevice() {
        String token = FirebaseInstanceId.getInstance().getToken();

        DeviceRaw deviceRaw= new DeviceRaw();
        deviceRaw.setDeviceToken(token);
        deviceRaw.setDeviceId(deviceInformation.getDeviceID());
        deviceRaw.setOsVersion(deviceInformation.getVersionSDK());
        deviceRaw.setOs(deviceInformation.getOs());

        Call<BaseResponse> callback= ApiClient.getMyApiClient().registerDevice(deviceRaw);
        callback.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful()){

                }else {

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
}

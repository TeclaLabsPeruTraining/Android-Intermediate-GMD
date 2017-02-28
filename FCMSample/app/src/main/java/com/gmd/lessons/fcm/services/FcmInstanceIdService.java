package com.gmd.lessons.fcm.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by eduardomedina on 28/02/17.
 */
public class FcmInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "FcmInstanceIdService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG, "token "+token);

        sendRegisterToken(token);
    }

    private void sendRegisterToken(String token) {

    }


}

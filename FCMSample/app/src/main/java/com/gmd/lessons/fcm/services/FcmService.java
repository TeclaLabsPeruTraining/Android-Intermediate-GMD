package com.gmd.lessons.fcm.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.gmd.lessons.fcm.MyNotificationActivity;
import com.gmd.lessons.fcm.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by eduardomedina on 28/02/17.
 */
public class FcmService extends FirebaseMessagingService {

    private static final String TAG ="FcmService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "Notification " + remoteMessage.getData());
        //buildNotification(remoteMessage.getNotification().getBody());
        String message="";
        String title = "Nueva notificacion";

        if(remoteMessage.getData()!=null){
            message= remoteMessage.getData().get("message");
            title= remoteMessage.getData().get("android-content-title");
        }
        buildNotification(title, message);
        buildBroadCast(message);
    }

    private void buildBroadCast(String message) {

        Intent broadCastIntent = new Intent("update-message");
        broadCastIntent.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadCastIntent);
    }

    private void buildNotification( String title,String messageBody) {
        Intent intent = new Intent( this , MyNotificationActivity. class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("MESSAGE",messageBody);

        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}

package com.weerapat.psu_smart_university;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by weerapat on 9/24/2016 AD.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String strMessage = remoteMessage.getData().get("message");
        String strMessage2 = remoteMessage.getData().get("status");

        Log.d("NOFI : ",strMessage + " " + strMessage2);



        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Notification notification = notificationBuilder
                .setSmallIcon(R.drawable.event_icon)
                .setTicker("Event now")
                .setAutoCancel(true)
                .setContentTitle("Event now")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(strMessage))
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.event_icon))
                .setContentText(strMessage).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}

package com.example.finalproject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class NotificationBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        Long notifyAt = intent.getLongExtra("notifyAt", 0L);
        int id = intent.getIntExtra("id", 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Main Channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
        ;

        NotificationManager manager =(NotificationManager) context.
                getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(id, builder.build());
    }

}

package com.mycompany.igrocery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertEvents extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notificationEvent").
                setSmallIcon(R.drawable.icon).
                setContentTitle("You have an iGrocery Event").
                setContentText("Don't forget to buy your groceries.").
                setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat nManager = NotificationManagerCompat.from(context);
        nManager.notify(100, builder.build());
    }
}

package com.example.projekat.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projekat.R;
import com.example.projekat.calendar.CalendarPage;

public class NotificationIntentService extends IntentService {

    private static final String CHANNEL_ID = "Workout Notification Channel";
    private static final int NOTIFICATION_ID = 100;

    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Workout Notification Channel";
            String description = "Notification channel for workout app";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder builderCompat = new NotificationCompat.Builder(this, CHANNEL_ID);
            builderCompat.setSmallIcon(R.drawable.ic_stat_directions_run);
            builderCompat.setContentTitle("Workout Planner");
            builderCompat.setContentText("Today is a great day for a workout");

            Intent notifyIntent = new Intent(this, CalendarPage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builderCompat.setContentIntent(pendingIntent);
            Notification notificationCompat = builderCompat.build();

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(NOTIFICATION_ID, notificationCompat);
        } else {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.ic_stat_directions_run);
            builder.setContentTitle("Workout Planner");
            builder.setContentText("Today is a great day for a workout");

            Intent notifyIntent = new Intent(this, CalendarPage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(NOTIFICATION_ID, notification);
        }
    }
}

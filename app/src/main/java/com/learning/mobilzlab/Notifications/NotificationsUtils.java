package com.learning.mobilzlab.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.learning.mobilzlab.R;

public class NotificationsUtils {

    public static final String NOTIFICATION_CHANNEL_2 = "CHANNEL_2";
    public static final int NOTIFICATION_ID_CHAT = 1024;
    public static String NOTIFICATION_CHANNEL_1 = "CHANNEL_1";
    private NotificationManager notificationManager;

    private Context context;

    public NotificationsUtils(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createSimpleNotification(String title, String description) {

        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_1)

                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(description)
                .setOnlyAlertOnce(true)
                .build();


        notificationManager.notify(NOTIFICATION_ID_CHAT, notification);


    }

    public void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_1, NOTIFICATION_CHANNEL_1, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Important Messages channel for chat");

            NotificationChannel notificationChanne2 = new NotificationChannel(NOTIFICATION_CHANNEL_2, NOTIFICATION_CHANNEL_2, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Repair Request Messages");

            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.createNotificationChannel(notificationChanne2);


        }


    }
}

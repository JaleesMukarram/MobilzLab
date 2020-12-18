package com.learning.mobilzlab.Notifications;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationsUtils notificationsUtils = new NotificationsUtils(this);
        notificationsUtils.createNotificationChannels();
    }
}

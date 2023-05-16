package com.squizzard;

import android.app.Application;

import com.squizzard.broadcast.NotificationChannelHelper;

public class MisriApplication extends Application {

    private final NotificationChannelHelper notificationChannelHelper = new NotificationChannelHelper();

    @Override
    public void onCreate() {
        super.onCreate();
        notificationChannelHelper.createNotificationChannel(this);
    }
}

package com.squizzard;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squizzard.broadcast.NotificationChannelHelper;

public class MisriApplication extends Application {

    private NotificationChannelHelper notificationChannelHelper = new NotificationChannelHelper();

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        notificationChannelHelper.createNotificationChannel(this);
    }
}

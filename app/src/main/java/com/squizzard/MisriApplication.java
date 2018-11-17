package com.squizzard;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MisriApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

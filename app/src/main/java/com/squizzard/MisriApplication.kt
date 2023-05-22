package com.squizzard

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.squizzard.broadcast.NotificationChannelHelper

class MisriApplication : Application() {
    private val notificationChannelHelper = NotificationChannelHelper()
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate() {
        super.onCreate()
        firebaseAnalytics = Firebase.analytics
        notificationChannelHelper.createNotificationChannel(this)
    }
}
package com.squizzard.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsHelper(private val context: Context) {

    fun sendEvent(tag: String) {
        FirebaseAnalytics.getInstance(context).logEvent(tag, null)
    }

}
package com.squizzard.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object AnalyticsHelper {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    @JvmStatic fun sendEvent(tag: String) {
        firebaseAnalytics.logEvent(tag, null)
    }
}
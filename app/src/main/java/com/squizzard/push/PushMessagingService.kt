
package com.squizzard.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squizzard.converter.ui.ConverterActivity
import com.squizzard.misriCalendar.R

class PushMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(PUSH_TAG, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(PUSH_TAG, "Push Message from" +remoteMessage.from)

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(PUSH_TAG, "Push Message with data" + remoteMessage.data)
        }

        sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body,
            remoteMessage.data[PUSH_DATA_TYPE], remoteMessage.data[PUSH_DATA_ID]
        )
    }

    private fun sendNotification(messageTitle: String?, messageBody: String?, type: String?, documentId: String?) {
        val intent = Intent(this, ConverterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val bundle = Bundle()
        bundle.putString(PUSH_DATA_TYPE, type)
        bundle.putString(PUSH_DATA_ID, documentId)
        intent.putExtras(bundle)
        val pendingIntent = PendingIntent.getActivity(
            this, PUSH_REQUEST_CODE, intent,
            PendingIntent.FLAG_MUTABLE
        )

        val channelId = "IWA_CHANNEL"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher1)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Misrical",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object{
        const val PUSH_TAG = "PUSH"
        const val PUSH_REQUEST_CODE = 7787
        const val PUSH_DATA_TYPE = "type"
        const val PUSH_DATA_ID = "documentID"
    }
}
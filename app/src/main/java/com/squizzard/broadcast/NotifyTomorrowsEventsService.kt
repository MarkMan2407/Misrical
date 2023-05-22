package com.squizzard.broadcast

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.squizzard.Attributes
import com.squizzard.analytics.AnalyticsHelper
import com.squizzard.converter.ui.ConverterActivity
import com.squizzard.miqaatList.MiqaatListActivity
import com.squizzard.misriCalendar.R

class NotifyTomorrowsEventsService : IntentService("NotifyTomorrowsEventsService") {

    override fun onHandleIntent(intent: Intent?) {

        val allEvents: MutableList<String> = mutableListOf()

        val miqaatList: MutableList<String> = GetMiqaatsUseCase().getForTomorrow()
        val reminderList: MutableList<String> = GetRemindersUseCase().getForTomorrow(applicationContext)
        allEvents.addAll(miqaatList)
        allEvents.addAll(reminderList)

        var numEvents = 0
        val notificationBuilder = NotificationCompat.Builder(applicationContext, Attributes.DEFAULT_NOTIFICATION_CHANNEL_ID)

        if (allEvents.isNotEmpty()) {
            notificationBuilder.setContentTitle(applicationContext.getString(R.string.events_for_tomorrow_label))
            val inboxStyle = NotificationCompat.InboxStyle()
            for (x in allEvents.indices) {
                inboxStyle.addLine(allEvents[0])
                numEvents++
            }
            notificationBuilder.setStyle(inboxStyle)
            notificationBuilder.setNumber(numEvents)
            buildAndSendNotification(applicationContext, notificationBuilder)
        } else {
            applicationContext.sendBroadcast(Intent(Attributes.NO_MIQAAT_TOMORROW))
        }
        AnalyticsHelper.sendEvent("event_check_tomorrow")
    }

    private fun buildAndSendNotification(context: Context, notificationBuilder: NotificationCompat.Builder) {
        val resultIntent = Intent(context, MiqaatListActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(ConverterActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(resultPendingIntent)
        notificationBuilder.setSmallIcon(R.drawable.ic_notification)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Attributes.EVENING_ALARM_CODE, notificationBuilder.build())
    }
}
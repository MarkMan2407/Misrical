package com.squizzard.broadcast;

import com.squizzard.Attributes;
import com.squizzard.converter.ui.ConverterActivity;
import com.squizzard.converter.model.Misri;
import com.squizzard.MisriCalendar.R;
import com.squizzard.data.GetGregorianRemindersByDateUseCase;
import com.squizzard.data.GetMisriRemindersByDateUseCase;
import com.squizzard.reminders.model.Reminder;
import com.squizzard.reminders.ui.ReminderListActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int numEvents = 0;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, Attributes.DEFAULT_NOTIFICATION_CHANNEL_ID);

        String key = intent.getStringExtra(Attributes.EVENT_CHECK_KEY);

        if (key.equals(Attributes.MORNING_CHECK_MIQAAT_INTENT)) {
            String[] miqaatList = new GetTodaysEventsUseCase().getTodaysEvents();
            Calendar calendar = Calendar.getInstance();
            List<Reminder> gregorianReminders = new GetGregorianRemindersByDateUseCase(context).
                    getRemindersByDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));
            int[] misriDates = new Misri().getMisriDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            List<Reminder> misriReminders = new GetMisriRemindersByDateUseCase(context).
                    getRemindersByDate(misriDates[0], misriDates[1]);
            if (gregorianReminders != null && misriReminders != null) {
                gregorianReminders.addAll(misriReminders);
            }

            if (miqaatList != null && miqaatList.length > 0 || gregorianReminders.size() > 0) {
                notificationBuilder.setContentTitle(context.getString(R.string.events_for_today_label));
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                for (int x = 0; x < miqaatList.length; x++) {
                    inboxStyle.addLine(miqaatList[0]);
                    numEvents++;
                }
                for(Reminder reminder: gregorianReminders){
                    inboxStyle.addLine(reminder.getReminderText());
                    numEvents++;
                }

                notificationBuilder.setStyle(inboxStyle);
                notificationBuilder.setNumber(numEvents);
                buildAndSendNotification(context, notificationBuilder);
            } else {
                sentAlertDialogBroadcast(context, Attributes.NO_MIQAAT_TODAY);
            }
        } else if (key.equals(Attributes.EVENING_CHECK_MIQAAT_INTENT)) {
            String[] miqaatList = new GetTomorrowsEventsUseCase().getTomorrowsEvents();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            List<Reminder> gregorianReminders = new GetGregorianRemindersByDateUseCase(context).
                    getRemindersByDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));
            int[] misriDates = new Misri().getMisriDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            List<Reminder> misriReminders = new GetMisriRemindersByDateUseCase(context).
                    getRemindersByDate(misriDates[0], misriDates[1]);
            if (gregorianReminders != null && misriReminders != null) {
                gregorianReminders.addAll(misriReminders);
            }


            if (miqaatList != null && miqaatList.length > 0 || gregorianReminders.size() > 0) {
                notificationBuilder.setContentTitle(context.getString(R.string.events_for_tomorrow_label));

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                for (int x = 0; x < miqaatList.length; x++) {
                    inboxStyle.addLine(miqaatList[0]);
                    numEvents++;
                }
                for(Reminder reminder: gregorianReminders){
                    inboxStyle.addLine(reminder.getReminderText());
                    numEvents++;
                }
                notificationBuilder.setStyle(inboxStyle);
                notificationBuilder.setNumber(numEvents);
                buildAndSendNotification(context, notificationBuilder);
            } else {
                sentAlertDialogBroadcast(context, Attributes.NO_MIQAAT_TOMORROW);
            }
        }
    }

    private void buildAndSendNotification(Context context, NotificationCompat.Builder notificationBuilder) {
        Intent resultIntent = new Intent(context, ReminderListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ConverterActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Attributes.MORNING_ALARM_CODE, notificationBuilder.build());
    }

    private void sentAlertDialogBroadcast(Context context, String intentKey) {
        Intent dialogIntent = new Intent(intentKey);
                context.sendBroadcast(dialogIntent);
    }
}

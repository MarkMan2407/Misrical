package com.squizzard.broadcast;

import com.squizzard.Attributes;
import com.squizzard.converter.ui.ConverterActivity;
import com.squizzard.converter.model.Misri;
import com.squizzard.MisriCalendar.R;
import com.squizzard.reminders.ui.ReminderListActivity;
import com.squizzard.util.DateUtil;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver {

    private Misri m = new Misri();

    @Override
    public void onReceive(Context context, Intent intent) {
        String mAction = intent.getAction();
        int todayNumber = m.getMisriOrdinal();
        int numEvents = 0;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        if (mAction.equals(Attributes.MORNING_CHECK_MIQAAT_INTENT)) {//check for today
            String[] miqaatList = DateUtil.priorityEventMap.get(todayNumber);

            if (miqaatList != null && miqaatList.length > 0) {
                mBuilder.setSmallIcon(R.drawable.ic_launcher1)
                        .setContentTitle("Events for today");

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                for (int x = 0; x < miqaatList.length; x++) {
                    inboxStyle.addLine(miqaatList[0]);
                    numEvents++;
                }

                mBuilder.setStyle(inboxStyle);
                mBuilder.setNumber(numEvents);
            } else {
                Intent eveningEventIntent = new Intent(Attributes.NO_MIQAAT_TODAY);
                context.sendBroadcast(eveningEventIntent);
            }
        } else if (mAction.equals(Attributes.EVENING_CHECK_MIQAAT_INTENT)) {//check for tomorrow
            String[] miqaatList = DateUtil.priorityEventMap.get(todayNumber + 1);

            if (miqaatList != null && miqaatList.length > 0) {
                mBuilder.setSmallIcon(R.drawable.ic_launcher1)
                        .setContentTitle("Events for tomorrow");

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                for (int x = 0; x < miqaatList.length; x++) {
                    inboxStyle.addLine(miqaatList[0]);
                    numEvents++;
                }
                mBuilder.setStyle(inboxStyle);
            } else {
                Intent eveningEventIntent = new Intent(Attributes.NO_MIQAAT_TOMORROW);
                context.sendBroadcast(eveningEventIntent);
            }
        }

        Intent resultIntent = new Intent(context, ReminderListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ConverterActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Attributes.MORNING_ALARM_CODE, mBuilder.build());
    }
}

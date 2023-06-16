package com.squizzard.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.squizzard.Attributes;

import java.util.Calendar;

import static com.squizzard.Attributes.EVENING_ALARM_CODE;
import static com.squizzard.Attributes.MORNING_ALARM_CODE;

public class AlarmCoordinator extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            boolean alertsEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Attributes.MIQAATS_ALERT_PREFERENCE, false);
            if (alertsEnabled) {
                turnOnAlarms(context);
            }
        }
    }
	
	public static void turnOnAlarms(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (am != null) {
			Calendar morningAlarm = Calendar.getInstance();
			morningAlarm.set(Calendar.HOUR_OF_DAY, 6);
			morningAlarm.set(Calendar.MINUTE, 0);
			Intent morningEventIntent = new Intent(context, NotifyTodaysEventsService.class);
			PendingIntent morningPendingIntent = PendingIntent.getService(context, MORNING_ALARM_CODE, morningEventIntent, PendingIntent.FLAG_IMMUTABLE);
			am.setRepeating(AlarmManager.RTC_WAKEUP, morningAlarm.getTimeInMillis(), AlarmManager.INTERVAL_DAY, morningPendingIntent);

			Calendar eveningAlarm = Calendar.getInstance();
			eveningAlarm.set(Calendar.HOUR_OF_DAY, 20);
			eveningAlarm.set(Calendar.MINUTE, 0);
			Intent eveningEventIntent = new Intent(context, NotifyTomorrowsEventsService.class);
			PendingIntent eveningPendingIntent = PendingIntent.getService(context, EVENING_ALARM_CODE, eveningEventIntent, PendingIntent.FLAG_IMMUTABLE);
			am.setRepeating(AlarmManager.RTC_WAKEUP, eveningAlarm.getTimeInMillis(), AlarmManager.INTERVAL_DAY, eveningPendingIntent);
		}
	}
	
	public static void turnOffAlarms(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (am != null) {
            Intent morningEventIntent = new Intent(context, NotifyTodaysEventsService.class);
            PendingIntent morningPendingIntent = PendingIntent.getBroadcast(context, MORNING_ALARM_CODE, morningEventIntent, PendingIntent.FLAG_IMMUTABLE);
            am.cancel(morningPendingIntent);
            morningPendingIntent.cancel();

            Intent eveningEventIntent = new Intent(context, NotifyTomorrowsEventsService.class);
            PendingIntent eveningPendingIntent = PendingIntent.getBroadcast(context, EVENING_ALARM_CODE, eveningEventIntent, PendingIntent.FLAG_IMMUTABLE);
            am.cancel(eveningPendingIntent);
            eveningPendingIntent.cancel();
        }
	}
}

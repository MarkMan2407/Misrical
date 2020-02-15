package com.squizzard.broadcast;

import java.util.Calendar;
import android.app.AlarmManager; 
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.squizzard.Attributes;

public class AlarmCoordinator extends BroadcastReceiver {
	
	private final static int MORNING_ALARM = 223;
	private final static int EVENING_ALARM = 222;
	
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
			morningAlarm.set(Calendar.HOUR_OF_DAY, 9);
			morningAlarm.set(Calendar.MINUTE, 0);
			morningAlarm.set(Calendar.SECOND, 0);
			Intent morningEventIntent = new Intent(Attributes.MORNING_CHECK_MIQAAT_INTENT);
			PendingIntent morningPendingIntent = PendingIntent.getBroadcast(context, MORNING_ALARM, morningEventIntent, PendingIntent.FLAG_CANCEL_CURRENT);
			am.setRepeating(AlarmManager.RTC_WAKEUP, morningAlarm.getTimeInMillis(), AlarmManager.INTERVAL_DAY, morningPendingIntent);

			Calendar eveningAlarm = Calendar.getInstance();
			eveningAlarm.set(Calendar.HOUR_OF_DAY, 21);
			eveningAlarm.set(Calendar.MINUTE, 0);
			eveningAlarm.set(Calendar.SECOND, 0);
			Intent eveningEventIntent = new Intent(Attributes.EVENING_CHECK_MIQAAT_INTENT);
			PendingIntent eveningPendingIntent = PendingIntent.getBroadcast(context, 222, eveningEventIntent, PendingIntent.FLAG_CANCEL_CURRENT);
			am.setRepeating(AlarmManager.RTC_WAKEUP, eveningAlarm.getTimeInMillis(), AlarmManager.INTERVAL_DAY, eveningPendingIntent);
		}
	}
	
	public static void turnOffAlarms(Context context)
	{
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (am != null) {
            Intent morningEventIntent = new Intent(Attributes.MORNING_CHECK_MIQAAT_INTENT);
            PendingIntent morningPendingIntent = PendingIntent.getBroadcast(context, MORNING_ALARM, morningEventIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.cancel(morningPendingIntent);
            morningPendingIntent.cancel();

            Intent eveningEventIntent = new Intent(Attributes.EVENING_CHECK_MIQAAT_INTENT);
            PendingIntent eveningPendingIntent = PendingIntent.getBroadcast(context, EVENING_ALARM, eveningEventIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.cancel(eveningPendingIntent);
            eveningPendingIntent.cancel();
        }
	}
}

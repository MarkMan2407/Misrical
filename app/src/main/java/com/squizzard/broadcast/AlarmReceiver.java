package com.squizzard.broadcast;

import java.util.ArrayList;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squizzard.Attributes;
import com.squizzard.converter.ui.ConverterActivity;
import com.squizzard.converter.model.Misri;
import com.squizzard.MisriCalendar.R;
import com.squizzard.data.DatabaseHelper;
import com.squizzard.reminders.Reminder;
import com.squizzard.reminders.ReminderListActivity;
import com.squizzard.util.DateUtil;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver{
	public static final int ADD_DAYS_TODAY = 0;
	public static final int ADD_DAYS_TOMORROW = 1;
	
	private Misri m  = new Misri();
	private DatabaseHelper databaseHelper = null;
	private Context context;
		
	@Override
	public void onReceive(Context context, Intent intent) {
		String mAction = intent.getAction();
		int todayNumber = m.getMisriOrdinal();
		int numEvents = 0;
		this.context = context;
		ArrayList<Reminder> databaseReminders  = new ArrayList<Reminder>();
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		
		if(mAction.equals(Attributes.MORNING_CHECK_MIQAAT_INTENT)){//check for today
			databaseReminders = getHelper().getReminderEvents(ADD_DAYS_TODAY);
            String[] miqaatList = DateUtil.priorityEventMap.get(todayNumber);
			
			if((miqaatList != null && miqaatList.length > 0) || databaseReminders.size() > 0){
				mBuilder.setSmallIcon(R.drawable.ic_launcher1)
				.setContentTitle("Events for today");

				NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
				if(miqaatList != null){
					for(int x = 0; x < miqaatList.length; x++){
						inboxStyle.addLine(miqaatList[0]);
						numEvents++;
					}
				}
				for(Reminder reminder: databaseReminders){
					inboxStyle.addLine(reminder.getReminderText());
					numEvents++;
				}
				mBuilder.setStyle(inboxStyle);
				mBuilder.setNumber(numEvents);
			}else{
				Intent eveningEventIntent= new Intent(Attributes.NO_MIQAAT_TODAY);
				context.sendBroadcast(eveningEventIntent);
			}
		}
		else if(mAction.equals(Attributes.EVENING_CHECK_MIQAAT_INTENT)){//check for tomorrow
			databaseReminders = getHelper().getReminderEvents(ADD_DAYS_TOMORROW);
            String[] miqaatList = DateUtil.priorityEventMap.get(todayNumber + 1);
			
			if((miqaatList != null && miqaatList.length > 0) || databaseReminders.size() > 0){
				mBuilder.setSmallIcon(R.drawable.ic_launcher1)
				.setContentTitle("Events for tomorrow");

				NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
				if(miqaatList != null){
					for(int x = 0; x < miqaatList.length; x++){
						inboxStyle.addLine(miqaatList[0]);
						numEvents++;
					}
				}
				for(Reminder reminder: databaseReminders){
					inboxStyle.addLine(reminder.getReminderText());
					numEvents++;
				}
				mBuilder.setStyle(inboxStyle);
			}else{
				Intent eveningEventIntent= new Intent(Attributes.NO_MIQAAT_TOMORROW);
				context.sendBroadcast(eveningEventIntent);
			}
		}
			
			Intent resultIntent = new Intent(context, ReminderListActivity.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
			stackBuilder.addParentStack(ConverterActivity.class);

			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(Attributes.MORNING_ALARM_CODE, mBuilder.build());			
	}
	
	protected DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
		return databaseHelper;
	}
}

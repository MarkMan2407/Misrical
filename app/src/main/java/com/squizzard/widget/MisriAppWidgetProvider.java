package com.squizzard.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;

import com.squizzard.converter.model.Misri;
import com.squizzard.converter.ui.ConverterActivity;
import com.squizzard.misriCalendar.R;

public class MisriAppWidgetProvider extends AppWidgetProvider{

	private static final String FLIP_DATE_ACTION = "FLIP_DATE";
	private static Misri m  = new Misri();
	private RemoteViews remoteView;
	private static boolean gregorian = false;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		String todayMisri = m.getTodayMisri();
		remoteView = new RemoteViews(context.getPackageName(), R.layout.misri_appwidget_layout);
		remoteView.setTextViewText(R.id.w_misri_date, todayMisri);

		Intent launchAppIntent = new Intent(context, ConverterActivity.class);
		PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context,0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.launcher_icon, launchAppPendingIntent);

		Intent flipDateIntent = new Intent(context, MisriAppWidgetProvider.class);
		flipDateIntent.setAction(FLIP_DATE_ACTION);
		PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, flipDateIntent, 0);
		remoteView.setOnClickPendingIntent(R.id.w_misri_date, pendingIntent2);

		appWidgetManager.updateAppWidget(appWidgetIds, remoteView);
	}
	@Override
	public void onEnabled(Context context){
		context.getApplicationContext().registerReceiver(this, new IntentFilter(FLIP_DATE_ACTION));
		remoteView = new RemoteViews(context.getPackageName(), R.layout.misri_appwidget_layout);	
	}

	@Override
	public void onReceive(Context context, Intent intent){
		super.onReceive(context, intent);
		if (FLIP_DATE_ACTION.equals(intent.getAction())){
			String updateDate;
			if(gregorian){updateDate = m.getTodayMisri(); gregorian=false;}
			else {updateDate=m.getTodayGregorian(); gregorian=true;}
			remoteView = new RemoteViews(context.getPackageName(), R.layout.misri_appwidget_layout);
			remoteView.setTextViewText(R.id.w_misri_date, updateDate);
			ComponentName cn = new ComponentName(context, MisriAppWidgetProvider.class);  
			AppWidgetManager.getInstance(context).updateAppWidget(cn, remoteView);	
		}
	}
}

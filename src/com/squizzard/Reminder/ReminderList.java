package com.squizzard.Reminder;

import java.util.ArrayList;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squizzard.Database.DatabaseHelper;
import com.squizzard.MisriCalendar.Attributes;
import com.squizzard.MisriCalendar.R;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ReminderList extends ActionBarActivity implements OnClickListener{
	private ListView listView;
	private ReminderAdapter adapter;
	private ArrayList<Reminder> reminders;
	private DatabaseHelper databaseHelper;
	private Button btnCheckToday;
	private Button btnCheckTomorrow;
	private boolean isDeleteMode = false;
	
	private BroadcastReceiver todayReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			new AlertDialog.Builder(ReminderList.this)
		    .setTitle("No Events")
		    .setMessage("You have no reminders or Miqaats for today")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        }
		     })
		    .setIcon(R.drawable.ic_launcher1)
		     .show();
		}
	};

	private BroadcastReceiver tomorrowReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			new AlertDialog.Builder(ReminderList.this)
		    .setTitle("No Events")
		    .setMessage("You have no reminders or Miqaats for tomorrow")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        }
		     })
		    .setIcon(R.drawable.ic_launcher1)
		     .show();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.reminder_list);
		
		listView = (ListView)findViewById(R.id.reminder_list);
		adapter = new ReminderAdapter();
		reminders = getHelper().getReminders();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listClickListener);
		btnCheckToday = (Button)findViewById(R.id.miqaatCheckToday);
		btnCheckToday.setOnClickListener(this);
		btnCheckTomorrow = (Button)findViewById(R.id.miqaatCheckTomorrow);
		btnCheckTomorrow.setOnClickListener(this);
	}
	
	private OnItemClickListener listClickListener = new OnItemClickListener(){

	    @Override
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	Reminder reminder = (Reminder) v.getTag();
	    	if(reminder != null){
	    		Intent displayReminderIntent = new Intent(ReminderList.this, ReminderDisplay.class);
	    		displayReminderIntent.putExtra(ReminderDisplay.REMINDER_ID, reminder.getId());
	    		startActivity(displayReminderIntent);
	    	}
	    }       
	};
	
	@Override
	protected void onPause() {
	  unregisterReceiver(todayReceiver);
	  unregisterReceiver(tomorrowReceiver);
	  super.onPause();
	} 
	
	
	@Override 
	public void onResume(){
		super.onResume();
		
		registerReceiver(todayReceiver, new IntentFilter(Attributes.NO_MIQAAT_TODAY));
		registerReceiver(tomorrowReceiver, new IntentFilter(Attributes.NO_MIQAAT_TOMORROW));
		
		isDeleteMode = false;
		
		if(adapter != null){
			reminders = getHelper().getReminders();
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_reminder_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
        case android.R.id.home: 
            onBackPressed();
            break;
		case R.id.reminder_list_add:
			Intent addReminderIntent = new Intent(getApplicationContext(), ReminderAdd.class);
			startActivity(addReminderIntent);
			break;
		case R.id.reminder_list_discard:
			isDeleteMode = !isDeleteMode;
			listView.invalidateViews();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.miqaatCheckToday:
			Intent morningEventIntent= new Intent(Attributes.MORNING_CHECK_MIQAAT_INTENT);
			sendBroadcast(morningEventIntent);	
			break;
		case R.id.miqaatCheckTomorrow:
			Intent eveningEventIntent= new Intent(Attributes.EVENING_CHECK_MIQAAT_INTENT);
			sendBroadcast(eveningEventIntent);
			break;
		}
	}
	
	private class ReminderAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return reminders.size();
		}

		@Override
		public Reminder getItem(int position) {
			return reminders.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			if(isDeleteMode){
				view = inflater.inflate(R.layout.reminder_list_item_delete, null);
			}else{
				view = inflater.inflate(R.layout.reminder_list_item, null);
			}

			Reminder reminder = getItem(position);
			if(reminder != null){
				((TextView)view.findViewById(R.id.reminder_item_text)).setText(reminder.getReminderText());
				((TextView)view.findViewById(R.id.reminder_item_misri_text)).setText(reminder.getMisriDateText());
				((TextView)view.findViewById(R.id.reminder_item_gregorian_text)).setText(reminder.getGregorianDateText());
				
				view.setTag(reminder);
				if(isDeleteMode){
					ImageButton deleteButton = ((ImageButton)view.findViewById(R.id.reminder_list_discard));
					deleteButton.setTag(reminder);
					deleteButton.setOnClickListener(new View.OnClickListener() {
			            @Override
			            public void onClick(View clickedButton) {
			            	getHelper().deleteReminder(((Reminder)clickedButton.getTag()).getId());
			            	reminders = getHelper().getReminders();
			            	notifyDataSetChanged();
			            }
			        });
				}
			}
			return view;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
	
	protected DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}
}

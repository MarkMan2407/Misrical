package com.squizzard.reminders.ui;

import java.util.ArrayList;

import com.squizzard.MisriCalendar.databinding.ReminderListBinding;
import com.squizzard.broadcast.AlarmReceiver;
import com.squizzard.data.DeleteReminderUseCase;
import com.squizzard.data.GetRemindersUseCase;
import com.squizzard.Attributes;
import com.squizzard.MisriCalendar.R;
import com.squizzard.reminders.model.Reminder;
import com.squizzard.reminders.reminderList.ReminderListViewModel;
import com.squizzard.util.DateUtil;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
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

public class ReminderListActivity extends AppCompatActivity implements OnClickListener{
	private ListView listView;
	private ReminderAdapter adapter;
	private ArrayList<Reminder> reminders;
	private boolean isDeleteMode = false;
	
	private BroadcastReceiver todayReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			new AlertDialog.Builder(ReminderListActivity.this)
		    .setTitle(R.string.no_events)
		    .setMessage(R.string.no_events_today)
		    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        }
		     })
		    .setIcon(R.drawable.ic_launcher1).show();
		}
	};

	private BroadcastReceiver tomorrowReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			new AlertDialog.Builder(ReminderListActivity.this)
		    .setTitle(R.string.no_events)
		    .setMessage(R.string.no_events_tomorrow)
		    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        }
		     })
		    .setIcon(R.drawable.ic_launcher1).show();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.reminder_list);

		ReminderListViewModel viewModel = new ViewModelProvider(this).get(ReminderListViewModel.class);
		ReminderListBinding binding = DataBindingUtil.setContentView(this, R.layout.reminder_list);
		binding.setLifecycleOwner(this);
		viewModel.getCheckTodaysEventsPressed().observe(this, click -> {
                Log.d("MM_DEBUG", "click");
		});
		
		listView = findViewById(R.id.reminder_list);
		adapter = new ReminderAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listClickListener);
		Button btnCheckToday = findViewById(R.id.miqaatCheckToday);
		btnCheckToday.setOnClickListener(this);
		Button btnCheckTomorrow = findViewById(R.id.miqaatCheckTomorrow);
		btnCheckTomorrow.setOnClickListener(this);
	}
	
	private OnItemClickListener listClickListener = new OnItemClickListener(){

	    @Override
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	Reminder reminder = (Reminder) v.getTag();
	    	if(reminder != null){
	    		Intent displayReminderIntent = new Intent(ReminderListActivity.this, DisplayReminderActivity.class);
	    		displayReminderIntent.putExtra(DisplayReminderActivity.REMINDER_ID, reminder.getId());
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
			reminders = (ArrayList)new GetRemindersUseCase(getApplicationContext()).getReminders();
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
			Intent addReminderIntent = new Intent(getApplicationContext(), AddReminderActivity.class);
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
			Intent morningEventIntent = new Intent(this, AlarmReceiver.class);
			morningEventIntent.putExtra(Attributes.EVENT_CHECK_KEY, Attributes.MORNING_CHECK_MIQAAT_INTENT);
			sendBroadcast(morningEventIntent);
			break;
		case R.id.miqaatCheckTomorrow:
			Intent eveningEventIntent= new Intent(this, AlarmReceiver.class);
			eveningEventIntent.putExtra(Attributes.EVENT_CHECK_KEY, Attributes.EVENING_CHECK_MIQAAT_INTENT);
			sendBroadcast(eveningEventIntent);
			break;
		}
	}
	
	private class ReminderAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (reminders != null) {
				return reminders.size();
			} else return 0;
		}

		@Override
		public Reminder getItem(int position) {
			return reminders.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			if (isDeleteMode) {
				view = inflater.inflate(R.layout.reminder_list_item_delete, null);
			} else {
				view = inflater.inflate(R.layout.reminder_list_item, null);
			}

			final Reminder reminder = getItem(position);
			if(reminder != null){
				((TextView)view.findViewById(R.id.reminder_item_text)).setText(reminder.getReminderText());
				((TextView)view.findViewById(R.id.reminder_item_misri_text)).setText(DateUtil.getMisriDateString(reminder.getMisriDay(), reminder.getMisriMonth()));
				((TextView)view.findViewById(R.id.reminder_item_gregorian_text)).setText(DateUtil.getGregorianDateString(reminder.getGregorianDay(), reminder.getGregorianMonth()));
				
				view.setTag(reminder);
				if(isDeleteMode){
					ImageButton deleteButton = view.findViewById(R.id.reminder_list_discard);
					deleteButton.setTag(reminder);
					deleteButton.setOnClickListener(new View.OnClickListener() {
			            @Override
			            public void onClick(View clickedButton) {
							new DeleteReminderUseCase(getApplicationContext()).deleteReminder(reminder);
			            	reminders = (ArrayList)new GetRemindersUseCase(getBaseContext()).getReminders();
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
}

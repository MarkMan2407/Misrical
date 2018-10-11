package com.squizzard.Reminder;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squizzard.Database.DatabaseHelper;
import com.squizzard.MisriCalendar.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ReminderDisplay extends ActionBarActivity{
	public static final String REMINDER_ID = "reminder_id";
	private DatabaseHelper databaseHelper;
	private int reminderId = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		reminderId = getIntent().getIntExtra(REMINDER_ID, -1);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		updateReminder();	
	}
	
	@Override
	public void onResume(){
		super.onResume();
		updateReminder();
	}
	
	private void updateReminder(){
		if(reminderId == -1){
			finish();
		}else{
			Reminder reminder = getHelper().getReminder(reminderId);
			if(reminder != null){
				setContentView(R.layout.reminder_display);
				((TextView)findViewById(R.id.reminder_display_title)).setText(reminder.getReminderText());
				((TextView)findViewById(R.id.reminder_display_misri_text)).setText(reminder.getMisriDateText());
				((TextView)findViewById(R.id.reminder_display_gregorian_text)).setText(reminder.getGregorianDateText());
			}else{
				finish();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_reminder_display, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home: 
    		onBackPressed();
    		return true;
		case R.id.reminder_display_discard:
			AlertDialog.Builder builder = new AlertDialog.Builder(ReminderDisplay.this);
			builder.setMessage(R.string.reminder_delete_confirm);
			builder.setNegativeButton(getString(R.string.word_cancel), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		              dialog.dismiss();
		           }
		       });
			builder.setPositiveButton(getString(R.string.word_ok), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   getHelper().deleteReminder(reminderId);        
		        	   finish();
		           }
		       });
			builder.show();
			break;
		case R.id.reminder_display_edit:
			Intent addReminderIntent = new Intent(getApplicationContext(), ReminderAdd.class);
			addReminderIntent.putExtra(REMINDER_ID, reminderId);
			startActivity(addReminderIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	protected DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}
	
}

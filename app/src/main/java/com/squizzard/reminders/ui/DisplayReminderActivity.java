package com.squizzard.reminders.ui;

import com.squizzard.data.DeleteReminderUseCase;
import com.squizzard.data.GetRemindersUseCase;
import com.squizzard.MisriCalendar.R;
import com.squizzard.reminders.model.Reminder;
import com.squizzard.util.DateUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class DisplayReminderActivity extends AppCompatActivity{
	public static final String REMINDER_ID = "reminder_id";
	private int reminderId = -1;
	private Reminder reminder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		reminderId = getIntent().getIntExtra(REMINDER_ID, -1);
		if (reminderId != -1) {
			reminder = new GetRemindersUseCase(getApplicationContext()).getReminder(reminderId);
		} else {
			finish();
		}
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		updateReminder();	
	}
	
	@Override
	public void onResume(){
		super.onResume();
		updateReminder();
	}
	
	private void updateReminder() {
		if (reminder != null) {
			setContentView(R.layout.reminder_display);
			((TextView) findViewById(R.id.reminder_display_title)).setText(reminder.getReminderText());
			((TextView) findViewById(R.id.reminder_display_misri_text)).setText(DateUtil.getMisriDateString(reminder.getMisriDay(), reminder.getMisriMonth()));
			((TextView) findViewById(R.id.reminder_display_gregorian_text)).setText(DateUtil.getGregorianDateString(reminder.getGregorianDay(), reminder.getGregorianMonth()));
		} else {
			finish();
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
			AlertDialog.Builder builder = new AlertDialog.Builder(DisplayReminderActivity.this);
			builder.setMessage(R.string.reminder_delete_confirm);
			builder.setNegativeButton(getString(R.string.word_cancel), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		              dialog.dismiss();
		           }
		       });
			builder.setPositiveButton(getString(R.string.word_ok), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   new DeleteReminderUseCase(getApplicationContext()).deleteReminder(reminder);
		        	   finish();
		           }
		       });
			builder.show();
			break;
		case R.id.reminder_display_edit:
			Intent addReminderIntent = new Intent(getApplicationContext(), AddReminderActivity.class);
			addReminderIntent.putExtra(REMINDER_ID, reminderId);
			startActivity(addReminderIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}

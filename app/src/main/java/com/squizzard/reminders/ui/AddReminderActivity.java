package com.squizzard.reminders.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squizzard.data.GetRemindersUseCase;
import com.squizzard.converter.ui.DatePickerFragment;
import com.squizzard.converter.ui.MisiriDatePickerFragment;
import com.squizzard.converter.model.Misri;
import com.squizzard.MisriCalendar.R;
import com.squizzard.data.SaveReminderUseCase;
import com.squizzard.reminders.model.Reminder;
import com.squizzard.util.DateUtil;

public class AddReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

	private static final char TYPE_GREGORIAN = 'G';
	private static final char TYPE_MISRI = 'M';

	private EditText etReminder;
	private FragmentManager fragmentManager;
	private RadioButton rbGregorian;
	private RadioButton rbMisri;
	private TextView tvGregorian;
	private TextView tvMisri;
	private Misri dateConverter;
	private int saveGregorianDay=0;
	private int saveGregorianMonth=0;
	private int saveMisriDay=0;
	private int saveMisriMonth=0;
	private int reminderId = -1;
	private Reminder savedReminder = null;
	private LinearLayout dateButtonRow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder_add);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		etReminder = findViewById(R.id.reminder_add_title);
		Button btnSetMisri = findViewById(R.id.reminder_add_misri_set);
		Button btnSetGregorian = findViewById(R.id.reminder_add_gregorian_set);
		btnSetMisri.setOnClickListener(buttonClickListener);
		btnSetGregorian.setOnClickListener(buttonClickListener);
		rbGregorian = findViewById(R.id.gregorian_radio_button);
		rbGregorian.setChecked(false);
		rbMisri = findViewById(R.id.misri_radio_button);
		rbMisri.setChecked(false);
		tvGregorian = findViewById(R.id.reminder_add_gregorian_text);
		tvMisri = findViewById(R.id.reminder_add_misri_text);
		dateButtonRow = findViewById(R.id.reminder_add_button_row);
		
		reminderId = getIntent().getIntExtra(DisplayReminderActivity.REMINDER_ID, -1);
		if(reminderId != -1){
			savedReminder = new GetRemindersUseCase(getApplicationContext()).getReminder(reminderId);
			if(savedReminder != null){
				etReminder.setText(savedReminder.getReminderText());
				tvGregorian.setText(DateUtil.getGregorianDateString(savedReminder.getGregorianDay(), savedReminder.getGregorianMonth()));
				tvMisri.setText(DateUtil.getMisriDateString(savedReminder.getMisriDay(), savedReminder.getMisriMonth()));
				saveGregorianDay = savedReminder.getGregorianDay();
				saveGregorianMonth = savedReminder.getGregorianMonth();
				saveMisriDay = savedReminder.getMisriDay();
				saveMisriMonth = savedReminder.getMisriMonth();
				if(savedReminder.getType() == TYPE_GREGORIAN){
					rbGregorian.setChecked(true);
				}else{
					rbMisri.setChecked(true);
				}
			}
		}

		dateConverter = new Misri();
		fragmentManager = getSupportFragmentManager();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_individual_reminder, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
        	case android.R.id.home: 
        		onBackPressed();
        		return true;
	        case R.id.reminder_save:
	        	if (etReminder.getText().toString().trim().length() <= 0){
	        		Animation shake = AnimationUtils.loadAnimation(AddReminderActivity.this, R.anim.shake);
	        		etReminder.startAnimation(shake);
				} else if(saveGregorianDay == 0 || saveGregorianMonth == 0 || saveMisriDay ==  0 || saveMisriMonth == 0){
					Animation shake = AnimationUtils.loadAnimation(AddReminderActivity.this, R.anim.shake);
					dateButtonRow.startAnimation(shake);
				} else {
					char type = TYPE_GREGORIAN;
					if(rbMisri.isChecked()){
						type = TYPE_MISRI;
					}
					if (reminderId == -1) {
						Reminder reminder = new Reminder(etReminder.getText().toString(),
								saveGregorianDay,
								saveGregorianMonth,
								saveMisriDay,
								saveMisriMonth,
								0,
								0,
								type);
						new SaveReminderUseCase(getApplicationContext()).saveReminder(reminder);
					} else {
						savedReminder.setReminderText(etReminder.getText().toString());
						savedReminder.setGregorianDay(saveGregorianDay);
						savedReminder.setGregorianMonth(saveGregorianMonth);
						savedReminder.setMisriDay(saveMisriDay);
						savedReminder.setMisriMonth(saveMisriMonth);
						savedReminder.setType(type);
						new SaveReminderUseCase(getApplicationContext()).saveReminder(savedReminder);
					}
					
				    finish();
				}
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    
	}

	private OnClickListener buttonClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.reminder_add_gregorian_set:
				new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");
				break;
			case R.id.reminder_add_misri_set:
				MisiriDatePickerFragment misriDatePicker = MisiriDatePickerFragment.newInstance();
				misriDatePicker.show(fragmentManager, "misri_date_picker_fragment");
				break;
			}	
		}
	};


	@Override
	public void onDateSet(DatePicker datePicker, int year, int month, int day) {
		//get the date back and display both date strings based on a prior conversion to Gregorian
		if(datePicker != null){//this is a standard date picker
			rbGregorian.setChecked(true);
			tvGregorian.setText(DateUtil.getGregorianDateString(day, month));
			int [] misriArray = dateConverter.getMisriDate(day, month, year);
			tvMisri.setText(DateUtil.getMisriDateString(misriArray[0], misriArray[1]));
			saveGregorianDay = day;
			saveGregorianMonth = month+1;
			saveMisriDay = misriArray[0];
			saveMisriMonth = misriArray[1];
		}
		else{
			rbMisri.setChecked(true);
			tvMisri.setText(DateUtil.getMisriDateString(day, month));
			int[] gregorianDateArray =  dateConverter.getGregorianDate(day, month-1, year);
			tvGregorian.setText(DateUtil.getGregorianDateString(gregorianDateArray[0], gregorianDateArray[1]));
			saveGregorianDay = gregorianDateArray[0];
			saveGregorianMonth = gregorianDateArray[1];
			saveMisriDay = day;
			saveMisriMonth = month;
		}
	}
}

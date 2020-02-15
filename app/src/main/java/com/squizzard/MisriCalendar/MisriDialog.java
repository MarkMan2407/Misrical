package com.squizzard.MisriCalendar;

import com.squizzard.util.DateUtil;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;


public class MisriDialog extends Dialog implements OnClickListener{
	private EditText dayText;
	private EditText monthText;
	private EditText yearText;
	private int monthCode;
	private Integer dayCode;//30,29,30,29
	private Integer yearCode;
	private Button dayMinusButton;
	private Button dayPlusButton;
	private Button monthMinusButton;
	private Button monthPlusButton;
	private Button yearMinusButton;
	private Button yearPlusButton;
	private Button setButton;
	private Button cancelButton;
	Misri misriConverter;
	private static final int MIN_DAY = 1;

	public MisriDialog(Context context, Misri m) {
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.misri_datepicker);
		misriConverter = m;
		
		dayMinusButton = findViewById(R.id.dayMinus);
		dayMinusButton.setOnClickListener(this);
		dayPlusButton = findViewById(R.id.dayPlus);
		dayPlusButton.setOnClickListener(this);
		monthMinusButton = findViewById(R.id.monthMinus);
		monthMinusButton.setOnClickListener(this);
		monthPlusButton = findViewById(R.id.monthPlus);
		monthPlusButton.setOnClickListener(this);
		yearMinusButton = findViewById(R.id.yearMinus);
		yearMinusButton.setOnClickListener(this);
		yearPlusButton = findViewById(R.id.yearPlus);
		yearPlusButton.setOnClickListener(this);
		setButton = findViewById(R.id.misriSet);
		setButton.setOnClickListener(this);
		cancelButton = findViewById(R.id.misriCancel);
		cancelButton.setOnClickListener(this);
		
		dayText = findViewById(R.id.dayText);
		monthText = findViewById(R.id.monthText);
		yearText = findViewById(R.id.yearText);
		//get todays gregorian date and convert it to the 
		dayCode = Integer.valueOf(m.getTodayMisriDay());
		dayText.setText(dayCode.toString().trim());
		monthText.setText(m.getTodayMisriMonth().trim());
		monthCode = m.getTodayMisriMonthCode();
		yearCode = Integer.valueOf(m.getTodayMisriYear());
		yearText.setText(yearCode.toString().trim());
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.dayMinus:
			subtractDay();
			dayText.setText(dayCode.toString().trim());
			break;
		case R.id.dayPlus:
			addDay();
			dayText.setText(dayCode.toString().trim());
			break;
		case R.id.monthMinus:
			subtractMonth();			
			monthText.setText(DateUtil.getMisriMonth(monthCode).trim());
			break;
		case R.id.monthPlus:
			addMonth();
			monthText.setText(DateUtil.getMisriMonth(monthCode).trim());
			break;
		case R.id.yearMinus:
			subtractYear();
			yearText.setText(yearCode.toString().trim());
			break;
		case R.id.yearPlus:
			addYear();
			yearText.setText(yearCode.toString().trim());
			break;
		case R.id.misriSet:
			validate();
			setDate();
			break;
		case R.id.misriCancel:
			this.dismiss();
			break;
		}
		
	}

	private void validate(){
	String dayValue = dayText.getText().toString();
	String yearValue = yearText.getText().toString();
	dayCode = Integer.parseInt(dayValue);
	yearCode = Integer.parseInt(yearValue);
		if(dayCode<1){
			dayCode=1;
			dayText.setText(dayCode.toString());
		//	return false;
		}
		if(dayCode>29){
			if(monthCode%2==1){
				dayCode=30;
				dayText.setText(dayCode.toString());
			//	return false;
			}else{
				dayCode=29;
				dayText.setText(dayCode.toString());
			}
		}
		if(yearCode>1499){
			yearCode=1499;
			yearText.setText(yearCode.toString());
		//	return false;
		}
		if(yearCode<1){
			yearCode=1;
			yearText.setText(yearCode.toString());
		//	return false;
		}
		
		
	//	return true;
	}
	
	private void setDate(){
		int[] gregorianDateArray =  misriConverter.getGregorianDate(dayCode, monthCode-1, yearCode);
		//int[] misriDateArray = misriConverter.getMisriDate(dayCode, monthCode-1, yearCode);
		//CalendarConvert.misriText.setText(DateUtil.getMisriDateString(misriDateArray[0], misriDateArray[1], misriDateArray[2]));
		CalendarConvert.misriText.setText(DateUtil.getMisriDateString(dayCode, monthCode, yearCode));
		misriConverter.setEvent(monthCode, dayCode);
		CalendarConvert.eventText.setText(misriConverter.getTodayEvent());
		CalendarConvert.gregorianText.setText(DateUtil.getGregorianDateString(gregorianDateArray[0], gregorianDateArray[1], gregorianDateArray[2]));
		this.dismiss();
	}

	private void addYear() {
		if(yearCode==2100){
			yearCode=1900;
		}
		yearCode++;
	}

	private void subtractYear() {
		if(yearCode==1900){
			yearCode=2100;
		}
		yearCode--;
		
	}

	private void subtractDay() {
		if(dayCode==MIN_DAY){
			if(monthCode%2==1){
				dayCode=30;
			}else{
				dayCode=29;
			}
		}else dayCode--;
		
	}

	private void addDay() {
        if(dayCode==29 && monthCode%2==0){//odd months have 30 days
			dayCode=1;
		}
        else if(dayCode==30){
        	dayCode=1;
        }
		else{
		dayCode++;
		}
	}

	private void addMonth() {
		if(monthCode==12){
			monthCode=1;
		}else{
			monthCode++;
		}
		
	}

	private void subtractMonth() {
		if(monthCode==1){
			monthCode=12;
		}else{
			monthCode--;
		}
			
	}
	
	public int getDayCode(){
		return 1;
	}
}

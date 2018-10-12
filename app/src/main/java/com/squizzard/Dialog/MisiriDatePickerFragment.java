package com.squizzard.Dialog;

import com.squizzard.MisriCalendar.Misri;
import com.squizzard.MisriCalendar.R;
import com.squizzard.util.DateUtil;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View.OnClickListener;

public class MisiriDatePickerFragment extends DialogFragment implements OnClickListener{
	
	private EditText dayText;
	private EditText monthText;
	private EditText yearText;
	private int monthCode;
	private int dayCode;//30,29,30,29
	private int yearCode;
	private Button dayMinusButton;
	private Button dayPlusButton;
	private Button monthMinusButton;
	private Button monthPlusButton;
	private Button yearMinusButton;
	private Button yearPlusButton;
	private Button setButton;
	private Misri misriConverter;
	private View view;
	
	public static MisiriDatePickerFragment newInstance(){
		MisiriDatePickerFragment misriDatePicker = new MisiriDatePickerFragment();
		return misriDatePicker;
	}
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
            Bundle savedInstanceState) {
    	view = inflater.inflate(R.layout.misri_datepicker, viewGroup);

        getDialog().setTitle("Set Date");
        misriConverter = new Misri();
        
		dayMinusButton = (Button)view.findViewById(R.id.dayMinus);
		dayMinusButton.setOnClickListener(this);
		dayPlusButton = (Button)view.findViewById(R.id.dayPlus);
		dayPlusButton.setOnClickListener(this);
		monthMinusButton = (Button)view.findViewById(R.id.monthMinus);
		monthMinusButton.setOnClickListener(this);
		monthPlusButton = (Button)view.findViewById(R.id.monthPlus);
		monthPlusButton.setOnClickListener(this);
		yearMinusButton = (Button)view.findViewById(R.id.yearMinus);
		yearMinusButton.setOnClickListener(this);
		yearPlusButton = (Button)view.findViewById(R.id.yearPlus);
		yearPlusButton.setOnClickListener(this);
		setButton = (Button)view.findViewById(R.id.misriSet);
		setButton.setOnClickListener(this);
		
		dayText = (EditText)view.findViewById(R.id.dayText);
		monthText = (EditText)view.findViewById(R.id.monthText);
		yearText = (EditText)view.findViewById(R.id.yearText);
		dayCode = Integer.valueOf(misriConverter.getTodayMisriDay());
		dayText.setText(String.valueOf(dayCode));
		monthText.setText(misriConverter.getTodayMisriMonth().trim());
		monthCode = misriConverter.getTodayMisriMonthCode();
		yearCode = Integer.valueOf(misriConverter.getTodayMisriYear());
		yearText.setText(String.valueOf(yearCode));

        return view;
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.dayMinus:
			subtractDay();
			dayText.setText(String.valueOf(dayCode));
			break;
		case R.id.dayPlus:
			addDay();
			dayText.setText(String.valueOf(dayCode));
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
			yearText.setText(String.valueOf(yearCode));
			break;
		case R.id.yearPlus:
			addYear();
			yearText.setText(String.valueOf(yearCode));
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
			dayText.setText(String.valueOf(dayCode));
		}
		if(dayCode>29){
			if(monthCode%2==1){
				dayCode=30;
				dayText.setText(String.valueOf(dayCode));
			}else{
				dayCode=29;
				dayText.setText(String.valueOf(dayCode));
			}
		}
		if(yearCode>1499){
			yearCode=1499;
			yearText.setText(String.valueOf(yearCode));
		}
		if(yearCode<1){
			yearCode=1;
			yearText.setText(String.valueOf(yearCode));
		}
	}
	
	private void setDate(){
		DatePickerDialog.OnDateSetListener activity = (DatePickerDialog.OnDateSetListener) getActivity();
		activity.onDateSet(null, yearCode, monthCode, dayCode);
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
		if(dayCode==1){//1 being the minimum value a day can take
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
}

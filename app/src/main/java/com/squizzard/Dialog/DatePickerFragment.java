package com.squizzard.Dialog;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	public static final int DIALOG_ID_GREGORIAN_PICKER = 1;

	public DatePickerFragment() {

	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		DatePickerDialog.OnDateSetListener activity = (DatePickerDialog.OnDateSetListener) getActivity();
		view.setId(DIALOG_ID_GREGORIAN_PICKER);
		activity.onDateSet(view, year, month, day);
        this.dismiss();
	}
}

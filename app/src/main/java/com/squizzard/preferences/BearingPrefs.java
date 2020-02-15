package com.squizzard.preferences;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squizzard.Attributes;
import com.squizzard.MisriCalendar.R;
import com.squizzard.broadcast.AlarmCoordinator;
import com.squizzard.data.DatabaseHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.TextView;

public class BearingPrefs extends PreferenceActivity{

	public enum BearingOptions{ON_TOUCH, ALWAYS_ON, OFF}
	private DatabaseHelper databaseHelper = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTheme(R.style.CustomActionBarTheme);
		setContentView(R.layout.bearing_settings_info);
		
		addPreferencesFromResource(R.xml.settings);
		TextView providerText = findViewById(R.id.providerValue);
		TextView meccaBearingText = findViewById(R.id.meccaBearingValue);
		providerText.setText(getIntent().getStringExtra("PROVIDER"));
		meccaBearingText.setText(getIntent().getStringExtra("BEARING_TO_MECCA"));
		Preference alertPreference = getPreferenceManager().findPreference(Attributes.MIQAATS_ALERT_PREFERENCE);
		Preference locationPreference = getPreferenceManager().findPreference(Attributes.LOCATION_SETTINGS_PREFERENCE);

		alertPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				boolean alertsEnabled = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(Attributes.MIQAATS_ALERT_PREFERENCE, false);
				if(alertsEnabled){
					AlarmCoordinator.turnOnAlarms(getApplicationContext());
				}
				else{
					AlarmCoordinator.turnOffAlarms(getApplicationContext());
				}
				return true;
			}
		});
		
		locationPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
	        public boolean onPreferenceClick(Preference preference) {
	            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	            startActivity(viewIntent);

	            return true;
	        }
	    });
	}

	public static BearingOptions getBearingMode(Context context){
		String prefString = PreferenceManager.getDefaultSharedPreferences(context).getString("listPref", "1");//Default to first in list
		int i = Integer.valueOf(prefString);
		switch(i) {
		case 1:
			return BearingOptions.ALWAYS_ON;
		case 2:
			return BearingOptions.ON_TOUCH;
		case 3: 
			return BearingOptions.OFF;
			default: return BearingOptions.ALWAYS_ON;
		}
	}

	public static boolean miqaatAlertsEnabled(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.miqaat_alert_preference), false);
	}
	
	protected DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
		}
		return databaseHelper;
	}
}

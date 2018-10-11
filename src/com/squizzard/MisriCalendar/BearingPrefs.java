package com.squizzard.MisriCalendar;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squizzard.Database.DatabaseHelper;

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

	public enum BearingOptions{ON_TOUCH, ALWAYS_ON, OFF};
	private TextView meccaBearingText;
	private TextView providerText;
	private Preference alertPreference;
	private Preference locationPreference;
	private DatabaseHelper databaseHelper = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTheme(R.style.CustomActionBarTheme);
		setContentView(R.layout.bearinginfo);
		
		addPreferencesFromResource(R.xml.settings);
		providerText = (TextView) findViewById(R.id.provider);
		meccaBearingText = (TextView) findViewById(R.id.meccaBearing);
		providerText.setText(getIntent().getStringExtra("PROVIDER"));
		meccaBearingText.setText(getIntent().getStringExtra("BEARING_TO_MECCA"));
		alertPreference = getPreferenceManager().findPreference(Attributes.MIQAATS_ALERT_PREFERENCE);
		locationPreference = getPreferenceManager().findPreference(Attributes.LOCATION_SETTINGS_PREFERENCE);

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
		BearingOptions retVal = null;
		switch(i){
		case 1:
			retVal = BearingOptions.ALWAYS_ON;
			break;
		case 2:
			retVal =  BearingOptions.ON_TOUCH;
			break;
		case 3: 
			retVal =  BearingOptions.OFF;
			break;
		}
		return retVal;
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

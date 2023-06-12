package com.squizzard.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.TextView;

import com.squizzard.Attributes;
import com.squizzard.analytics.AnalyticsHelper;
import com.squizzard.broadcast.AlarmCoordinator;
import com.squizzard.misriCalendar.R;

public class SettingsActivity extends PreferenceActivity {

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

		if (alertPreference != null) {
			alertPreference.setOnPreferenceClickListener(preference -> {
				boolean alertsEnabled = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(Attributes.MIQAATS_ALERT_PREFERENCE, false);
				if (alertsEnabled) {
					AnalyticsHelper.sendEvent("alerts_enable");
					AlarmCoordinator.turnOnAlarms(getApplicationContext());
				} else {
					AnalyticsHelper.sendEvent("alerts_disable");
					AlarmCoordinator.turnOffAlarms(getApplicationContext());
				}
				return true;
			});
		}

		if (locationPreference != null) {
			locationPreference.setOnPreferenceClickListener(preference -> {
				Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(viewIntent);
				return true;
			});
		}
	}
}

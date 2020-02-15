package com.squizzard.MisriCalendar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.squizzard.util.Utility;

public class About extends AppCompatActivity implements OnClickListener{

	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
		findViewById(R.id.emailButton).setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    onBackPressed();
	    return true;
	}

	public void onClick(View v) {
		if (v.getId() == R.id.emailButton){

			String messageString = "\n\n\n\n OS Version: " + System.getProperty("os.version");
			messageString += "\n OS API Level: " + Build.VERSION.RELEASE;
			messageString += "\n Device: " + android.os.Build.DEVICE;
			messageString += "\n Model : " + android.os.Build.MODEL;
			messageString += "\n Display: " + android.os.Build.DISPLAY;
			messageString += "\n Manufacturer: " + android.os.Build.MANUFACTURER;
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.ouikka_email)});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    "MisriCal "+ Utility.getVersion(getApplicationContext()));
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, messageString);
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		}	
	}
}

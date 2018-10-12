package com.squizzard.MisriCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class About extends ActionBarActivity implements OnClickListener{

	private TextView emailButton;
	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		emailButton = (TextView) findViewById(R.id.emailButton);
		emailButton.setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
	
	@SuppressWarnings("deprecation")
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.emailButton:
			String messageString = "\n\n\n\n OS Version: " + System.getProperty("os.version");
			messageString += "\n OS API Level: " + android.os.Build.VERSION.SDK;
			messageString += "\n Device: " + android.os.Build.DEVICE;
			messageString += "\n Model : " + android.os.Build.MODEL;
			messageString += "\n Display: " + android.os.Build.DISPLAY;
			messageString += "\n Manufacturer: " + android.os.Build.MANUFACTURER;
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.ouikka_email)});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MisriCal "+ getString(R.string.app_version));
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, messageString);
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			break;
		}	
	}
}

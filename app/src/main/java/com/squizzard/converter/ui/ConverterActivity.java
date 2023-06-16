package com.squizzard.converter.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squizzard.about.AboutActivity;
import com.squizzard.analytics.AnalyticsHelper;
import com.squizzard.converter.model.Misri;
import com.squizzard.miqaatList.MiqaatListActivity;
import com.squizzard.misriCalendar.R;
import com.squizzard.reminders.ui.activity.ReminderListActivity;
import com.squizzard.settings.SettingsActivity;
import com.squizzard.util.DateUtil;

import java.util.Calendar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ConverterActivity extends AppCompatActivity implements OnClickListener, SensorEventListener, LocationListener {//make the location listener into an inner class

	private static final int DATE_DIALOG_ID = 0;
	private static final int MISRI_DIALOG_ID = 1;
	private final String CALENDAR_STATE = "CALENDAR_STATE";
	static Dialog returnDialog;
	private final TextView[] weekdayButtons = new TextView[7];//monButton, tueButton, wedButton, thuButton, friButton, satButton, sunButton;
	static TextView misriText;
	static TextView eventText;
	static TextView gregorianText;
	private ImageView arrowImageNorth;
	private ImageView arrowImageMecca;
	private Misri dateConverter;
	private Calendar c = Calendar.getInstance();
	private float startRotateNorth;
	private float endRotateNorth;
	private float startRotateMecca;
	private float endRotateMecca;
	private Location location;
	private LocationManager locMgr;
	private Sensor magneticSensor;
	private Sensor accelerometerSensor;
	private SensorManager sensorManager;
	private float[] accelerometerValues;
	private float[] magneticValues;
	private float declination;
	private Location mecca;
	private String bearingToMeccaString;
	private String providerString;
	private final int LOCATION_PERMISSION_REQUEST = 1011;
	private NetworkInfo network;
	private NetworkInfo wifi;

	// Declare the launcher at the top of your Activity/Fragment:
	private final ActivityResultLauncher<String> requestPermissionLauncher =
			registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
				if (isGranted) {
					// FCM SDK (and your app) can post notifications.
				} else {
					// TODO: Inform user that that your app will not show notifications.
				}
			});

	private void askNotificationPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
					PackageManager.PERMISSION_GRANTED) {
				// FCM SDK (and your app) can post notifications.
			} else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
				// TODO: display an educational UI explaining to the user the features that will be enabled
				//       by them granting the POST_NOTIFICATION permission. This UI should provide the user
				//       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
				//       If the user selects "No thanks," allow the user to continue without notifications.
			} else {
				// Directly ask for the permission
				requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			c = (Calendar) savedInstanceState.getSerializable(CALENDAR_STATE);
		}
		setContentView(R.layout.main);
		misriText = findViewById(R.id.convertedDate);
		eventText = findViewById(R.id.eventText);
		gregorianText = findViewById(R.id.dateGregorian);
		TextView setGregorianButton = findViewById(R.id.setGregorianButton);
		TextView setMisriButton = findViewById(R.id.setMisriButton);
		setGregorianButton.setOnClickListener(this);
		setMisriButton.setOnClickListener(this);
		TextView setTodayButton = findViewById(R.id.todayButton);
		setTodayButton.setOnClickListener(this);
		View dayPlusButton = findViewById(R.id.dayPlusButton);
		dayPlusButton.setOnClickListener(this);
		View dayMinusButton = findViewById(R.id.dayMinusButton);
		dayMinusButton.setOnClickListener(this);
		arrowImageNorth = findViewById(R.id.arrow_icon_north);
		arrowImageMecca = findViewById(R.id.arrow_icon_mecca);
		dateConverter = new Misri();
		mecca = new Location("mecca");
		mecca.setLatitude(21.427);
		mecca.setLongitude(39.814);
		weekdayButtons[0] = findViewById(R.id.sunButton);
		weekdayButtons[1] = findViewById(R.id.monButton);
		weekdayButtons[2] = findViewById(R.id.tueButton);
		weekdayButtons[3] = findViewById(R.id.wedButton);
		weekdayButtons[4] = findViewById(R.id.thuButton);
		weekdayButtons[5] = findViewById(R.id.friButton);
		weekdayButtons[6] = findViewById(R.id.satButton);


		askNotificationPermission();
		FirebaseMessaging.getInstance().getToken()
				.addOnCompleteListener(new OnCompleteListener<String>() {
					@Override
					public void onComplete(@NonNull Task<String> task) {
						if (!task.isSuccessful()) {
							Log.w("PUSH", "Fetching FCM registration token failed", task.getException());
							return;
						}

						// Get new FCM registration token
						String token = task.getResult();

						// Log and toast

						Log.d("PUSH", "token " + token);
						//Toast.makeText(ConverterActivity.this, token, Toast.LENGTH_SHORT).show();
					}
				});

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		Criteria locationCriteria = new Criteria();
		locationCriteria.setBearingRequired(true);
		locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

		ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		network = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		updateDisplay();

		new ReminderReporter(getApplicationContext()).reportAllReminders();

		startRotateNorth = endRotateNorth = startRotateMecca = endRotateMecca = 0;
	}

	private void highLightDay(Calendar c2) {
		int today = c2.get(Calendar.DAY_OF_WEEK);//Sunday is 1
		for (int d = 0; d < 7; d++) {
			if (d == today - 1) {
				weekdayButtons[d].setBackgroundDrawable(getResources().getDrawable(R.drawable.day_selected_button));
			} else weekdayButtons[d].setBackgroundColor(getResources().getColor(R.color.black));
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull  Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(CALENDAR_STATE, c);
	}

	private void checkPermissions() {
		if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					LOCATION_PERMISSION_REQUEST);
		} else {
			doLocationProcessing();
		}
	}

	@Override
	public void onResume(){
		super.onResume();
		//point arrows north  endRotationNorth/Mecca=0
		//point the arrow north: endRotationNorth/Mecca=0
		checkPermissions();
		makeRotation(arrowImageNorth, false);
		makeRotation(arrowImageMecca, false);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == LOCATION_PERMISSION_REQUEST) {
			if (grantResults.length > 0
					&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				doLocationProcessing();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "Mecca pointer disabled. Go to the settings menu to enable location services.", Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}

	@SuppressLint("MissingPermission")
	private void doLocationProcessing() {

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);

		if (locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
		}

		providerString = "No Location Available";

		if (location == null && network != null) {
			if (network.isConnected()) {
				location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				providerString = network.getExtraInfo();
				if (providerString.equals("No Provider Connection") || providerString.equals("")) {
					providerString = "NETWORK";
				}
			}
		}

		if (location == null && locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				providerString = "GPS";
			}
		}

		if (location == null && wifi.isConnected()) {
			providerString = "WIFI";
			location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		if (location != null) {
			//arrowImageMecca.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.DARKEN);
			getGeomagneticField();
			bearingToMeccaString = Float.toString(Math.round(location.bearingTo(mecca)));
		} else {
			bearingToMeccaString = "Unavailable";
			Toast toast = Toast.makeText(getApplicationContext(), "Mecca pointer disabled. Go to the settings menu to enable Network/WIFI/GPS location services.", Toast.LENGTH_LONG);
			toast.show();
			LightingColorFilter lcf = new LightingColorFilter(0x44555555, 0);
			arrowImageMecca.setColorFilter(lcf);
		}
	}

	public void onClick(View v) {
		switch(v.getId()){
			case R.id.setGregorianButton:
				AnalyticsHelper.sendEvent("set_gregorian");
				showDialog(DATE_DIALOG_ID);
				break;

			case R.id.setMisriButton:
				AnalyticsHelper.sendEvent("set_misri");
				showDialog(MISRI_DIALOG_ID);
				break;

			case R.id.dayPlusButton:
				AnalyticsHelper.sendEvent("set_day_plus");
				c.add(Calendar.DAY_OF_MONTH, 1);
				updateDisplay();
				break;

			case R.id.dayMinusButton:
				AnalyticsHelper.sendEvent("set_day_minus");
				c.add(Calendar.DAY_OF_MONTH, -1);
				updateDisplay();
				break;

			case R.id.todayButton:
				AnalyticsHelper.sendEvent("set_today");
				c = Calendar.getInstance();
				updateDisplay();
		}
	}

	private void updateDisplay(){
		setGregorianText(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		setMisriText(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		highLightDay(c);
	}

	private void setMisriText(int dayOfMonth, int monthOfYear, int year) {
		int[] dateArray = dateConverter.getMisriDate(dayOfMonth, monthOfYear, year);
		misriText.setText(DateUtil.getMisriDateString(dateArray[0], dateArray[1], dateArray[2]));
		eventText.setText(dateConverter.getTodayEvent());
	}

	private void setGregorianText(int dayOfMonth, int monthOfYear, int year){
		gregorianText.setText(DateUtil.getGregorianDateString(dayOfMonth, monthOfYear, year));
		eventText.setText(dateConverter.getTodayEvent());
	}

	private final DatePickerDialog.OnDateSetListener mDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
		if((year>1900) && (year<2077)){
			c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			c.set(Calendar.MONTH, monthOfYear);
			c.set(Calendar.YEAR, year);
			highLightDay(c);
			setGregorianText(dayOfMonth, monthOfYear, year);
			setMisriText(dayOfMonth, monthOfYear, year);
		}
		else{
			Toast toast = Toast.makeText(getApplicationContext(), "1900 < YEAR < 2077", Toast.LENGTH_LONG);
			toast.show();
		}
	};

	protected Dialog onCreateDialog(int id){
		switch(id){
			case DATE_DIALOG_ID:
				c = Calendar.getInstance();
				returnDialog =  new DatePickerDialog(this, mDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				break;
			case MISRI_DIALOG_ID:
				returnDialog = new MisriDialog(this, dateConverter);
				returnDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
					@Override
					public void onDismiss(DialogInterface dialog) {
						if(dateConverter.getConvertedGregorianDay() != 0 && dateConverter.getConvertedGregorianMonth() != 0 && dateConverter.getConvertedGregorianYear() != 0){
							c.set(Calendar.DAY_OF_MONTH, dateConverter.getConvertedGregorianDay());
							c.set(Calendar.MONTH, dateConverter.getConvertedGregorianMonth());
							c.set(Calendar.YEAR, dateConverter.getConvertedGregorianYear());
						}
						highLightDay(c);
					}
				});
				break;
		}
		return returnDialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.miqaat:
				AnalyticsHelper.sendEvent("miqaat_menu");
				startActivity(new Intent(this, MiqaatListActivity.class));
				break;
			case R.id.about:
				AnalyticsHelper.sendEvent("about_menu");
				startActivity(new Intent(this, AboutActivity.class));
				break;
			case R.id.bearings:
				AnalyticsHelper.sendEvent("bearings_menu");
				Intent bearingOptionIntent = new Intent(this, SettingsActivity.class);
				bearingOptionIntent.putExtra("PROVIDER", providerString);
				bearingOptionIntent.putExtra("BEARING_TO_MECCA", bearingToMeccaString);
				startActivity(bearingOptionIntent);
				break;
			case R.id.share:
				AnalyticsHelper.sendEvent("share_menu");
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("text/*");
				intent.putExtra(android.content.Intent.EXTRA_TEXT, "Android app for converting between Misri and Gregorian dates: \nhttps://market.android.com/details?id=com.squizzard.MisriCalendar");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MisriCal for Android");
				startActivity(Intent.createChooser(intent, "Share using..."));
				break;
			case R.id.reminders:
				AnalyticsHelper.sendEvent("reminders_menu");
				Intent reminderIntent = new Intent(this, ReminderListActivity.class);
				startActivity(reminderIntent);
				break;
			default:
		}
		return false;
	}

	private void makeRotation(ImageView image, boolean defaultNorth) {
		float[] R = new float[16];
		float[] I = new float[16];
		float[] remappedMatrix = new float[16];
		float[] actual_orientation = new float[3];
		try {
			if (magneticValues != null && accelerometerValues != null) {
				SensorManager.getRotationMatrix(R, I, accelerometerValues, magneticValues);
				Display display = getWindowManager().getDefaultDisplay();

				int phoneRotation = display.getOrientation();

				if (phoneRotation == 0) {//portrait
					SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_X, SensorManager.AXIS_Y, remappedMatrix);
				}
				if (phoneRotation == 1) {//landscape facing left
					SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, remappedMatrix);
				}
				if (phoneRotation == 3) {//landscape facing right
					SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, remappedMatrix);
				}

				SensorManager.getOrientation(remappedMatrix, actual_orientation);
				//values[0]: Azimuth, rotation around the Z axis (0<=azimuth<360). 0 = North, 90 = East, 180 = South, 270 = West
				float azi = (float) Math.toDegrees(actual_orientation[0]);

				if (declination >= 0) {
					azi -= declination;//positive declination means the magnetic field is rotated east that much from true north. if its positive subtract it, negative then add it
				} else azi += declination;//need to do an azimuth conversion.
				int aziDisplay = (int) azi;//for display purposes
				if (aziDisplay < 0) {
					aziDisplay = 180 + (180 + aziDisplay);
				}

				endRotateNorth = -azi;//this is endRotate is north
				if (image.equals(arrowImageNorth)) {
					if (defaultNorth) endRotateNorth = 0;
					RotateAnimation rotateAnimation = new RotateAnimation(startRotateNorth, endRotateNorth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
					rotateAnimation.setDuration(500);
					rotateAnimation.setFillAfter(true);
					image.startAnimation(rotateAnimation);
					startRotateNorth = endRotateNorth;
				}
				if (image.equals(arrowImageMecca)) {
					if (location != null) {
						endRotateMecca = endRotateNorth + location.bearingTo(mecca);
					}
					if (defaultNorth || bearingToMeccaString.equals("Unavailable"))
						endRotateMecca = 0;
					RotateAnimation rotateAnimation = new RotateAnimation(startRotateMecca, endRotateMecca, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
					rotateAnimation.setDuration(500);
					rotateAnimation.setFillAfter(true);
					image.startAnimation(rotateAnimation);
					startRotateMecca = endRotateMecca;
				}
			}
		} catch (Exception e) {
			AnalyticsHelper.sendEvent("Exception in makeRotation() " + e.getMessage());
		}
	}

	@Override
	protected void onPause(){
		super.onPause();
		sensorManager.unregisterListener(this, accelerometerSensor);
		sensorManager.unregisterListener(this, magneticSensor);
		locMgr.removeUpdates(this);
	}

	private void getGeomagneticField() {
		try {
			GeomagneticField geoField = new GeomagneticField(
					Double.valueOf(location.getLatitude()).floatValue(),
					Double.valueOf(location.getLongitude()).floatValue(),
					Double.valueOf(location.getAltitude()).floatValue(),
					System.currentTimeMillis());
			declination = geoField.getDeclination();//positive means the magnetic field is rotated east that much from true north
		} catch (Exception e) {
			AnalyticsHelper.sendEvent("Exception in getGeomagneticField()");
		}
	}

	public void onSensorChanged(SensorEvent event) {

		switch (event.sensor.getType()) {
			case (Sensor.TYPE_ACCELEROMETER):
				accelerometerValues = event.values.clone();
				break;
			case (Sensor.TYPE_MAGNETIC_FIELD):
				magneticValues = event.values.clone();
				break;
		}
		if (magneticValues != null && accelerometerValues != null) {
			makeRotation(arrowImageNorth, false);
			makeRotation(arrowImageMecca, bearingToMeccaString.equals("Unavailable"));
		}
	}

	public void onLocationChanged(Location newLocation) {
		if ( location==null ){//location has become available for the first time
			Toast toast = Toast.makeText(this.getApplicationContext(), "Location established. Mecca pointer active!", Toast.LENGTH_SHORT);
			toast.show();
			arrowImageMecca.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.DARKEN);
		}
		location = newLocation;
		bearingToMeccaString=Float.toString(Math.round(location.bearingTo(mecca)));
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
		providerString = provider;
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//3 Accuracy high 2 low 1 medium 0 unreliable
	}
}
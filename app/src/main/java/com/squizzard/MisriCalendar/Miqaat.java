package com.squizzard.MisriCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Miqaat extends AppCompatActivity {

	final static String[] months = new String[] {"Muharram al-Haraam","Safar al-Muzaffar","Rabi al-Awwal","Rabi al-Aakhar",
		"Jumada al-Ula","Jumada al-Ukhra","Rajab al-Asab","Shaban al-Karim","Ramadan al-Moazzam",
		"Shawwal al-Mukarram","Zilqad al-Haraam","Zilhaj al-Haraam"};

	private MonthListAdapter adapter;
	private ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.miqaat_month_display);
		list = (ListView)findViewById(R.id.month_list);
		list.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View v,int position, long id)
		    { 
				int intItem = (int)id;
				Intent miqaatMonthIntent = new Intent(Miqaat.this, MiqaatMonthView.class);
				miqaatMonthIntent.putExtra("MONTH", intItem);
				startActivity(miqaatMonthIntent);
		    }
		});
		
		adapter = new MonthListAdapter();
		list.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
	
	private class MonthListAdapter extends BaseAdapter {
		public int getCount() {
			return Miqaat.months.length;
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if(row==null){
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(R.layout.miqaat_month_row, parent, false);
			}

			((TextView)row.findViewById(R.id.month_text)).setText(months[position]);
			return row;
		}
	}
}

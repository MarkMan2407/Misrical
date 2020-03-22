package com.squizzard.miqaatList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squizzard.converter.model.Misri;
import com.squizzard.misriCalendar.R;
import com.squizzard.util.DateUtil;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MiqaatMonthActivity extends AppCompatActivity {

	private ArrayList<String> events = new ArrayList<>();
	private ArrayList<String> dates = new ArrayList<>();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.miqaat_event_display);
		int month =  getIntent().getIntExtra("MONTH", 0);
		ListView list = findViewById(R.id.miqaat_list);
		MiqaatListAdapter adapter = new MiqaatListAdapter();
		list.setAdapter(adapter);
		
		getSupportActionBar().setTitle(MiqaatListActivity.months[month]);
		
		//determine start and end days for displaying events
		int eventsStart = 0, eventsEnd = 0;
		eventsStart= Misri.misri_month[month]+1;
		if(month<11){
		eventsEnd=Misri.misri_month[month+1]+1;}
		else eventsEnd=355;


		for(int x=eventsStart;x<eventsEnd;x++){
			if(DateUtil.priorityEventMap.containsKey(x)){
                String[] arr = DateUtil.priorityEventMap.get(x);
				for(int y=0;y<arr.length;y++){
					events.add(arr[y]);
					dates.add((x - eventsStart + 1) + DateUtil.getDaySuffix(x-eventsStart+1) + " - Notification Event");
				}
			}
			if(DateUtil.eventMap.containsKey(x)){
                String[] arr2 = DateUtil.eventMap.get(x);
				for(int y=0;y<arr2.length;y++){
					events.add(arr2[y]);
					dates.add((x - eventsStart + 1) + DateUtil.getDaySuffix(x-eventsStart+1));
				}
			}			
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
	
	private class MiqaatListAdapter extends BaseAdapter {
		public int getCount() {
			return events.size();
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
				row=inflater.inflate(R.layout.miqaat_event_row, parent, false);
			}

			((TextView)row.findViewById(R.id.date)).setText(dates.get(position));
			((TextView)row.findViewById(R.id.event)).setText(events.get(position));
			return row;
		}
	}
	
	
	
	
	
}

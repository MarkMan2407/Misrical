package com.squizzard.miqaatList

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squizzard.converter.model.Misri
import com.squizzard.misriCalendar.R
import com.squizzard.util.DateUtil
import com.squizzard.util.DateUtil.getDaySuffix

class MiqaatMonthActivity : AppCompatActivity() {
    private val events = ArrayList<String>()
    private val dates = ArrayList<String>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.miqaat_event_display)
        val month = intent.getIntExtra("MONTH", 0)
        val list = findViewById<ListView>(R.id.miqaat_list)
        val adapter = MiqaatListAdapter()
        list.adapter = adapter

        supportActionBar!!.title = MiqaatListActivity.months[month]

        //determine start and end days for displaying events
        val eventsStart: Int = Misri.misri_month[month] + 1
        val eventsEnd: Int = if (month < 11) {
            Misri.misri_month[month + 1] + 1
        } else 355


        for (x in eventsStart until eventsEnd) {
            if (DateUtil.priorityEventMap.containsKey(x)) {
                val arr = DateUtil.priorityEventMap[x]!!
                for (y in arr.indices) {
                    events.add(arr[y])
                    dates.add((x - eventsStart + 1).toString() + getDaySuffix(x - eventsStart + 1) + " - Notification Event")
                }
            }
            if (DateUtil.eventMap.containsKey(x)) {
                val arr2 = DateUtil.eventMap[x]!!
                for (y in arr2.indices) {
                    events.add(arr2[y])
                    dates.add((x - eventsStart + 1).toString() + getDaySuffix(x - eventsStart + 1))
                }
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private inner class MiqaatListAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return events.size
        }

        override fun getItem(position: Int): String {
            return events[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView: View
            val viewHolder: EventViewHolder

            if (convertView == null) {
                rowView = layoutInflater.inflate(R.layout.miqaat_event_row, parent, false)

                viewHolder = EventViewHolder(
                    dateTextView = rowView.findViewById(R.id.date),
                    eventTextView = rowView.findViewById(R.id.event)
                )
                rowView.tag = viewHolder
            } else {
                rowView = convertView
                viewHolder = rowView.tag as EventViewHolder
            }

            viewHolder.dateTextView.text = dates[position]
            viewHolder.eventTextView.text = events[position]

            return rowView
        }
    }
}

private class EventViewHolder(
    val dateTextView: TextView,
    val eventTextView: TextView
)

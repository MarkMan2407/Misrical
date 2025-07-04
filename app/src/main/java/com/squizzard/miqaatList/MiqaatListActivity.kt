package com.squizzard.miqaatList

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squizzard.analytics.AnalyticsHelper.sendEvent
import com.squizzard.misriCalendar.R

class MiqaatListActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.miqaat_month_display)
        val list = findViewById<ListView>(R.id.month_list)
        list.onItemClickListener = OnItemClickListener { arg0, v, position, id ->
            sendEvent("month_selected")

            val miqaatMonthIntent = Intent(this@MiqaatListActivity, MiqaatMonthActivity::class.java)
            miqaatMonthIntent.putExtra("MONTH", position)
            startActivity(miqaatMonthIntent)
        }

        val adapter = MonthListAdapter()
        list.adapter = adapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private inner class MonthListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return months.size
        }

        override fun getItem(position: Int): String {
            return months[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView: View
            val viewHolder: ViewHolder

            if (convertView == null) {
                rowView = layoutInflater.inflate(R.layout.miqaat_month_row, parent, false)
                viewHolder = ViewHolder(rowView.findViewById(R.id.month_text))
                rowView.tag = viewHolder
            } else {
                rowView = convertView
                viewHolder = rowView.tag as ViewHolder // Safe cast if you are sure about the tag
            }

            viewHolder.monthTextView.text = months[position]
            // You could also get the item using getItem(position)
            // viewHolder.monthTextView.text = getItem(position)
            return rowView
        }
    }

    private class ViewHolder(val monthTextView: TextView)

    companion object {
        val months: Array<String> = arrayOf(
            "Muharram al-Haraam",
            "Safar al-Muzaffar",
            "Rabi al-Awwal",
            "Rabi al-Aakhar",
            "Jumada al-Ula",
            "Jumada al-Ukhra",
            "Rajab al-Asab",
            "Shaban al-Karim",
            "Ramadan al-Moazzam",
            "Shawwal al-Mukarram",
            "Zilqad al-Haraam",
            "Zilhaj al-Haraam"
        )
    }
}

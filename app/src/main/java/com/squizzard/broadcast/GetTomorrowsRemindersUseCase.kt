package com.squizzard.broadcast

import android.content.Context
import com.squizzard.converter.model.Misri
import com.squizzard.data.GetGregorianRemindersByDateUseCase
import com.squizzard.data.GetMisriRemindersByDateUseCase
import java.util.*

class GetTomorrowsRemindersUseCase {
    fun getTomorrowsReminders(context: Context): MutableList<String> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val allReminders: MutableList<String> = mutableListOf()
        val gregorianReminders = GetGregorianRemindersByDateUseCase(context).getRemindersByDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH))

        val misriDates = Misri().getMisriDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
        val misriReminders = GetMisriRemindersByDateUseCase(context).getRemindersByDate(misriDates[0], misriDates[1])

        gregorianReminders?.let {
            for(reminder in it) {
                allReminders.add(reminder?.reminderText!!)
            }
        }

        misriReminders?.let {
            for(reminder in it) {
                allReminders.add(reminder?.reminderText!!)
            }
        }

        return allReminders
    }
}
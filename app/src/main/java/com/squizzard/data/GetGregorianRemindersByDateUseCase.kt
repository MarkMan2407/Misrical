package com.squizzard.data

import android.content.Context
import com.squizzard.reminders.model.Reminder

class GetGregorianRemindersByDateUseCase(context: Context) {

    private val db: MisricalDatabase? = MisricalDatabase.getDatabase(context)
    private val reminderDao: ReminderDao? = db?.reminderDao()

    fun getRemindersByDate(day: Int, month: Int): List<Reminder?>? {
        return reminderDao!!.getGregorianRemindersForDayAndMonth(day, month)
    }

}
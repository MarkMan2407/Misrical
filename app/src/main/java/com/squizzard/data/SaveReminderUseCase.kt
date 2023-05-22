package com.squizzard.data

import android.content.Context
import com.squizzard.reminders.model.Reminder


class SaveReminderUseCase(context: Context) {
    private val db: MisricalDatabase? = MisricalDatabase.getDatabase(context)

    private val reminderDao: ReminderDao? = db?.reminderDao()

    fun saveReminder(reminder: Reminder) {
        MisricalDatabase.databaseWriteExecutor.execute { reminderDao!!.insert(reminder) }
    }
}
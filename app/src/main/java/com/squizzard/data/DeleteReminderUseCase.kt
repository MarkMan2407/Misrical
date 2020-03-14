package com.squizzard.data

import android.content.Context
import com.squizzard.reminders.model.Reminder


class DeleteReminderUseCase(context: Context) {
    private val db: MisricalDatabase? = MisricalDatabase.getDatabase(context)

    private val reminderDao: ReminderDao? = db?.reminderDao()

    fun deleteReminder(reminder: Reminder) = reminderDao!!.delete(reminder)

}
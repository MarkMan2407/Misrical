package com.squizzard.data

import android.content.Context

import com.squizzard.reminders.model.Reminder

class GetRemindersUseCase(context: Context) {
    private val db: MisricalDatabase? = MisricalDatabase.getDatabase(context)

    private val reminderDao: ReminderDao? = db?.reminderDao()
    fun getReminder(id: Int): Reminder? {
        return reminderDao!!.get(id)
    }

    val reminders: List<Reminder?>?
        get() = reminderDao!!.getAll()

}
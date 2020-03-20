package com.squizzard.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.squizzard.reminders.model.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders where id = :reminderId")
    fun get(reminderId: Int): Reminder?

    @Query("SELECT * FROM reminders")
    fun getAll(): List<Reminder>?

    @Query ("SELECT * FROM reminders where type = 'G'")
    fun getGregorianReminders(): List<Reminder>?

    @Query ("SELECT * FROM reminders where type = 'M'")
    fun getMisriReminders(): List<Reminder>?

    @Query ("SELECT * FROM reminders where type = 'G' and gregorianDay = :day and gregorianMonth = :month")
    fun getGregorianRemindersForDayAndMonth(day: Int, month: Int): List<Reminder>?

    @Query ("SELECT * FROM reminders where type = 'M' and misriDay = :day and misriMonth = :month")
    fun getMisriRemindersForDayAndMonth(day: Int, month: Int): List<Reminder>?

    @Delete
    fun delete(reminder: Reminder?)

    @Insert
    fun insert(reminder: Reminder?)
}
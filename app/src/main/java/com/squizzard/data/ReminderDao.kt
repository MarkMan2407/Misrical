package com.squizzard.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.squizzard.reminders.model.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders where id = :queryId")
    fun get(queryId: Int): Reminder?

    @Query("SELECT * FROM reminders")
    fun getAll(): List<Reminder>?

    @Delete
    fun delete(reminder: Reminder?)

    @Insert
    fun insert(reminder: Reminder?)
}
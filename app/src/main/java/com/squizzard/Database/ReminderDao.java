package com.squizzard.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.squizzard.Reminder.Reminder;

import java.sql.SQLException;
import java.util.ArrayList;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminders where id = :queryId")
    Reminder getReminder(int queryId);

    @Query("SELECT * FROM reminders")
    ArrayList<Reminder> getReminders();

    @Query("SELECT * FROM reminders WHERE misriDay = :day and misriMonth = :month and type = 'M'")
    ArrayList<Reminder> getMisriReminders(int day, int month);

    @Query("SELECT * FROM reminders WHERE gregorianDay = :day and gregorianMonth = :month and type = 'G'")
    ArrayList<Reminder> getGregorianReminders(int day, int month);

    @Delete
    void deleteReminder(int id);

    @Insert
    void insertReminder(Reminder reminder);
}

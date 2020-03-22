package com.squizzard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import com.squizzard.reminders.model.Reminder
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class MisricalDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: MisricalDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        fun getDatabase(context: Context): MisricalDatabase? {
            if (INSTANCE == null) {
                synchronized(MisricalDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                MisricalDatabase::class.java, "misrical_database")
                                .allowMainThreadQueries().build()//todo take off main thread
                    }
                }
            }
            return INSTANCE
        }
    }
}
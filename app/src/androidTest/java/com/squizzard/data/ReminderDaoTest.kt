package com.squizzard.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.runner.AndroidJUnit4
import com.squizzard.reminders.model.Reminder
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReminderDaoTest {

    private lateinit var reminderDao: ReminderDao
    private lateinit var db: MisricalDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, MisricalDatabase::class.java).build()
        reminderDao = db.reminderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertReminderAndCheck() {
        val reminder = Reminder("Test", 2, 3, 4, 5, 6, 7, "G").
                apply { id=34 }

        reminderDao.insert(reminder)
        val allReminders = reminderDao.getAll()
        assertThat(allReminders?.size, equalTo(1))

        val retrievedReminder = reminderDao.get(34)
        assertThat(retrievedReminder?.id, equalTo(34))
    }


    @Test
    @Throws(Exception::class)
    fun insertReminderAndDelete() {
        val reminder = Reminder("Test", 2, 3, 4, 5, 6, 7, "G").
                apply { id=34 }

        reminderDao.insert(reminder)
        var allReminders = reminderDao.getAll()
        assertThat(allReminders?.size, equalTo(1))

        reminderDao.delete(reminder)
        allReminders = reminderDao.getAll()
        assertThat(allReminders?.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun insertByTypeAndCheck() {
        val reminder1G = Reminder("Test", 2, 3, 4, 5, 6, 7, "G")
        reminderDao.insert(reminder1G)
        val reminder2G = Reminder("Test", 2, 3, 4, 5, 6, 7, "G")
        reminderDao.insert(reminder2G)
        val reminder3G = Reminder("Test", 2, 3, 4, 5, 6, 7, "G")
        reminderDao.insert(reminder3G)
        val reminder1M = Reminder("Test", 2, 3, 4, 5, 6, 7, "M")
        reminderDao.insert(reminder1M)
        val reminder2M = Reminder("Test", 2, 3, 4, 5, 6, 7, "M")
        reminderDao.insert(reminder2M)

        val allGregorianReminders = reminderDao.getGregorianReminders()
        assertThat(allGregorianReminders?.size, equalTo(3))

        val allMisriReminders = reminderDao.getMisriReminders()
        assertThat(allMisriReminders?.size, equalTo(2))

    }

    @Test
    @Throws(Exception::class)
    fun insertByTypeAndCheckByDate() {
        val reminder1G = Reminder("Test1", 2, 3, 8, 1, 6, 7, "G")
        reminderDao.insert(reminder1G)
        val reminder2G = Reminder("Test2", 3, 4, 9, 2, 6, 7, "G")
        reminderDao.insert(reminder2G)
        val reminder3G = Reminder("Test3", 4, 5, 10, 3, 6, 7, "G")
        reminderDao.insert(reminder3G)
        val reminder1M = Reminder("Test4", 5, 6, 11, 4, 6, 7, "M")
        reminderDao.insert(reminder1M)
        val reminder2M = Reminder("Test5", 6, 7, 12, 5, 6, 7, "M")
        reminderDao.insert(reminder2M)
        val reminder3M = Reminder("Test6", 6, 7, 12, 5, 6, 7, "M")
        reminderDao.insert(reminder3M)

        val allReminders = reminderDao.getAll()
        assertThat(allReminders?.size, equalTo(6))

        val gregorianByDate = reminderDao.getGregorianRemindersForDayAndMonth(2,3)
        assert(gregorianByDate?.size == 1)
        assertThat(gregorianByDate?.get(0)?.reminderText, equalTo("Test1") )

        val misriByDate = reminderDao.getMisriRemindersForDayAndMonth(12,5)
        assert(misriByDate?.size == 2)
        assertThat(misriByDate?.get(0)?.reminderText, equalTo("Test5") )
        assertThat(misriByDate?.get(1)?.reminderText, equalTo("Test6") )
    }
}
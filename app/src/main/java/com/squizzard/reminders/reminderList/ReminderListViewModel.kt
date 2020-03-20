package com.squizzard.reminders.reminderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.squizzard.liveData.SingleLiveEvent

class ReminderListViewModel : ViewModel() {

    private val checkEventsTodayLiveEvent = SingleLiveEvent<Any>()

    val checkTodaysEventsPressed: LiveData<Any> get() = checkEventsTodayLiveEvent

    fun checkTodaysEvents() = checkEventsTodayLiveEvent.call()

}
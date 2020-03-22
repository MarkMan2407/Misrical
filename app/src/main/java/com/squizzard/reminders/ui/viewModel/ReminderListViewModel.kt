package com.squizzard.reminders.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.squizzard.liveData.SingleLiveEvent

class ReminderListViewModel : ViewModel() {

    private val checkEventsTodayLiveEvent = SingleLiveEvent<Any>()
    private val checkEventsTomorrowLiveEvent = SingleLiveEvent<Any>()

    val checkTodaysEventsPressed: LiveData<Any> get() = checkEventsTodayLiveEvent
    val checkTomorrowsEventsPressed: LiveData<Any> get() = checkEventsTomorrowLiveEvent

    fun checkTodaysEvents() = checkEventsTodayLiveEvent.call()
    fun checkTomorrowsEvents() = checkEventsTomorrowLiveEvent.call()


}
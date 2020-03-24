package com.squizzard.broadcast

import com.squizzard.converter.model.Misri
import com.squizzard.util.DateUtil

class GetMiqaatsUseCase {
    fun getForToday(): MutableList<String> {
        var events: MutableList<String> = mutableListOf()
        val miqaats = DateUtil.priorityEventMap[Misri().misriOrdinal]

        miqaats?.let {
            for (string in it) {
                events.add(string)
            }
        }
        return events
    }

    fun getForTomorrow(): MutableList<String> {

        var events: MutableList<String> = mutableListOf()
        val miqaats = DateUtil.priorityEventMap[Misri().misriOrdinal + 1]

        miqaats?.let {
            for (string in it) {
                events.add(string)
            }
        }

        return events
    }
}
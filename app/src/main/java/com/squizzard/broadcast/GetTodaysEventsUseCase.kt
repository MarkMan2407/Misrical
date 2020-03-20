package com.squizzard.broadcast

import com.squizzard.converter.model.Misri
import com.squizzard.util.DateUtil

class GetTodaysEventsUseCase {
    fun getTodaysEvents() = DateUtil.priorityEventMap[Misri().misriOrdinal]
}
package com.squizzard.broadcast

import com.squizzard.converter.model.Misri
import com.squizzard.util.DateUtil

class GetTomorrowsEventsUseCase {
    fun getTomorrowsEvents() = DateUtil.priorityEventMap[Misri().misriOrdinal + 1]
}
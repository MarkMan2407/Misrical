package com.squizzard.reminders.model

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder @JvmOverloads constructor(@ColumnInfo(name = "reminderText") var reminderText: String?,
                    @ColumnInfo(name = "gregorianDay") var gregorianDay: Int,
                    @ColumnInfo(name = "gregorianMonth") var gregorianMonth: Int,
                    @ColumnInfo(name = "misriDay") var misriDay: Int,
                    @ColumnInfo(name = "misriMonth") var misriMonth: Int,
                    @ColumnInfo(name = "hour") var hour: Int,
                    @ColumnInfo(name = "minute") var minute: Int,
                    @ColumnInfo(name = "type") var type: String) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0
    @ColumnInfo(name = "active") var active: Boolean = true
}



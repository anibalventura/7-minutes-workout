package com.anibalventura.t7minutesworkout.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
class HistoryModel(
    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "time")
    val time: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
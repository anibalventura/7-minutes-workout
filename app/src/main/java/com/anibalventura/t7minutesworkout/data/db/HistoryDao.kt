package com.anibalventura.t7minutesworkout.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anibalventura.t7minutesworkout.data.models.HistoryModel

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history_table ORDER BY id DESC")
    fun getDatabase(): LiveData<List<HistoryModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(historyModel: HistoryModel)

    @Query("DELETE FROM history_table")
    suspend fun deleteDatabase()
}
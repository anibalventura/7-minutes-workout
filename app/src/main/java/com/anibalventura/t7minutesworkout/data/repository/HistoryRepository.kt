package com.anibalventura.t7minutesworkout.data.repository

import androidx.lifecycle.LiveData
import com.anibalventura.t7minutesworkout.data.db.HistoryDao
import com.anibalventura.t7minutesworkout.data.models.HistoryModel

class HistoryRepository(private val historyDao: HistoryDao) {

    val getDatabase: LiveData<List<HistoryModel>> = historyDao.getDatabase()

    suspend fun insertItem(historyModel: HistoryModel) {
        historyDao.insertItem(historyModel)
    }

    suspend fun deleteDatabase() {
        historyDao.deleteDatabase()
    }
}
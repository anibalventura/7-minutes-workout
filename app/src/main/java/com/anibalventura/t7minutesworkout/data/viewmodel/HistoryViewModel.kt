package com.anibalventura.t7minutesworkout.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anibalventura.t7minutesworkout.data.db.HistoryDatabase
import com.anibalventura.t7minutesworkout.data.models.HistoryModel
import com.anibalventura.t7minutesworkout.data.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val historyDao = HistoryDatabase.getDatabase(application).historyDao()
    private val repository: HistoryRepository

    val getDatabase: LiveData<List<HistoryModel>>

    init {
        repository = HistoryRepository(historyDao)
        getDatabase = repository.getDatabase
    }

    private fun insertItem(historyModel: HistoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertItem(historyModel)
        }
    }

    fun deleteDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDatabase()
        }
    }

    fun insertDateToDatabase() {
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val sdfDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = sdfDate.format(dateTime)
        val time = sdfTime.format(dateTime)

        insertItem(HistoryModel(date, time))
    }

    /** =========================== Check for empty database. =========================== **/

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfHistoryIsEmpty(historyData: List<HistoryModel>) {
        emptyDatabase.value = historyData.isEmpty()
    }
}
package com.example.topitup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topitup.database.history.History
import com.example.topitup.database.history.HistoryDao
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(private val historyDao: HistoryDao) : ViewModel() {
    fun getAll(): Flow<List<History>> = historyDao.getAll()

    fun searchDate(date: String): Flow<List<History>> = historyDao.searchDate(date)

   /* private val _text = MutableLiveData<String>().apply {
        value = "All Dates"
    }
    val text: LiveData<String> = _text*/
}

class HistoryViewModelFactory(
    private val historyDao: HistoryDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(historyDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
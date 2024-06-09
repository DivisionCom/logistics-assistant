package com.example.logistics_assistant.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logistics_assistant.database.StatisticsDao
import com.example.logistics_assistant.database.StatisticsEntity
import com.example.logistics_assistant.database.TasksDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val tasksDao: TasksDao,
    private val statisticsDao: StatisticsDao,
) : ViewModel() {

    val allTasks = tasksDao.getAllTasks()

    fun getStatisticsForMonth(month: String): LiveData<StatisticsEntity> {
        return statisticsDao.getStatisticsForMonth(month)
    }

    fun updateStatistics(month: String, completedTasks: Int) {
        viewModelScope.launch {
            val statisticsEntity = statisticsDao.getStatisticsForMonthSync(month)
            val updatedStatistics = statisticsEntity?.copy(completedTasks = completedTasks)
                ?: StatisticsEntity(month = month, completedTasks = completedTasks)
            statisticsDao.insertStatistics(updatedStatistics)
        }
    }
}

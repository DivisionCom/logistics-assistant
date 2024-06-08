package com.example.logistics_assistant.ui.main.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logistics_assistant.database.StatisticsDao
import com.example.logistics_assistant.database.StatisticsEntity
import com.example.logistics_assistant.database.TasksDao
import com.example.logistics_assistant.database.TasksModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksDao: TasksDao,
    private val statisticsDao: StatisticsDao,
) : ViewModel() {

    val allTasks: LiveData<List<TasksModel>> = tasksDao.getAllTasks()

    fun getTaskById(taskId: Int): LiveData<TasksModel> {
        return tasksDao.getTaskById(taskId)
    }

    fun markTaskAsCompleted(currentTask: TasksModel) {
        viewModelScope.launch {
            val updatedTask = currentTask.copy(isCompleted = true, status = "Выполнено")
            tasksDao.updateTask(updatedTask)
            updateStatistics()
        }
    }

    fun markTaskAsAtWork(currentTask: TasksModel) {
        viewModelScope.launch {
            val updatedTask = currentTask.copy(status = "В работе")
            tasksDao.updateTask(updatedTask)
        }
    }

    private fun updateStatistics() {
        viewModelScope.launch {
            val currentMonth = getCurrentMonth()
            val statisticsEntity = statisticsDao.getStatisticsForMonthSync(currentMonth)
            val updatedStatistics =
                statisticsEntity?.copy(completedTasks = statisticsEntity.completedTasks + 1)
                    ?: StatisticsEntity(month = currentMonth, completedTasks = 1)
            statisticsDao.insertStatistics(updatedStatistics)
        }
    }

    private fun getCurrentMonth(): String {
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return current.format(formatter)
    }
}
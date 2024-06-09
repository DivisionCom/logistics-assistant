package com.example.logistics_assistant.presentation

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logistics_assistant.database.StatisticsDao
import com.example.logistics_assistant.database.StatisticsEntity
import com.example.logistics_assistant.database.TasksDao
import com.example.logistics_assistant.database.TasksModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksDao: TasksDao,
    private val statisticsDao: StatisticsDao,
) : ViewModel() {
    private val allTasks = tasksDao.getAllTasks()

    val _currentLocation =
        MutableStateFlow<Pair<Double, Double>?>(Pair(59.898348, 30.287443))

    private val _sortedTasks = MutableStateFlow<List<TasksModel>>(emptyList())
    val sortedTasks: StateFlow<List<TasksModel>> = _sortedTasks

    init {
        viewModelScope.launch {
            combine(allTasks, _currentLocation) { tasks, location ->
                location?.let { loc ->
                    sortTasksByDistance(tasks, loc)
                } ?: tasks
            }.collect { sortedTasks ->
                provideTasks(sortedTasks)
            }
        }
    }

    private fun provideTasks(tasks: List<TasksModel>) {
        viewModelScope.launch {
            _sortedTasks.value = tasks
        }
    }

    fun updateLocation(location: Location) {
        _currentLocation.value = Pair(location.latitude, location.longitude)
    }

    private fun sortTasksByDistance(
        tasks: List<TasksModel>, location: Pair<Double, Double>
    ): List<TasksModel> {
        val sortedTasks = tasks.sortedBy { task ->
            calculateDistance(
                location.first, location.second, task.placeALatitude, task.placeALongitude
            )
        }
        return sortedTasks
    }

    private fun calculateDistance(
        startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double
    ): Float {
        val startLocation = Location("").apply {
            latitude = startLatitude
            longitude = startLongitude
        }
        val endLocation = Location("").apply {
            latitude = endLatitude
            longitude = endLongitude
        }
        return startLocation.distanceTo(endLocation) / 1000 // Расстояние в километрах
    }

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
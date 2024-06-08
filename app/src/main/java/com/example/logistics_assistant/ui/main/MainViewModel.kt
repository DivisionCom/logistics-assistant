package com.example.logistics_assistant.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logistics_assistant.database.TasksModel
import com.example.logistics_assistant.ui.main.chat.User

class MainViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData<User>()
    val liveDataTasks = MutableLiveData<TasksModel>()
    val liveDataTasksList = MutableLiveData<List<TasksModel>>()
}
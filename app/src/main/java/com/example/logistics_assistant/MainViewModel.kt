package com.example.logistics_assistant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logistics_assistant.main.chat.User

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<User>()
}
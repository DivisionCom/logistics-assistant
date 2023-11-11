package com.example.logistics_assistant.ui.main.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.logistics_assistant.models.MainViewModel
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentTasksBinding
import com.example.logistics_assistant.ui.main.MenuActivity
import com.example.logistics_assistant.ui.main.chat.User

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveUserPhone()
        (activity as MenuActivity?)?.unsetLogoBar()
        activity?.title = resources.getString(R.string.title_tasks)
    }

    private fun saveUserPhone(){
        val user = User(
            phone = (activity as MenuActivity?)?.userPhone()
        )
        model.liveDataCurrent.value = user
    }
}
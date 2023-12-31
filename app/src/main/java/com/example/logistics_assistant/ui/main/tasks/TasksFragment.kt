package com.example.logistics_assistant.ui.main.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.logistics_assistant.R
import com.example.logistics_assistant.adapters.TasksVpAdapter
import com.example.logistics_assistant.databinding.FragmentTasksBinding
import com.example.logistics_assistant.models.MainViewModel
import com.example.logistics_assistant.ui.main.MenuActivity
import com.example.logistics_assistant.ui.main.chat.User
import com.google.android.material.tabs.TabLayoutMediator

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private val model: MainViewModel by activityViewModels()
    private val fList: List<Fragment> = listOf(
        InboxFragment.newInstance(),
        AtworkFragment.newInstance()
    )
    private val tList = listOf(
        "Входящие",
        "В работе"
    )

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

        init()
    }

    private fun init() = with(binding){
        val adapter = TasksVpAdapter(activity as FragmentActivity, fList)
        vpTasks.adapter = adapter
        TabLayoutMediator(tabLayout, vpTasks){
            tab, pos -> tab.text = tList[pos]
        }.attach()
    }

    private fun saveUserPhone(){
        val user = User(
            phone = (activity as MenuActivity?)?.userPhone()
        )
        model.liveDataCurrent.value = user
    }
}
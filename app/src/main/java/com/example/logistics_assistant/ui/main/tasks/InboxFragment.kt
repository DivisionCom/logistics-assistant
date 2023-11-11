package com.example.logistics_assistant.ui.main.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logistics_assistant.adapters.TasksAdapter
import com.example.logistics_assistant.databinding.FragmentInboxBinding
import com.example.logistics_assistant.models.MainViewModel
import com.example.logistics_assistant.models.TasksModel

class InboxFragment : Fragment() {
    private lateinit var binding: FragmentInboxBinding
    private lateinit var adapter: TasksAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        fillInboxPage()
    }

    private fun fillInboxPage() {
        adapter.submitList(getInboxList())
    }

    private fun initRv() = with(binding) {
        rvInbox.layoutManager = LinearLayoutManager(activity)
        adapter = TasksAdapter()
        rvInbox.adapter = adapter
    }

    private fun getInboxList(): List<TasksModel> {
        val list = ArrayList<TasksModel>()
        list.add(
            createTask(
                "Задание № 003",
                "30 000,00 ₽",
                "11.08.2023",
                "12:00",
                "Новое",
                "Машиностроительная улица, 91",
                "Магистральная улица, 52",
                "12.08.2023",
                "12:00",
                "13.08.2023",
                "13:00",
            )
        )
        list.add(
            createTask(
                "Задание № 002",
                "32 000,00 ₽",
                "11.08.2023",
                "10:00",
                "Новое",
                "Въезд Космонавтов, 96",
                "Спуск Косиора, 32",
                "12.08.2023",
                "07:00",
                "13.08.2023",
                "14:00",
            )
        )
        list.add(
            createTask(
                "Задание № 002",
                "20 000,00 ₽",
                "11.08.2023",
                "09:00",
                "Новое",
                "Проезд Ладыгина, 44",
                "Проезд Балканская, 20",
                "12.08.2023",
                "07:00",
                "13.08.2023",
                "14:00",
            )
        )
        list.add(
            createTask(
                "Задание № 001",
                "64 000,00 ₽",
                "11.08.2023",
                "09:00",
                "Новое",
                "Проезд Ладыгина, 44",
                "Проезд Балканская, 20",
                "12.08.2023",
                "07:00",
                "13.08.2023",
                "14:00",
            )
        )
        return list
    }

    private fun createTask(
        taskNum: String,
        price: String,
        dateTask: String,
        timeTask: String,
        status: String,
        placeA: String,
        placeB: String,
        datePlaceA: String,
        timePlaceA: String,
        datePlaceB: String,
        timePlaceB: String
    ): TasksModel {
        return TasksModel(
            taskNum = taskNum,
            price = price,
            dateTask = dateTask,
            timeTask = timeTask,
            status = status,
            placeA = placeA,
            placeB = placeB,
            datePlaceA = datePlaceA,
            timePlaceA = timePlaceA,
            datePlaceB = datePlaceB,
            timePlaceB = timePlaceB,
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = InboxFragment()
    }
}
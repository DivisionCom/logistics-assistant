package com.example.logistics_assistant.ui.main.tasks

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.logistics_assistant.database.TasksModel
import com.example.logistics_assistant.databinding.FragmentTaskDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {
    private lateinit var binding: FragmentTaskDetailBinding
    private val tasksViewModel: TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId = arguments?.getInt("taskId") ?: return
        val taskString = arguments?.getString("taskStatus")
        lateinit var currentTask: TasksModel
        Log.d("deblog", taskString.toString())

        tasksViewModel.getTaskById(taskId).observe(viewLifecycleOwner) { task ->
            task?.let {
                Log.d("deblog", task.toString())
                currentTask = task
                binding.tvCurrentTask.text = task.taskNum
                binding.tvCurrentPrice.text = task.price
                binding.tvCurrentStatus.text = task.status
                binding.tvCurrentDateOnly.text = task.dateTask
                binding.tvCurrentTimeOnly.text = task.timeTask
                binding.tvCurrentPlaceA.text = task.placeA
                binding.tvCurrentDatePlaceA.text = task.datePlaceA
                binding.tvCurrentTimePlaceA.text = task.timePlaceA
                binding.tvCurrentPlaceB.text = task.placeB
                binding.tvCurrentDatePlaceB.text = task.datePlaceB
                binding.tvCurrentTimePlaceB.text = task.timePlaceB
                when (task.status) {
                    "Новое" -> binding.tvCurrentStatus.setTextColor(Color.parseColor("#59C617"))
                    "Запланированные" -> binding.tvCurrentStatus.setTextColor(Color.parseColor("#308ADD"))
                    "В процессе" -> binding.tvCurrentStatus.setTextColor(Color.parseColor("#C649F2"))
                    "Проверка" -> binding.tvCurrentStatus.setTextColor(Color.parseColor("#FAB020"))
                    "Ожидает оплаты" -> binding.tvCurrentStatus.setTextColor(Color.parseColor("#FAB020"))
                    "В работе" -> binding.tvCurrentStatus.setTextColor(Color.parseColor("#4682B4"))
                }
                if (taskString == "Входящие") {
                    binding.btnMarkCompleted.text = "Принять в работу"
                } else {
                    binding.btnMarkCompleted.text = "Пометить выполненным"
                }
            }
        }

        binding.btnMarkCompleted.setOnClickListener {
            if (taskString == "Входящие") {
                tasksViewModel.markTaskAsAtWork(currentTask)
                findNavController().navigateUp()
            } else {
                tasksViewModel.markTaskAsCompleted(currentTask)
                findNavController().navigateUp()
            }
        }

    }
}
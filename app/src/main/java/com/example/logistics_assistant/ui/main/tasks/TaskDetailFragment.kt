package com.example.logistics_assistant.ui.main.tasks

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.logistics_assistant.database.TasksModel
import com.example.logistics_assistant.databinding.FragmentTaskDetailBinding
import com.example.logistics_assistant.presentation.TasksViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectTapListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {
    private lateinit var binding: FragmentTaskDetailBinding
    private val tasksViewModel: TasksViewModel by viewModels()
    private lateinit var currentTask: TasksModel
    private lateinit var currentLocation: Pair<Double, Double>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MapKitFactory.initialize(requireContext())

        val taskId = arguments?.getInt("taskId") ?: return
        val taskString = arguments?.getString("taskStatus")

        tasksViewModel._currentLocation.value?.let {
            currentLocation = it
        }

        tasksViewModel.getTaskById(taskId).observe(viewLifecycleOwner) { task ->
            task?.let {
                currentTask = task
                updateUI(task, taskString)
                displayPointOnMap(task)
            }
        }

        binding.btnMarshrut.setOnClickListener {
            Toast.makeText(requireContext(), "Функция пока недоступна", Toast.LENGTH_SHORT).show()
        }

        binding.btnMarkCompleted.setOnClickListener {
            if (taskString == "Входящие") {
                tasksViewModel.markTaskAsAtWork(currentTask)
            } else {
                tasksViewModel.markTaskAsCompleted(currentTask)
            }
            findNavController().navigateUp()
        }
    }

    private fun displayPointOnMap(task: TasksModel) {
        showCurrentLocation()
        val pointA = Point(task.placeALatitude, task.placeALongitude)
        binding.mapView.map.move(CameraPosition(pointA, 14.0f, 0.0f, 0.0f))

        val mapObjects = binding.mapView.map.mapObjects
        val placemark = mapObjects.addPlacemark(pointA)

        placemark.addTapListener(MapObjectTapListener { mapObject, point ->
            Toast.makeText(requireContext(), "Точка A: ${task.placeA}", Toast.LENGTH_SHORT).show()
            true
        })
    }

    private fun showCurrentLocation() {
        val currentLocation = Point(currentLocation.first, currentLocation.second)
        binding.mapView.map.mapObjects.addPlacemark(currentLocation)
        binding.mapView.map.move(CameraPosition(currentLocation, 14.0f, 0.0f, 0.0f))

        val mapObjects = binding.mapView.map.mapObjects
        val placemark = mapObjects.addPlacemark(currentLocation)

        placemark.addTapListener(MapObjectTapListener { mapObject, point ->
            Toast.makeText(requireContext(), "Ваше местоположение", Toast.LENGTH_SHORT).show()
            true
        })
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun updateUI(task: TasksModel, taskString: String?) {
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
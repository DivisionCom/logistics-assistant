package com.example.logistics_assistant.ui.main.profile.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class StatisticsFragment : DialogFragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private val statisticsViewModel: StatisticsViewModel by viewModels()
    private var completeCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.StatisticsDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentMonth = getCurrentMonth()
        statisticsViewModel.allTasks.observe(viewLifecycleOwner) { tasksList ->
            completeCount = tasksList.count { it.isCompleted }
            updateProgressBar(completeCount)
        }
    }

    private fun updateProgressBar(completedTasks: Int) {
        binding.progressBar.max = 5 // Set the max value to 5
        binding.progressBar.progress = completedTasks

        val progressColor = when (completedTasks) {
            0 -> R.color.progress_low
            in 1..2 -> R.color.progress_medium
            in 3..4 -> R.color.progress_high
            5 -> R.color.progress_full
            else -> R.color.progress_low
        }

        binding.progressBar.progressTintList =
            ContextCompat.getColorStateList(requireContext(), progressColor)

        binding.tvStatistics.text =
            "Вы выполнили $completedTasks/5 заказов на грузоперевозку для получения премии"
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getCurrentMonth(): String {
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return current.format(formatter)
    }
}
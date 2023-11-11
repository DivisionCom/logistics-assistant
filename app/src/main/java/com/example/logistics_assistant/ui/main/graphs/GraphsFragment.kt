package com.example.logistics_assistant.ui.main.graphs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logistics_assistant.R
import com.example.logistics_assistant.adapters.CalendarAdapter
import com.example.logistics_assistant.databinding.FragmentGraphsBinding
import com.example.logistics_assistant.ui.main.MenuActivity
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class GraphsFragment() : Fragment(), CalendarAdapter.OnItemListener {

    private lateinit var binding: FragmentGraphsBinding
    private lateinit var selectedDate: LocalDate
    val months = listOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGraphsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MenuActivity?)?.unsetLogoBar()
        activity?.title = resources.getString(R.string.title_graphs_tb)

        init()

    }

    private fun init(){
        selectedDate = LocalDate.now()
        setMonthView()
        previousMonthAction()
        nextMonthAction()
    }

    private fun setMonthView() {
        binding.tvMonthYear.text = getMonthYearDate(selectedDate)
        val daysInMonth: ArrayList<String> = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(context, 7)
        binding.rvCalendar.layoutManager = layoutManager
        binding.rvCalendar.adapter = calendarAdapter
    }

    private fun getMonthYearDate(date: LocalDate) = "${translateMonth(date)} ${getYear(date)}"

    private fun translateMonth(date: LocalDate): String{
        val monthIndex = date.month.value - 1
        return months[monthIndex]
    }

    private fun getYear(date: LocalDate) = date.year

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 2..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun nextMonthAction() {
        binding.ibNext.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }
    }

    private fun previousMonthAction() {
        binding.ibPrevious.setOnClickListener{
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }
    }

    override fun onItemClick(position: Int, dayText: String?) {
        Toast.makeText(context, "Функция пока недоступна", Toast.LENGTH_SHORT).show()
    }


}
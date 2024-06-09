package com.example.logistics_assistant.ui.main.tasks.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logistics_assistant.adapters.TasksAdapter
import com.example.logistics_assistant.database.TasksModel
import com.example.logistics_assistant.databinding.FragmentAtworkBinding
import com.example.logistics_assistant.presentation.TasksViewModel
import com.example.logistics_assistant.ui.main.tasks.TasksFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AtworkFragment : Fragment(), TasksAdapter.OnItemClickListener {
    private lateinit var binding: FragmentAtworkBinding
    private lateinit var adapter: TasksAdapter
    private val model: TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAtworkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRv()
        observeTasks()
    }

    private fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            model.sortedTasks.collect { tasks ->
                val updatedTasks = tasks.filter {
                    it.status == "В работе"
                }

                adapter.submitList(updatedTasks)
            }
        }
    }

    private fun initRv() = with(binding) {
        rvAtwork.layoutManager = LinearLayoutManager(activity)
        adapter = TasksAdapter(this@AtworkFragment)
        rvAtwork.adapter = adapter
    }

    override fun onItemClick(task: TasksModel) {
        val action =
            TasksFragmentDirections.actionNavigationTasksToTaskDetailFragment(task.id, "В работе")
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AtworkFragment()
    }
}
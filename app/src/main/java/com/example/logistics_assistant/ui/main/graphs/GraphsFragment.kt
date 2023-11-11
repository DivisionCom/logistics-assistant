package com.example.logistics_assistant.ui.main.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentGraphsBinding
import com.example.logistics_assistant.ui.main.MenuActivity

class GraphsFragment : Fragment() {

    private lateinit var binding: FragmentGraphsBinding

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
    }
}
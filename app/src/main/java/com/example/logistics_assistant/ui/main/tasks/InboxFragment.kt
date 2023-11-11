package com.example.logistics_assistant.ui.main.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentInboxBinding
import com.example.logistics_assistant.databinding.FragmentTasksBinding

class InboxFragment : Fragment() {
    private lateinit var binding: FragmentInboxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
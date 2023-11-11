package com.example.logistics_assistant.ui.main.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentAtworkBinding
import com.example.logistics_assistant.databinding.FragmentInboxBinding

class AtworkFragment : Fragment() {
    private lateinit var binding: FragmentAtworkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAtworkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
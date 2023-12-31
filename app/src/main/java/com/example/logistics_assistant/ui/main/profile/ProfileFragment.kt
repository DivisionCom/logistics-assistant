package com.example.logistics_assistant.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.logistics_assistant.models.MainViewModel
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentProfileBinding
import com.example.logistics_assistant.ui.main.MenuActivity

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MenuActivity?)?.unsetLogoBar()
        activity?.title = resources.getString(R.string.title_profile)
        fillUserPhone()
    }

    private fun fillUserPhone(){
        if(model.liveDataCurrent.value?.phone != "null") {
            binding.tvPhone.text = model.liveDataCurrent.value?.phone
        }
    }
}
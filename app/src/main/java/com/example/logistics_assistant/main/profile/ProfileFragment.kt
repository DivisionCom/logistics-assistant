package com.example.logistics_assistant.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.logistics_assistant.MainViewModel
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentProfileBinding
import com.example.logistics_assistant.main.MenuActivity
import com.example.logistics_assistant.main.chat.User

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
        val user = User(
            phone = (activity as MenuActivity?)?.userPhone()
        )
        model.liveDataCurrent.value = user
        Log.d("MyLog", user.phone.toString())
    }
}
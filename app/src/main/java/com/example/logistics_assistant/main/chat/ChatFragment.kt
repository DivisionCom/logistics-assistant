package com.example.logistics_assistant.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.FragmentChatBinding
import com.example.logistics_assistant.main.MenuActivity


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createUserAndSetHeader()
    }

    private fun createUserAndSetHeader(): User{
        val user = User(
            name = "Антонов Антон Антонович",
            photo = ResourcesCompat.getDrawable(resources, R.drawable.antonov, null)
        )
        activity?.title = null
        (activity as MenuActivity?)?.supportActionBar!!.subtitle = "  ${user.name}"
        (activity as MenuActivity?)?.setLogoBar(user.photo)
        return user
    }

}
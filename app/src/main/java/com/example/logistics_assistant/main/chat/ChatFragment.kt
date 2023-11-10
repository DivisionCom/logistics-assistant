package com.example.logistics_assistant.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logistics_assistant.R
import com.example.logistics_assistant.adapters.ChatAdapter
import com.example.logistics_assistant.databinding.FragmentChatBinding
import com.example.logistics_assistant.main.MenuActivity
import com.example.logistics_assistant.models.ChatMessage


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val adapter = ChatAdapter()
    private var index = 0
    private val listMyMessages = listOf(
        "Да, сегодня буду"
    )
    private val listFriendMessages = listOf(
        "Ты сегодня выйдешь на смену?",
        "Ждем тебя"
    )

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
        setMessages()
        init()
    }

    private fun init(){
        binding.apply {
            rvChat.adapter = adapter
            ibMessageSend.setOnClickListener{
                val mes = ChatMessage(
                    message = binding.etMessage.text.toString(),
                    senderId = null,
                    receiverId = "1"
                )
                adapter.addMessage(mes)
            }
        }
    }

    private fun setMessages(){
        binding.rvChat.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
            reverseLayout = false
        }
        setFriendMessages()
        setMyMessages()
        index++
        setFriendMessages()
    }

    private fun setFriendMessages(){
        val mes = ChatMessage(
            message = listFriendMessages[index],
            senderId = "1",
            receiverId = null
        )
        adapter.addMessage(mes)
    }

    private fun setMyMessages(){
        val mes = ChatMessage(
            message = listMyMessages[0],
            senderId = null,
            receiverId = "1"
        )
        adapter.addMessage(mes)
    }

    private fun createUserAndSetHeader(): User {
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
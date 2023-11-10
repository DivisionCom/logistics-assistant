package com.example.logistics_assistant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.ItemContainerReceivedMessageBinding
import com.example.logistics_assistant.databinding.ItemContainerSentMessageBinding
import com.example.logistics_assistant.models.ChatMessage

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val messageList = ArrayList<ChatMessage>()

    inner class ReceivedMessageViewHolder(private val receivedMessageBinding: ItemContainerReceivedMessageBinding) :
        RecyclerView.ViewHolder(receivedMessageBinding.root) {
        fun bind(chatMessage: ChatMessage) = with(receivedMessageBinding){
            tvMessage.text = chatMessage.message
        }
    }

    inner class SentMessageViewHolder(private val sentMessageBinding: ItemContainerSentMessageBinding) :
        RecyclerView.ViewHolder(sentMessageBinding.root) {
        fun bind(chatMessage: ChatMessage) = with(sentMessageBinding){
            tvMessage.text = chatMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].senderId == null) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = ItemContainerReceivedMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceivedMessageViewHolder(view)
        } else {
            val view = ItemContainerSentMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentMessageViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0){
            (holder as ReceivedMessageViewHolder).bind(messageList[position])
        } else{
            (holder as SentMessageViewHolder).bind(messageList[position])
        }
    }

    fun addMessage(message: ChatMessage){
        messageList.add(message)
        notifyDataSetChanged()
    }

}
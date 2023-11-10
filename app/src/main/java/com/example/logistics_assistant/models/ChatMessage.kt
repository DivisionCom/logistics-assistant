package com.example.logistics_assistant.models

data class ChatMessage (
    val senderId: String? = null,
    val receiverId: String? = null,
    val message: String? = null
)
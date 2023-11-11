package com.example.logistics_assistant.ui.main.chat

import android.graphics.drawable.Drawable

data class User(
    val name: String? = null,
    val job: String? = null,
    val personalNumber: Int? = null,
    val phone: String? = null,
    val citizenship: String? = null,
    val carType: String? = null,
    val carNumber: String? = null,
    val photo: Drawable? = null
)

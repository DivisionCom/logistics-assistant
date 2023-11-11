package com.example.logistics_assistant.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.logistics_assistant.databinding.ContentAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ContentAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
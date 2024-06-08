package com.example.logistics_assistant.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.logistics_assistant.databinding.ContentAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ContentAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ContentAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSystemPaddings()
    }

    private fun setSystemPaddings() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.containerAuth) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}
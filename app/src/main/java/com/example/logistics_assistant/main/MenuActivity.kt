package com.example.logistics_assistant.main

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.ActivityMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_menu)
        navView.setupWithNavController(navController)

        setSupportActionBar(binding.myToolbar)
    }

    fun setLogoBar(photo: Drawable?){
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setLogo(photo)
    }

    fun unsetLogoBar(){
        supportActionBar?.setDisplayUseLogoEnabled(false)
        supportActionBar?.subtitle = null
    }
}
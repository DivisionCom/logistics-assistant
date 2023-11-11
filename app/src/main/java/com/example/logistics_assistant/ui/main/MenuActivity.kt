package com.example.logistics_assistant.ui.main

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.ActivityMenuBinding
import com.google.android.material.badge.BadgeDrawable
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

        setChatNotifications(3)
    }

    fun setChatNotifications(notifications: Int){
        val badge = binding.navView.getOrCreateBadge(R.id.navigation_chat)
        if(notifications != 0) {
            badge.isVisible = true
            badge.backgroundColor = ResourcesCompat.getColor(resources, R.color.error, null)
            badge.number = notifications
        } else {
            badge.isVisible = false
        }
    }

    fun userPhone(): String{
        return intent.getStringExtra("save_phone").toString()
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
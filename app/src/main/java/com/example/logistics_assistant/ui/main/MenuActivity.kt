package com.example.logistics_assistant.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.logistics_assistant.R
import com.example.logistics_assistant.databinding.ActivityMenuBinding
import com.example.logistics_assistant.presentation.TasksViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private val model: TasksViewModel by viewModels()

    private var lastLocation: Location? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getCurrentLocation()
        } else {
            Log.d("deblog", "Permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSystemPaddings()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_menu)
        navView.setupWithNavController(navController)

        setSupportActionBar(binding.myToolbar)

        setChatNotifications(3)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val lastLoc = lastLocation
                if (lastLoc == null || location.distanceTo(lastLoc) > 10) {
                    lastLocation = location
                    model.updateLocation(location)
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        checkLocationPermissionAndGetLocation()
    }

    private fun getCurrentLocation() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000L,  // Минимальное время в миллисекундах между обновлениями местоположения
                10f,  // Минимальное расстояние в метрах между обновлениями местоположения
                locationListener
            )
        } catch (e: SecurityException) {
            Log.d("deblog", "SecurityException: ${e.message}")
        }
    }

    private fun checkLocationPermissionAndGetLocation() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun setSystemPaddings() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun setChatNotifications(notifications: Int) {
        val badge = binding.navView.getOrCreateBadge(R.id.navigation_chat)
        if (notifications != 0) {
            badge.isVisible = true
            badge.backgroundColor = ResourcesCompat.getColor(resources, R.color.error, null)
            badge.number = notifications
        } else {
            badge.isVisible = false
        }
    }

    fun userPhone(): String {
        return intent.getStringExtra("save_phone").toString()
    }


    fun setLogoBar(photo: Drawable?) {
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setLogo(photo)
    }

    fun unsetLogoBar() {
        supportActionBar?.setDisplayUseLogoEnabled(false)
        supportActionBar?.subtitle = null
    }
}
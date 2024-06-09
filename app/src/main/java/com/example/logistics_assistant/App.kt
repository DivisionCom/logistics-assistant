package com.example.logistics_assistant

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey("")
        MapKitFactory.initialize(this)
    }
}
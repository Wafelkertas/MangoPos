package com.example.mangopos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MangoPosApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}
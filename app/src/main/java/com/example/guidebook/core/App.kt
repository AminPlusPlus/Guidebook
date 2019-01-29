package com.example.guidebook.core

import android.app.Application

class App : Application() {

    private val appRunning = false

    override fun onCreate() {
        super.onCreate()
        GuideBook.getDatabaseInstance(this) //This will provide AppDatabase Instance
    }
}
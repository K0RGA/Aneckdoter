package com.example.aneckdoter

import android.app.Application
import com.example.aneckdoter.db.JokeRepository

class JokeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JokeRepository.initialize(this)
    }
}
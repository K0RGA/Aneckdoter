package com.example.aneckdoter

import android.app.Application
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.ui.JokeListFragment

class JokeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JokeRepository.initialize(this)
        JokeListFragment.initialize()
    }
}
package com.example.aneckdoter

import android.app.Application
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.ui.BestListFragment
import com.example.aneckdoter.ui.JokeListFragment
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JokeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JokeListFragment.initialize()
        BestListFragment.initialize()
    }
}
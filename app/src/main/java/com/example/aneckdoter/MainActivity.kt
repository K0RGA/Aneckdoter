package com.example.aneckdoter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isFragmentContainerEmpty = (savedInstanceState == null)
        if (isFragmentContainerEmpty){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, JokeListFragment.newInstance())
                .commit()
        }
    }


}
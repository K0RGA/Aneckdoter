package com.example.aneckdoter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "JokeListViewModel"

class JokeListViewModel : ViewModel() {
    val jokeListLiveData: MutableLiveData<String> = MutableLiveData<String>()

    fun getNewJoke() {
        val newJoke = JokeFetcher().fetchRandomJoke((1..1142).random())
        Log.d(TAG, newJoke)
        jokeListLiveData.value = newJoke
    }
}
package com.example.aneckdoter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val TAG = "JokeListViewModel"

class JokeListViewModel : ViewModel() {
    private val _jokeLiveData: MutableLiveData<String> = MutableLiveData()
    val jokeLiveData: LiveData<String> = _jokeLiveData

    fun getNewJoke() {
        MainScope().launch {
            _jokeLiveData.value = JokeFetcher().fetchJokeByNumber((0..1142).random()).await()
        }
    }
}
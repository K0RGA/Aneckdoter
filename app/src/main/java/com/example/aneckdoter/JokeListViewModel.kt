package com.example.aneckdoter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aneckdoter.network.JokeFetcher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val TAG = "JokeListViewModel"

class JokeListViewModel : ViewModel() {
    private val _jokeLiveData: MutableLiveData<String> = MutableLiveData()
    val jokeLiveData: LiveData<String> = _jokeLiveData

    fun getNewJoke() {
        MainScope().launch {
            _jokeLiveData.value = JokeFetcher().getRandomJoke().await()
        }
    }
}
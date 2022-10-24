package com.example.aneckdoter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class JokeListViewModel: ViewModel() {
        val jokeListLiveData: LiveData<String> = JokeFetcher().fetchRandomJoke(500)
}
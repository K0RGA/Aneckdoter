package com.example.aneckdoter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke
import com.example.aneckdoter.network.JokeFetcher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LikeListViewModel: ViewModel() {

    val repository = JokeRepository.get()
    val jokeLiveData: LiveData<List<Joke>> = repository.getLikeJokes()

    fun dislikeJoke(joke: Joke){
        repository.dislikeJoke(joke)
    }
}
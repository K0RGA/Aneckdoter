package com.example.aneckdoter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LikeListViewModel @Inject constructor(private val repository: JokeRepository): ViewModel() {

    val jokeLiveData: LiveData<List<Joke>> = repository.getLikeJokes()

    fun dislikeJoke(joke: Joke){
        repository.dislikeJoke(joke)
    }
}
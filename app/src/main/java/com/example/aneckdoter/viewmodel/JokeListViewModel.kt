package com.example.aneckdoter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke
import com.example.aneckdoter.network.JokeFetcher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val TAG = "JokeListViewModel"

class JokeListViewModel : ViewModel() {
    private val _jokeLiveData: MutableLiveData<MutableList<Joke>> = MutableLiveData()
    val jokeLiveData: LiveData<MutableList<Joke>> = _jokeLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    var currentJokeList = mutableListOf<Joke>()

    private val repository: JokeRepository = JokeRepository.get()
    val jokesFromDBLiveData: LiveData<List<Joke>> = repository.getLikeJokes()

    init {
        addNewJokes()
    }

    fun addNewJokes(){
        MainScope().launch {
            _isLoadingLiveData.value = true
            val listOfRandomNumber = List(BUFFER_SIZE){randomNumber()}
            for (randomNumber in listOfRandomNumber){
                val newJoke = JokeFetcher().fetchJokeByNumberAsync(randomNumber).await()
                currentJokeList.add(newJoke)
            }
            _isLoadingLiveData.value = false
            _jokeLiveData.value = currentJokeList
        }
    }

    fun randomNumber(): Int{
        return (Math.random() * 1142).toInt()
    }

    companion object {
        const val BUFFER_SIZE = 7
    }
}
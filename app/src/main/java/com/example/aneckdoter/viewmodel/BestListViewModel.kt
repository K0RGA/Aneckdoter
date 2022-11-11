package com.example.aneckdoter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke
import com.example.aneckdoter.network.JokeFetcher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val TAG = "BestListViewModel"

class BestListViewModel : ViewModel() {
    private val _jokeLiveData: MutableLiveData<MutableList<Joke>> = MutableLiveData()
    val jokeLiveData: LiveData<MutableList<Joke>> = _jokeLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    val jokesFromDBLiveData: LiveData<List<Joke>> = JokeRepository.get().getLikeJokes()

    var currentJokeList = mutableListOf<Joke>()

    private var alreadyLoaded = 0
    private var buffer = 7

    init {
        getBestJokeList()
    }

    fun getBestJokeList() {
        MainScope().launch {
            _isLoadingLiveData.value = true
            var listOfBestJokeNumbers = JokeFetcher().getBestJokesListAsync().await()
            listOfBestJokeNumbers = listOfBestJokeNumbers
                .filter { it !in listOfBestJokeNumbers.take(alreadyLoaded) }
            alreadyLoaded += buffer
            for (currentNumber in listOfBestJokeNumbers.take(buffer)) {
                val newJoke = JokeFetcher().fetchJokeByNumberAsync(currentNumber).await()
                currentJokeList.add(newJoke)
            }
            _isLoadingLiveData.value = false
            _jokeLiveData.value = currentJokeList
        }
    }
}
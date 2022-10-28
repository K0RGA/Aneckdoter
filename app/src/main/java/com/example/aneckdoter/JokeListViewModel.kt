package com.example.aneckdoter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aneckdoter.model.Joke
import com.example.aneckdoter.network.JokeFetcher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val TAG = "JokeListViewModel"

class JokeListViewModel : ViewModel() {
    private val _jokeLiveData: MutableLiveData<MutableList<Joke>> = MutableLiveData()
    val jokeLiveData: LiveData<MutableList<Joke>> = _jokeLiveData

    init {
        getListJoke()
    }

    fun getListJoke() {
        MainScope().launch {
            var listOfRandomJoke: MutableList<Joke> = mutableListOf()
            val listOfRandomNumber = List(10){(0..1142).random()}
            for (randomNumber in listOfRandomNumber){
                listOfRandomJoke.add(JokeFetcher().fetchJokeByNumber(randomNumber).await())
            }
            _jokeLiveData.value = listOfRandomJoke
        }
    }
}
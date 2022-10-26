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
    private val _jokeLiveData: MutableLiveData<List<Joke>> = MutableLiveData()
    val jokeLiveData: LiveData<List<Joke>> = _jokeLiveData

    fun getListJoke() {
        MainScope().launch {
            val listOfRandomNumber = List(20){(0..1142).random()}
            var listOfRandomJoke: MutableList<Joke> = mutableListOf()
            for (randomNumber in listOfRandomNumber){
                listOfRandomJoke.add(JokeFetcher().fetchJokeByNumber(randomNumber).await())
            }
            _jokeLiveData.value = listOfRandomJoke
        }
    }
}
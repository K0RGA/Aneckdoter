package com.example.aneckdoter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "JokeListViewModel"

class JokeListViewModel: ViewModel() {
        fun jokeListLiveData(): LiveData<List<String>>{
                val listOfRandomNumber = MutableList(50) { (1..1142).random() }
                val listOfJoke = mutableListOf<String>()
                for (i in listOfRandomNumber){
                        listOfJoke.add(JokeFetcher().fetchRandomJoke(i))
                        Log.d(TAG,JokeFetcher().fetchRandomJoke(i) )
                }
                val liveData: MutableLiveData<List<String>> = MutableLiveData()
                liveData.value = listOfJoke
                return liveData
        }
}
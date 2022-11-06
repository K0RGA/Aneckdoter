package com.example.aneckdoter.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aneckdoter.model.Joke

@Dao
interface JokeDao {
    @Query("select * from joke")
    fun getLikeJoke(): LiveData<List<Joke>>

    @Query("select number from joke")
    fun getNumbersOfLikeList() : LiveData<List<Int>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun likeJoke(joke: Joke)

    @Delete
    fun dislikeJoke(joke: Joke)
}
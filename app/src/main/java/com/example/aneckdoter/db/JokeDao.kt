package com.example.aneckdoter.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aneckdoter.model.Joke

@Dao
interface JokeDao {
    @Query("select * from joke")
    fun getLikeJoke(): LiveData<List<Joke>>

    @Insert
    fun likeJoke(joke: Joke)

    @Delete
    fun dislikeJoke(joke: Joke)
}
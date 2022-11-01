package com.example.aneckdoter.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.aneckdoter.model.Joke

@Dao
interface JokeDao {
    @Query("select * from joke")
    fun getLikeJoke(): LiveData<List<Joke>>
}
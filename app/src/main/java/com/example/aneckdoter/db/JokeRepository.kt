package com.example.aneckdoter.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.aneckdoter.model.Joke

private const val DATABASE_NAME = "joke-database"

class JokeRepository private constructor(context: Context){

    private val database : JokeDatabase = Room.databaseBuilder(
        context.applicationContext,
        JokeDatabase::class.java,
        DATABASE_NAME).build()

    private val jokeDao = database.jokeDao()

    fun getLikeJokes (): LiveData<List<Joke>> = jokeDao.getLikeJoke()

    companion object {
        private var INSTANCE: JokeRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = JokeRepository(context)
            }
        }

        fun get(): JokeRepository{
            return INSTANCE ?:
            throw IllegalStateException("JokeRepository must be initialized")
        }
    }


}
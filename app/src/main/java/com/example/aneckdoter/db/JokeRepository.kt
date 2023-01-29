package com.example.aneckdoter.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.aneckdoter.model.Joke
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

private const val DATABASE_NAME = "joke-database"
@Singleton
class JokeRepository @Inject constructor(@ApplicationContext context: Context) {

    private val database: JokeDatabase = Room.databaseBuilder(
        context.applicationContext,
        JokeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val jokeDao = database.jokeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getLikeJokes(): LiveData<List<Joke>> = jokeDao.getLikeJoke()

    fun likeJoke(joke: Joke) {
        executor.execute {
            jokeDao.likeJoke(joke)
        }
    }

    fun dislikeJoke(joke: Joke) {
        executor.execute {
            jokeDao.dislikeJoke(joke)
        }
    }
}
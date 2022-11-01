package com.example.aneckdoter.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aneckdoter.model.Joke

@Database(entities = [Joke::class], version = 1)
abstract class JokeDatabase: RoomDatabase() {

    abstract fun jokeDao(): JokeDao
}
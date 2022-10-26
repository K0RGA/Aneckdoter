package com.example.aneckdoter

import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "JokeFetch"

class JokeFetcher {
    private val jokeApi: JokeApi

    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://baneks.ru/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        jokeApi = retrofit.create(JokeApi::class.java)
    }


    suspend fun fetchJokeByNumber(numberOfJoke: Int): Deferred<String> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val response = jokeApi.getRandomJoke(numberOfJoke).body().toString().getJokeFromResponse()
                response
            } catch (e: Exception){
                "Default"
            }
        }
    }

    fun String.getJokeFromResponse(): String {
        return this
            .substringAfter("""name="description" content="""")
            .substringBefore("""">""")
    }
}
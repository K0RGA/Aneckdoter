package com.example.aneckdoter.network

import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.jsoup.Jsoup
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

    fun getRandomJoke(): Deferred<String>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val numberOfJoke = (0..1142).random()
                val response = Jsoup.connect("https://baneks.ru/${numberOfJoke}").get()
                val textOfJoke = response.select("p").text()
                textOfJoke
            } catch (e: Exception){
                "Default"
            }
        }
    }

}
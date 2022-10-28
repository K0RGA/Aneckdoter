package com.example.aneckdoter.network

import com.example.aneckdoter.model.Joke
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import retrofit2.*
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "JokeFetch"

class JokeFetcher {
    private val jokeApi: JokeApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://baneks.ru/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        jokeApi = retrofit.create(JokeApi::class.java)
    }


    suspend fun fetchJokeByNumber(numberOfJoke: Int): Deferred<Joke> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val textOfJoke =
                        jokeApi.getRandomJoke(numberOfJoke).body().toString().getJokeFromResponse()
                Joke(textOfJoke, numberOfJoke)
            } catch (e: Exception) {
                Joke("Default", 0)
            }
        }
    }

    private fun String.getJokeFromResponse(): String {
        return this
                .substringAfter("""name="description" content="""")
                .substringBefore("""">""")
    }

    fun getBestJokesList(): Deferred<List<Int>> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val numberOfJoke = (0..1142).random()
                val response = Jsoup.connect("https://baneks.ru/${numberOfJoke}").get()
                val listOfBestJoke = response.select("href").text().map { it.code }
                listOfBestJoke
            } catch (e: Exception) {
                MutableList(0) { 0 }
            }
        }
    }

}
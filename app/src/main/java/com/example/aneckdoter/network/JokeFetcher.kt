package com.example.aneckdoter.network

import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.aneckdoter.model.Joke
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import retrofit2.*
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.Executors

private const val TAG = "JokeFetcher"

class JokeFetcher {
    private val jokeApi: JokeApi
    private val executor = Executors.newSingleThreadExecutor()

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://baneks.ru/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        jokeApi = retrofit.create(JokeApi::class.java)
    }

    suspend fun fetchJokeByNumberAsync(numberOfJoke: Int): Deferred<Joke> {
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

    fun getBestJokesListAsync(): Deferred<List<Int>> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val response = Jsoup.connect("https://baneks.ru/top").get()
                val list = response.select("article").select("a")
                    .map { it.attr("href")}
                    .map { it.substringAfter("/").toInt()}
                list
            } catch (e: Exception) {
                Log.d(TAG, "getBestJokesListAsync error",e)
                MutableList(0) { 0 }
            }
        }
    }
}
package com.example.aneckdoter.network

import android.util.Log
import com.example.aneckdoter.model.Joke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "JokeFetcher"

@Singleton
class JokeFetcher @Inject constructor(private val jokeApi: JokeApi) {
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
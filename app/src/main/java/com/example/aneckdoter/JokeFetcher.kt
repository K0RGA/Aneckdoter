package com.example.aneckdoter

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "JokeFetch"

class JokeFetcher {
    private val jokeApi: JokeApi

    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://baneks.ru/")
           // .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        jokeApi = retrofit.create(JokeApi::class.java)
    }

    fun fetchRandomJoke(numberOfJoke: Int): String {
        var responseString: String = ""
        val jokeRequest: Call<String> = jokeApi.getRandomJoke(numberOfJoke)

        jokeRequest.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "Response received")
                responseString = response.body().toString()
                    .substringAfter("""name="description" content="""")
                    .substringBefore("""">""")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "Filed to fetch joke",t)
            }
        })
        return responseString
    }
}
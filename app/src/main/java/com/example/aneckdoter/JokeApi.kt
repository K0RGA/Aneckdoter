package com.example.aneckdoter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JokeApi {
    @GET("/{number}")
    fun getRandomJoke(@Path("number") number: Int): Call<String>
}
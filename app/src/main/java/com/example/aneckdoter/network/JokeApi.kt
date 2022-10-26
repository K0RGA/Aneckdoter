package com.example.aneckdoter.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JokeApi {
    @GET("/{number}")
    suspend fun getRandomJoke(@Path("number") number: Int): Response<String>
}
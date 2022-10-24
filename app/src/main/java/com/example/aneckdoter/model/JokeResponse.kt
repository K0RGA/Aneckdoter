package com.example.aneckdoter.model

import com.google.gson.annotations.SerializedName

class JokeResponse {
    @SerializedName("head")
    lateinit var head: String
}
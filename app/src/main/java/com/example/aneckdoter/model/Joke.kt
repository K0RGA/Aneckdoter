package com.example.aneckdoter.model

data class Joke(
    var joke: String,
    val id:Int,
    var isLiked: Boolean = false
    )

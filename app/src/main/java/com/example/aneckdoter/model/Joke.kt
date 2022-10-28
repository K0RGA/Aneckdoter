package com.example.aneckdoter.model

data class Joke(
        var text: String,
        val number: Int,
        var isLiked: Boolean = false
)

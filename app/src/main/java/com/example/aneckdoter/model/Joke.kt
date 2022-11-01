package com.example.aneckdoter.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Joke(
        var text: String,
       @PrimaryKey val number: Int,
        var isLiked: Boolean = false
)

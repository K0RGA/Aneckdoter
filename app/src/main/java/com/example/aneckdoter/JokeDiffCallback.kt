package com.example.aneckdoter

import androidx.recyclerview.widget.DiffUtil
import com.example.aneckdoter.model.Joke

class JokeDiffCallback : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }
}
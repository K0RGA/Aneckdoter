package com.example.aneckdoter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.aneckdoter.model.Joke

class JokeAdapter() :
    ListAdapter<Joke, JokeHolder>(JokeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.joke_list_item, parent, false)
        return JokeHolder(view)
    }

    override fun onBindViewHolder(holder: JokeHolder, position: Int) {
        val joke = getItem(position)
        holder.bind(joke)
    }
}
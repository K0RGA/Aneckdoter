package com.example.aneckdoter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class JokeHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view),
    View.OnLongClickListener {

    val context = view.context

    private val repository = JokeRepository.get()

    private var clipboard: ClipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    private val jokeText: TextView = view.findViewById(R.id.joke_text)
    private val likeButton: ToggleButton = view.findViewById(R.id.like_button)

    init {
        jokeText.setOnLongClickListener(this)
        likeButton.setOnClickListener {
            onItemClicked(bindingAdapterPosition)
        }
    }

    private lateinit var joke: Joke

    fun bind(joke: Joke) {
        this.joke = joke
        jokeText.text = joke.text
        likeButton.isChecked = joke.isLiked
    }

    override fun onLongClick(p0: View?): Boolean {
        val clip = ClipData.newPlainText("Text of joke", jokeText.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show()
        return true
    }
}
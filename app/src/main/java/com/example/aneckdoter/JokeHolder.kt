package com.example.aneckdoter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class JokeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener,
    View.OnLongClickListener {

    val context = view.context

    private val repository = JokeRepository.get()

    private var clipboard: ClipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    private var listOfLike = mutableListOf<Int>()


    private val jokeText: TextView = view.findViewById(R.id.joke_text)
    private val jokeNumber: TextView = view.findViewById(R.id.joke_number)
    private val likeButton: ImageButton = view.findViewById(R.id.like_button)

    init {
        jokeText.setOnLongClickListener(this)
        likeButton.setOnClickListener(this)
        MainScope().launch {
            listOfLike = repository.getListLikeJoke().await()
        }
    }

    private lateinit var joke: Joke

    fun bind(joke: Joke) {
        this.joke = joke
        jokeText.text = joke.text
        jokeNumber.text = joke.number.toString()
        joke.isLiked = joke.number in listOfLike
        //if (joke.isLiked) likeButton.setBackgroundColor(Color.BLUE)
    }

    override fun onLongClick(p0: View?): Boolean {
        val clip = ClipData.newPlainText("Text of joke", jokeText.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onClick(p0: View?) {
        val text = jokeText.text.toString()
        val number = jokeNumber.text.toString().toInt()
        repository.likeJoke(Joke(text, number, isLiked = true))
    }
}
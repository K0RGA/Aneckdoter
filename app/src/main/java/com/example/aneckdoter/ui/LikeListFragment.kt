package com.example.aneckdoter.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.LikeListViewModel
import com.example.aneckdoter.R
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke

private const val TAG = "Current fragment"

class LikeListFragment : Fragment() {
    private val likeListViewModel: LikeListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var clipboard: ClipboardManager
    private var adapter: JokeAdapter? = JokeAdapter(mutableListOf())
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading = false
    val repository = JokeRepository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        Log.d(TAG, "Like list fragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_like_list, container, false)

        recyclerView = view.findViewById(R.id.like_list_recycler_view)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        likeListViewModel.jokeLiveData.observe(
            viewLifecycleOwner,
            Observer { jokes ->
                jokes?.let {
                    updateUI(jokes)
                }
            }
        )
    }

    private fun updateUI(jokes: List<Joke>) {
        adapter?.updateAdapter(jokes)
    }

    private inner class JokeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnLongClickListener, View.OnClickListener {

        private val jokeText: TextView = view.findViewById(R.id.joke_text)
        private val jokeNumber: TextView = view.findViewById(R.id.joke_number)
        private val likeButton: ImageButton = view.findViewById(R.id.like_button)

        init {
            jokeText.setOnLongClickListener(this)
            likeButton.setOnClickListener(this)
        }

        private lateinit var joke: Joke

        fun bind(joke: Joke) {
            this.joke = joke
            jokeText.text = joke.text
            jokeNumber.text = joke.number.toString()
        }

        override fun onLongClick(p0: View?): Boolean {
            val clip = ClipData.newPlainText("Text of joke", jokeText.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Text copied", Toast.LENGTH_SHORT).show()
            return true
        }

        override fun onClick(p0: View?) {
            val text = jokeText.text.toString()
            val number = jokeNumber.text.toString().toInt()
            repository.dislikeJoke(Joke(text,number))
        }
    }

    private inner class JokeAdapter(var jokes: MutableList<Joke>) :
        RecyclerView.Adapter<JokeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
            val view = layoutInflater.inflate(R.layout.joke_list_item, parent, false)
            return JokeHolder(view)
        }

        override fun onBindViewHolder(holder: JokeHolder, position: Int) {
            val joke = jokes[position]
            holder.bind(joke)
        }

        override fun getItemCount(): Int = jokes.size

        fun updateAdapter(items: List<Joke>) {
            jokes.addAll(items)
            notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance() = LikeListFragment()
    }


}
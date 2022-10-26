package com.example.aneckdoter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.model.Joke

private const val TAG = "JokeListFragment"

class JokeListFragment : Fragment() {

    private val jokeListViewModel: JokeListViewModel by viewModels()
    private lateinit var likeButton: Button
    private lateinit var recyclerView: RecyclerView
    private var adapter: JokeAdapter? = JokeAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_joke_list, container, false)

        likeButton = view.findViewById(R.id.refresh_button)
        likeButton.setOnClickListener {
            jokeListViewModel.getListJoke()
        }

        recyclerView = view.findViewById(R.id.joke_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jokeListViewModel.jokeLiveData.observe(
            viewLifecycleOwner,
            Observer { jokes ->
                jokes?.let {
                    updateUI(jokes)
                }
            }
        )
    }

    private fun updateUI(jokes: List<Joke>) {
        adapter = JokeAdapter(jokes)
        recyclerView.adapter = adapter
    }

    private inner class JokeHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val jokeText: TextView = view.findViewById(R.id.joke_text)
        private val jokeNumber: TextView = view.findViewById(R.id.joke_number)

        private lateinit var joke: Joke

        fun bind(joke: Joke){
            this.joke = joke
            jokeText.text = joke.text
            jokeNumber.text = joke.number.toString()
        }
    }

    private inner class JokeAdapter(val jokes: List<Joke>):
        RecyclerView.Adapter<JokeHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
            val view = layoutInflater.inflate(R.layout.joke_list_item, parent, false)
            return JokeHolder(view)
        }

        override fun onBindViewHolder(holder: JokeHolder, position: Int) {
            val joke = jokes[position]
            holder.bind(joke)
        }

        override fun getItemCount(): Int = jokes.size
    }

    companion object {
        fun newInstance() = JokeListFragment()
    }

}
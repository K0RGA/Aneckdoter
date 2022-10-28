package com.example.aneckdoter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.model.Joke

private const val TAG = "JokeListFragment"

class JokeListFragment : Fragment() {

    private val jokeListViewModel: JokeListViewModel by viewModels()
    private lateinit var refreshButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var clipboard: ClipboardManager
    private var adapter: JokeAdapter? = JokeAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_joke_list, container, false)

        refreshButton = view.findViewById(R.id.refresh_button)
        refreshButton.setOnClickListener {
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

    private inner class JokeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val jokeText: TextView = view.findViewById(R.id.joke_text)
        private val jokeNumber: TextView = view.findViewById(R.id.joke_number)

        init {
            jokeText.setOnLongClickListener(this)
        }

        private lateinit var joke: Joke

        fun bind(joke: Joke){
            this.joke = joke
            jokeText.text = joke.text
            jokeNumber.text = joke.number.toString()
        }

        override fun onLongClick(p0: View?): Boolean {
            val clip = ClipData.newPlainText("Text of joke",jokeText.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Text copied",Toast.LENGTH_SHORT).show()
            return true
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
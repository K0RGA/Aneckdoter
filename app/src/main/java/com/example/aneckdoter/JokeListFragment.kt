package com.example.aneckdoter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.network.JokeFetcher

private const val TAG = "JokeListFragment"

class JokeListFragment : Fragment() {

    private val jokeListViewModel: JokeListViewModel by viewModels()
    private lateinit var jokeTextView: TextView
    private lateinit var likeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_joke_list, container, false)

        jokeTextView = view.findViewById(R.id.joke_text_view)
        likeButton = view.findViewById(R.id.like_button)

        likeButton.setOnClickListener {
            jokeListViewModel.getNewJoke()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jokeListViewModel.jokeLiveData.observe(
            viewLifecycleOwner,
            Observer { str ->
                jokeTextView.text = str
                Log.d(TAG, str)
            }
        )
    }

    private class JokeHolder(itemTextView: TextView) : RecyclerView.ViewHolder(itemTextView) {

        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

//    private  class JokeAdapter(private val joke: String):
//        RecyclerView.Adapter<JokeHolder>(){
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
//        }
//
//        override fun onBindViewHolder(holder: JokeHolder, position: Int) {
//        }
//
//        override fun getItemCount(): Int {
//        }
//    }

    companion object {
        fun newInstance() = JokeListFragment()
    }

}
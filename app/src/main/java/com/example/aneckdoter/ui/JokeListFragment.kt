package com.example.aneckdoter.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.JokeAdapter
import com.example.aneckdoter.JokeDiffCallback
import com.example.aneckdoter.JokeListViewModel
import com.example.aneckdoter.R
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke

private const val TAG = "Current fragment"

class JokeListFragment : Fragment() {

    private val jokeListViewModel: JokeListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private var adapter: JokeAdapter = JokeAdapter()
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_joke_list, container, false)
        createRecyclerView(view)
        return view
    }

    private fun createRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.joke_recycler_view)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        addScrollerListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jokeListViewModel.jokeLiveData.observe(
            viewLifecycleOwner
        ) { jokes ->
            adapter.submitList(jokes)
        }

        jokeListViewModel.isLoadingLiveData.observe(
            viewLifecycleOwner
        ) { isLoading ->
            this.isLoading = isLoading
        }
    }

    private fun addScrollerListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.currentList.size - 3) {
                        jokeListViewModel.addNewJokes()
                    }
                }
            }
        })
    }

    companion object {
        private var INSTANCE: JokeListFragment? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = JokeListFragment()
            }
        }

        fun get(): JokeListFragment {
            return INSTANCE ?: throw IllegalStateException("JokeRepository must be initialized")
        }
    }

}
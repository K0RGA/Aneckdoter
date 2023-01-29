package com.example.aneckdoter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.JokeAdapter
import com.example.aneckdoter.viewmodel.JokeListViewModel
import com.example.aneckdoter.R
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "JokeList"
private const val LOAD_THRESHOLD = 3

@AndroidEntryPoint
class JokeListFragment : Fragment() {

    private val jokeListViewModel: JokeListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading = false
    @Inject
    lateinit var repository: JokeRepository

    private var adapter: JokeAdapter = JokeAdapter() { joke, likeBtn ->
        val newJoke = Joke(joke.text, joke.number, isLiked = true)
        repository.likeJoke(newJoke)
        if (likeBtn.isChecked) {
            repository.likeJoke(joke)
            joke.isLiked = true
            adapterChange(joke)
        } else {
            repository.dislikeJoke(joke)
            joke.isLiked = false
            adapterChange(joke)
        }
    }

    fun adapterChange(joke: Joke) {
        adapter.notifyItemChanged(adapter.currentList.indexOf(joke))
    }

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
            adapter.submitList(ArrayList(jokes))
        }

        jokeListViewModel.isLoadingLiveData.observe(
            viewLifecycleOwner
        ) { isLoading ->
            this.isLoading = isLoading
            if (isLoading) {
                adapter.addLoadingView()
            } else {
                adapter.deleteLoadingView()
            }
        }

        jokeListViewModel.jokesFromDBLiveData.observe(
            viewLifecycleOwner
        ) { list ->
            if (list.isNotEmpty() && adapter.currentList.isNotEmpty()) {
                adapter.currentList.forEach {
                    if (it != null) it.isLiked = it in list
                }
            } else {
                adapter.currentList.forEach {
                    if (it != null) it.isLiked = false
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun addScrollerListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (layoutManager.findLastVisibleItemPosition() ==
                        adapter.currentList.size - LOAD_THRESHOLD
                    ) {
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
package com.example.aneckdoter.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.viewmodel.BestListViewModel
import com.example.aneckdoter.JokeAdapter
import com.example.aneckdoter.R
import com.example.aneckdoter.db.JokeRepository
import com.example.aneckdoter.model.Joke

private const val TAG = "BestListFragment"
private const val LOAD_THRESHOLD = 3

class BestListFragment : Fragment() {
    private val bestListViewModel: BestListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val repository = JokeRepository.get()
    private var isLoading = false

    private var adapter: JokeAdapter = JokeAdapter { joke, likeBtn ->
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

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_list, container, false)
        createRecyclerView(view)
        return view
    }

    private fun createRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.best_joke_recycler_view)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        addScrollerListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createListeners()
    }

    private fun createListeners() {
        bestListViewModel.jokeLiveData.observe(
            viewLifecycleOwner
        ) { jokes ->
            adapter.submitList(ArrayList(jokes))
            Log.d(TAG, jokes.size.toString())
        }

        bestListViewModel.isLoadingLiveData.observe(
            viewLifecycleOwner
        ) { isLoading ->
            this.isLoading = isLoading
            if (isLoading) {
                adapter.addLoadingView()
            } else {
                adapter.deleteLoadingView()
            }
        }

        bestListViewModel.jokesFromDBLiveData.observe(
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
                        bestListViewModel.getBestJokeList()
                    }
                }
            }
        })
    }

    companion object {
        private var INSTANCE: BestListFragment? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = BestListFragment()
            }
        }

        fun get(): BestListFragment {
            return INSTANCE ?: throw IllegalStateException("JokeRepository must be initialized")
        }
    }
}
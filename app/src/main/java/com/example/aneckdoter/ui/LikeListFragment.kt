package com.example.aneckdoter.ui

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aneckdoter.JokeAdapter
import com.example.aneckdoter.viewmodel.LikeListViewModel
import com.example.aneckdoter.R
import com.example.aneckdoter.db.JokeRepository

private const val TAG = "Current fragment"

class LikeListFragment : Fragment() {
    private val likeListViewModel: LikeListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var clipboard: ClipboardManager
    private var adapter: JokeAdapter = JokeAdapter(){ joke, likebtn ->
        joke.isLiked = true
        repository.likeJoke(joke)
        if (likebtn.isChecked){
            repository.likeJoke(joke)
        } else {
            repository.dislikeJoke(joke)
        }
    }
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
                    adapter.submitList(jokes)
                }
            }
        )
    }

    companion object {
        fun newInstance() = LikeListFragment()
    }
}
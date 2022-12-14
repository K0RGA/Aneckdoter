package com.example.aneckdoter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aneckdoter.model.Joke

private const val VIEW_TYPE_ITEM = 0
private const val VIEW_TYPE_LOAD = 1

class JokeAdapter(private val onItemClicked: (Joke, ToggleButton) -> Unit) :
    ListAdapter<Joke, ViewHolder>(JokeDiffCallback()) {

    private class LoadHolder(view: View) : ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.joke_list_item, parent, false)
            JokeHolder(view){
                onItemClicked(currentList[it], view.findViewById(R.id.like_button))
            }
        } else {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_loading, parent, false)
            LoadHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is JokeHolder) {
            val joke = getItem(position)
            holder.bind(joke)
        }

    }

    fun addLoadingView() {
        submitList(ArrayList(currentList + null))
        notifyItemInserted(currentList.lastIndex)
    }

    fun deleteLoadingView() {
        if (currentList.size != 0) {
            submitList(ArrayList(currentList - currentList.last()))
            notifyItemRemoved(currentList.size)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) {
            VIEW_TYPE_LOAD
        } else {
            VIEW_TYPE_ITEM
        }
    }
}
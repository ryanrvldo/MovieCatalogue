package com.ryanrvldo.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ryanrvldo.moviecatalogue.adapter.SearchHistoryAdapter.SearchViewHolder
import com.ryanrvldo.moviecatalogue.data.model.Search
import com.ryanrvldo.moviecatalogue.databinding.ItemRecentSearchBinding
import java.util.*

// TODO: 9/1/20 Add to adapter module
class SearchHistoryAdapter(
    private val searchList: ArrayList<Search>,
    private val onItemClicked: OnItemClicked
) : RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemRecentSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchItem = searchList[position]
        holder.binding.tvQuery.text = searchItem.query
        holder.binding.clearRecentItem.setOnClickListener {
            onItemClicked.onDeleteClick(searchItem)
        }
        holder.itemView.setOnClickListener {
            onItemClicked.onSearchClick(searchItem)
        }
    }

    override fun getItemCount(): Int = searchList.size

    fun refillSearch(searchList: List<Search>) {
        this.searchList.clear()
        this.searchList.addAll(searchList)
        notifyDataSetChanged()
    }

    interface OnItemClicked {
        fun onDeleteClick(search: Search)
        fun onSearchClick(search: Search)
    }

    class SearchViewHolder(val binding: ItemRecentSearchBinding) :
        RecyclerView.ViewHolder(binding.root)
}
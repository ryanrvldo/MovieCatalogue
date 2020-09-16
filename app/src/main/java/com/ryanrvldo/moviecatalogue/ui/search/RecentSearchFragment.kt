package com.ryanrvldo.moviecatalogue.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.SearchHistoryAdapter
import com.ryanrvldo.moviecatalogue.data.model.Search
import com.ryanrvldo.moviecatalogue.ui.viewmodel.RecentSearchViewModel
import com.ryanrvldo.moviecatalogue.ui.viewmodel.SearchViewModel.Companion.SEARCH_QUERY_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recent_search.*
import java.util.*

@AndroidEntryPoint
class RecentSearchFragment : Fragment() {

    private lateinit var historyAdapter: SearchHistoryAdapter
    private val viewModel: RecentSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recent_search, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        rv_recent_search.setHasFixedSize(true)
        rv_recent_search.layoutManager = LinearLayoutManager(context)
        historyAdapter = SearchHistoryAdapter(ArrayList(), onItemClicked)
        rv_recent_search.adapter = historyAdapter
        viewModel.searchHistories.observe(viewLifecycleOwner) { searchList ->
            searchList?.let {
                historyAdapter.refillSearch(it)
            }
        }
    }

    private val onItemClicked: SearchHistoryAdapter.OnItemClicked =
        object : SearchHistoryAdapter.OnItemClicked {
            override fun onDeleteClick(search: Search) {
                viewModel.removeSearchQuery(search)
            }

            override fun onSearchClick(search: Search) {
                val intent = Intent(context, SearchActivity::class.java)
                intent.putExtra(SEARCH_QUERY_KEY, search.query)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
}
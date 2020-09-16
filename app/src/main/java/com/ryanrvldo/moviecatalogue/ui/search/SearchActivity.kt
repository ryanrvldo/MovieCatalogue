package com.ryanrvldo.moviecatalogue.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.data.model.Search
import com.ryanrvldo.moviecatalogue.ui.viewmodel.RecentSearchViewModel
import com.ryanrvldo.moviecatalogue.ui.viewmodel.SearchViewModel.Companion.SEARCH_QUERY_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val recentViewModel: RecentSearchViewModel by viewModels()

    private val pageContent: Fragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        intent.getStringExtra(SEARCH_QUERY_KEY)?.let {
            searchQuery = it
        }
        title = getString(R.string.search_title) + " " + searchQuery
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, pageContent)
            .commit()
        toolbar_search.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (recentViewModel.selectSearch(query) == null) {
                        recentViewModel.addSearchQuery(Search(query))
                    }
                }
                searchQuery = query
                title = getString(R.string.search_title) + " " + searchQuery
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, pageContent).commit()
                toolbar_search.title = title
                invalidateOptionsMenu()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        val itemSearch = menu.findItem(R.id.search)
        itemSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RecentSearchFragment()).commit()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, pageContent).commit()
                return true
            }
        })
        return true
    }

    companion object {
        lateinit var searchQuery: String
    }
}
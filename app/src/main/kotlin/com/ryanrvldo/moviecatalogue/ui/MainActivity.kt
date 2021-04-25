package com.ryanrvldo.moviecatalogue.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.customview.widget.Openable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.data.model.Search
import com.ryanrvldo.moviecatalogue.databinding.ActivityMainBinding
import com.ryanrvldo.moviecatalogue.ui.search.SearchActivity
import com.ryanrvldo.moviecatalogue.ui.viewmodel.RecentSearchViewModel
import com.ryanrvldo.moviecatalogue.ui.viewmodel.SearchViewModel.Companion.SEARCH_QUERY_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: RecentSearchViewModel by viewModels()
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.navigationView, navController)

        appBarConfig = AppBarConfiguration.Builder(
            R.id.menu_movie, R.id.menu_tv_show, R.id.menu_favorite
        ).setOpenableLayout(openable)
            .build()
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private val openable: Openable = object : Openable {
        override fun isOpen(): Boolean {
            return binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)
        }

        override fun open() {
            binding.mainDrawerLayout.openDrawer(GravityCompat.START)
        }

        override fun close() {
            binding.mainDrawerLayout.closeDrawers()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Find something here.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    if (viewModel.selectSearch(query) == null) {
                        viewModel.addSearchQuery(
                            Search(query)
                        )
                    }
                }
                val searchIntent = Intent(this@MainActivity, SearchActivity::class.java)
                searchIntent.putExtra(SEARCH_QUERY_KEY, query)
                startActivity(searchIntent)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                    navController.navigate(R.id.recentSearchFragment)
                    return true
                }

                override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                    onSupportNavigateUp()
                    return true
                }
            })
            return true
        }
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_fragment_container)
        return NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawers()
            return
        }
        super.onBackPressed()
    }
}

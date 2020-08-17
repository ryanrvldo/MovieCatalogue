package com.ryanrvldo.moviecatalogue.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.customview.widget.Openable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ryanrvldo.moviecatalogue.R;
import com.ryanrvldo.moviecatalogue.data.source.model.Search;
import com.ryanrvldo.moviecatalogue.databinding.ActivityMainBinding;
import com.ryanrvldo.moviecatalogue.ui.search.SearchActivity;
import com.ryanrvldo.moviecatalogue.viewmodel.RecentSearchViewModel;
import com.ryanrvldo.moviecatalogue.viewmodel.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private RecentSearchViewModel viewModel;

    private AppBarConfiguration appBarConfig;
    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        navController = Navigation.findNavController(this, R.id.nav_fragment_container);
        NavigationUI.setupWithNavController(binding.navigationView, navController);
        appBarConfig = new AppBarConfiguration.Builder(
                R.id.menu_movie,
                R.id.menu_tv_show)
                .setOpenableLayout(openable)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
        viewModel = obtainViewModel(this);
    }

    @NonNull
    private static RecentSearchViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(RecentSearchViewModel.class);
    }

    private Openable openable = new Openable() {
        @Override
        public boolean isOpen() {
            return binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START);
        }

        @Override
        public void open() {
            binding.mainDrawerLayout.openDrawer(GravityCompat.START);
        }

        @Override
        public void close() {
            binding.mainDrawerLayout.closeDrawers();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (viewModel.selectSearch(query) == null) {
                    viewModel.insertSearch(new Search(query));
                }
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.putExtra(SearchActivity.SEARCH_QUERY, query);
                startActivity(searchIntent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setQueryHint("Find something here..");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    navController.navigate(R.id.recentSearchFragment);
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    onSupportNavigateUp();
                    return true;
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment_container);
        return NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawers();
        }
        super.onBackPressed();
    }
}
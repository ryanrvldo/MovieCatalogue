package com.dicoding.moviecataloguerv.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.model.Search;
import com.dicoding.moviecataloguerv.ui.favorite.FavoriteFragment;
import com.dicoding.moviecataloguerv.ui.movie.MovieFragment;
import com.dicoding.moviecataloguerv.ui.search.RecentSearchFragment;
import com.dicoding.moviecataloguerv.ui.search.SearchActivity;
import com.dicoding.moviecataloguerv.ui.setting.SettingActivity;
import com.dicoding.moviecataloguerv.ui.tvShow.TvShowFragment;
import com.dicoding.moviecataloguerv.viewmodel.RecentSearchViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public Fragment pageContent = new MovieFragment();
    private BottomNavigationView bottomNavigation;
    public boolean isSearch;

    private RecentSearchViewModel viewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.menu_movie);
        }

        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(RecentSearchViewModel.class);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.menu_movie:
                pageContent = new MovieFragment();
                break;
            case R.id.menu_tv_show:
                pageContent = new TvShowFragment();
                break;
            case R.id.menu_favorite:
                pageContent = new FavoriteFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
        return true;
    };

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Search search = new Search(query);
                    if (viewModel.selectSearch(query) == null) {
                        viewModel.addSearch(search);
                    }
                    Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                    searchIntent.putExtra(SearchActivity.SEARCH_QUERY, query);
                    startActivity(searchIntent);
                    isSearch = false;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
                    invalidateOptionsMenu();
                    bottomNavigation.setVisibility(View.VISIBLE);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        MenuItem itemSearch = menu.findItem(R.id.search);

        itemSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            MenuItem itemSetting = menu.findItem(R.id.setting);
            MenuItem itemDelete = menu.findItem(R.id.delete_recent);
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                itemSetting.setVisible(false);
                itemDelete.setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (isSearch) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
                    bottomNavigation.setVisibility(View.VISIBLE);
                    itemSetting.setVisible(true);
                    invalidateOptionsMenu();
                    itemDelete.setVisible(false);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (item.getItemId() == R.id.search) {
            bottomNavigation.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecentSearchFragment()).commit();
            isSearch = true;
        } else if (item.getItemId() == R.id.delete_recent) {
            viewModel.deleteAllSearch();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
        bottomNavigation.setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
    }
}
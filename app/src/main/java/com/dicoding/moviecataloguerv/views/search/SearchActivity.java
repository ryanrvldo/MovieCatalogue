package com.dicoding.moviecataloguerv.views.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.data.source.model.Search;
import com.dicoding.moviecataloguerv.viewmodel.RecentSearchViewModel;
import com.dicoding.moviecataloguerv.viewmodel.SearchViewModel;
import com.dicoding.moviecataloguerv.viewmodel.ViewModelFactory;

public class SearchActivity extends AppCompatActivity {

    public static String SEARCH_QUERY = "query";

    public static String searchQuery;
    private String title;
    private Toolbar toolbar;
    private RecentSearchViewModel recentViewModel;
    private SearchViewModel searchViewModel;
    private Fragment pageContent = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        searchQuery = getIntent().getStringExtra(SEARCH_QUERY);

        recentViewModel = obtainRecentSearchViewModel(this);
        searchViewModel = obtainSearchViewModel(this);
        title = getString(R.string.search_title) + " " + searchQuery;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
        toolbar.setTitle(title);
    }

    @NonNull
    private static RecentSearchViewModel obtainRecentSearchViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(RecentSearchViewModel.class);
    }

    @NonNull
    private static SearchViewModel obtainSearchViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(SearchViewModel.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Search search = new Search(query);
                    if (recentViewModel.selectSearch(query) == null) {
                        recentViewModel.insertSearch(search);
                    }
                    searchQuery = query;
                    title = getString(R.string.search_title) + " " + searchQuery;
                    searchViewModel.setSearchMovies(searchQuery);
                    searchViewModel.setSearchTv(searchQuery);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
                    toolbar.setTitle(title);
                    invalidateOptionsMenu();
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
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecentSearchFragment()).commit();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
                return true;
            }
        });
        return true;
    }
}

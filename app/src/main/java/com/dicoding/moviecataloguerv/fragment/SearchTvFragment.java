package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.SearchActivity;
import com.dicoding.moviecataloguerv.activity.TvShowDetailActivity;
import com.dicoding.moviecataloguerv.adapter.TvShowsAdapter;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowResponse;
import com.dicoding.moviecataloguerv.viewmodel.TvShowsViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTvFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private TvShowsAdapter tvShowsAdapter;
    private TvShowsViewModel tvShowsViewModel;

    private String language;


    public SearchTvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        language = getResources().getString(R.string.language);
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvMovies);

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setTvShowsRV();
        tvShowsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        observeData();
    }

    private void setTvShowsRV() {
        if (tvShowsAdapter == null) {
            tvShowsAdapter = new TvShowsAdapter(new ArrayList<TvShowItems>(), new ArrayList<Genre>(), onItemClicked);
            recyclerView.setAdapter(tvShowsAdapter);
        }
    }

    private void observeData() {
        tvShowsViewModel.getSearchTv(language, SearchActivity.searchQuery).observe(this, new Observer<TvShowResponse>() {
            @Override
            public void onChanged(TvShowResponse tvShowResponse) {
                if (tvShowResponse != null) {
                    tvShowsAdapter.refillTv(tvShowResponse.getTvShowItems());
                }
            }
        });
        tvShowsViewModel.getGenres(language).observe(this, new Observer<GenresResponse>() {
            @Override
            public void onChanged(GenresResponse genresResponse) {
                if (genresResponse != null) {
                    tvShowsAdapter.refillGenre(genresResponse.getGenres());
                }
            }
        });
        Log.d("FragmentSearchTv", "Loaded");
    }

    private TvShowsAdapter.OnItemClicked onItemClicked = new TvShowsAdapter.OnItemClicked() {
        @Override
        public void onItemClick(TvShowItems tvShowItems) {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShowItems.getId());
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        tvShowsViewModel.setSearchTv(language, SearchActivity.searchQuery);
        tvShowsViewModel.setGenres(language);
        observeData();
        refreshLayout.setRefreshing(false);
    }
}

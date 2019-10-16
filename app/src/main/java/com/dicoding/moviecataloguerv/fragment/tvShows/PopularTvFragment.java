package com.dicoding.moviecataloguerv.fragment.tvShows;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dicoding.moviecataloguerv.R;
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
public class PopularTvFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView tvShowsRV;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private TvShowsAdapter tvShowsAdapter;
    private TvShowsViewModel tvShowsViewModel;

    private String language;

    private Observer<TvShowResponse> getTvShows = new Observer<TvShowResponse>() {
        @Override
        public void onChanged(TvShowResponse tvShowResponse) {
            if (tvShowResponse != null) {
                tvShowsAdapter.refillTv(tvShowResponse.getTvShowItems());
            }
        }
    };
    private Observer<GenresResponse> getGenres = new Observer<GenresResponse>() {
        @Override
        public void onChanged(GenresResponse genresResponse) {
            if (genresResponse != null) {
                tvShowsAdapter.refillGenre(genresResponse.getGenres());
                showLoading(false);
            }
        }
    };
    private TvShowsAdapter.OnItemClicked onItemClicked = new TvShowsAdapter.OnItemClicked() {
        @Override
        public void onItemClick(TvShowItems tvShowItems) {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TV_SHOW_ID, tvShowItems.getId());
            startActivity(intent);
        }
    };

    public PopularTvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        language = getResources().getString(R.string.language);
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvShowsRV = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progressBar);

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        tvShowsRV.setHasFixedSize(true);
        tvShowsRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        setTvShowsRV();
        showLoading(true);
        tvShowsViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        observeData();
    }

    private void observeData() {
        tvShowsViewModel.getPopularTv(getResources().getString(R.string.language)).observe(getActivity(), getTvShows);
        tvShowsViewModel.getGenres(getResources().getString(R.string.language)).observe(getActivity(), getGenres);
        Log.d("FragmentPopularTV", "Loaded");
    }

    private void setTvShowsRV() {
        if (tvShowsAdapter == null) {
            tvShowsAdapter = new TvShowsAdapter(new ArrayList<TvShowItems>(), new ArrayList<Genre>(), onItemClicked);
            tvShowsRV.setAdapter(tvShowsAdapter);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            tvShowsRV.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            tvShowsRV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        tvShowsViewModel.setPopularTv(language);
        tvShowsViewModel.setGenres(language);
        observeData();
        refreshLayout.setRefreshing(false);
    }
}
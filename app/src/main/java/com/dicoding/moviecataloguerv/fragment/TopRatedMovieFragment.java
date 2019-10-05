package com.dicoding.moviecataloguerv.fragment;


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
import com.dicoding.moviecataloguerv.activity.MovieDetailActivity;
import com.dicoding.moviecataloguerv.adapter.MoviesAdapter;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.viewmodel.MoviesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView moviesRV;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private MoviesAdapter moviesAdapter;
    private MoviesViewModel moviesViewModel;

    private String language;

    private Observer<MovieResponse> getTopRated = new Observer<MovieResponse>() {
        @Override
        public void onChanged(MovieResponse movieResponse) {
            if (movieResponse != null) {
                moviesAdapter.refillMovie(movieResponse.getMovieItems());
            }
        }

    };
    private Observer<GenresResponse> getGenres = new Observer<GenresResponse>() {
        @Override
        public void onChanged(GenresResponse genresResponse) {
            moviesAdapter.refillGenre(genresResponse.getGenres());
            showLoading(false);
        }
    };
    private MoviesAdapter.OnItemClicked onItemClicked = new MoviesAdapter.OnItemClicked() {
        @Override
        public void onItemClick(MovieItems movieItems) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_ID, movieItems.getId());
            startActivity(intent);
        }
    };

    public TopRatedMovieFragment() {
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
        moviesRV = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progressBar);

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        moviesRV.setHasFixedSize(true);
        moviesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        setMoviesRV();
        showLoading(true);
        moviesViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        observeData();
    }

    private void observeData() {
        moviesViewModel.getTopRated(getResources().getString(R.string.language)).observe(getActivity(), getTopRated);
        moviesViewModel.getGenres(getResources().getString(R.string.language)).observe(getActivity(), getGenres);
        Log.d("FragmentMovieTopRated", "Loaded");
    }

    private void setMoviesRV() {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(new ArrayList<MovieItems>(), new ArrayList<Genre>(), onItemClicked);
            moviesRV.setAdapter(moviesAdapter);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            moviesRV.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            moviesRV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        moviesViewModel.setTopRatedMovies(language);
        moviesViewModel.setGenres(language);
        observeData();
        refreshLayout.setRefreshing(false);
    }
}

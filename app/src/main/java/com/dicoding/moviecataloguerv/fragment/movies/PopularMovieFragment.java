package com.dicoding.moviecataloguerv.fragment.movies;


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

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView moviesRV;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private MoviesAdapter moviesAdapter;
    private MoviesViewModel moviesViewModel;

    private String language;

    private Observer<MovieResponse> getMovies = new Observer<MovieResponse>() {
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
            if (genresResponse != null) {
                moviesAdapter.refillGenre(genresResponse.getGenres());
                showLoading(false);
            }
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

    public PopularMovieFragment() {
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
        super.onViewCreated(view, savedInstanceState);

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
        moviesViewModel.getPopularMovies(language).observe(getActivity(), getMovies);
        moviesViewModel.getGenres(language).observe(getActivity(), getGenres);
        Log.d("FragmentPopularMovies", "Loaded");
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
            moviesRV.setVisibility(GONE);
        } else {
            progressBar.setVisibility(GONE);
            moviesRV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        moviesViewModel.setPopularMovies(language);
        moviesViewModel.setGenres(language);
        observeData();
        refreshLayout.setRefreshing(false);
    }
}
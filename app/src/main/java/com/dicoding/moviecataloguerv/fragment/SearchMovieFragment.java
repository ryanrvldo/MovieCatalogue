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
import com.dicoding.moviecataloguerv.activity.MovieDetailActivity;
import com.dicoding.moviecataloguerv.activity.SearchActivity;
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
public class SearchMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private MoviesAdapter moviesAdapter;
    private MoviesViewModel moviesViewModel;

    private String language;

    public SearchMovieFragment() {
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

        setMoviesRV();
        moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        observeData();
    }

    private void observeData() {
        moviesViewModel.getSearchMovies(language, SearchActivity.searchQuery).observe(this, new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                if (movieResponse != null) {
                    moviesAdapter.refillMovie(movieResponse.getMovieItems());
                }
            }
        });
        moviesViewModel.getGenres(language).observe(this, new Observer<GenresResponse>() {
            @Override
            public void onChanged(GenresResponse genresResponse) {
                if (genresResponse != null) {
                    moviesAdapter.refillGenre(genresResponse.getGenres());
                }
            }
        });
        Log.d("FragmentSearchMovies", "Loaded");
    }

    private void setMoviesRV() {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(new ArrayList<MovieItems>(), new ArrayList<Genre>(), onItemClicked);
            recyclerView.setAdapter(moviesAdapter);
        }
    }

    private MoviesAdapter.OnItemClicked onItemClicked = new MoviesAdapter.OnItemClicked() {
        @Override
        public void onItemClick(MovieItems movieItems) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_ID, movieItems.getId());
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        moviesViewModel.setSearchMovies(language, SearchActivity.searchQuery);
        moviesViewModel.setGenres(language);
        observeData();
        refreshLayout.setRefreshing(false);
    }
}

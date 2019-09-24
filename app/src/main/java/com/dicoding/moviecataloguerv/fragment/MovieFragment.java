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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.MovieDetailActivity;
import com.dicoding.moviecataloguerv.adapter.MoviesAdapter;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private ArrayList<MovieItems> movieItemsArrayList = new ArrayList<>();
    private ArrayList<Genre> genreArrayList = new ArrayList<>();

    private RecyclerView moviesRV;
    private ProgressBar progressBar;

    private MoviesAdapter moviesAdapter;

    private Observer<MovieResponse> getMovies = new Observer<MovieResponse>() {
        @Override
        public void onChanged(MovieResponse movieResponse) {
            ArrayList<MovieItems> movieItems = movieResponse.getMovieItems();
            movieItemsArrayList.addAll(movieItems);
            moviesAdapter.notifyDataSetChanged();
        }

    };
    private Observer<GenresResponse> getGenres = new Observer<GenresResponse>() {
        @Override
        public void onChanged(GenresResponse genresResponse) {
            ArrayList<Genre> genreItems = genresResponse.getGenres();
            genreArrayList.addAll(genreItems);
            moviesAdapter.notifyDataSetChanged();
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

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moviesRV = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progressBar);

        showLoading(true);
        MoviesViewModel moviesViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MoviesViewModel.class);

        Log.d("FragmentMovie", "Loaded");
        moviesViewModel.getMovies(getResources().getString(R.string.language)).observe(getActivity(), getMovies);
        moviesViewModel.getGenres(getResources().getString(R.string.language)).observe(getActivity(), getGenres);

        setMoviesRV();
    }

    private void setMoviesRV() {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(movieItemsArrayList, getActivity(), genreArrayList, onItemClicked);
            moviesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            moviesRV.setAdapter(moviesAdapter);
        } else {
            moviesAdapter.notifyDataSetChanged();
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
}
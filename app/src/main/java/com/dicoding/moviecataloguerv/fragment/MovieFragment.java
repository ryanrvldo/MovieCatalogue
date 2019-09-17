package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.MovieDetailActivity;
import com.dicoding.moviecataloguerv.adapter.MoviesAdapter;
import com.dicoding.moviecataloguerv.model.Genre;
import com.dicoding.moviecataloguerv.model.Movie;
import com.dicoding.moviecataloguerv.model.MoviesData;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.getMoviesCallback;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;

    private MoviesData moviesData;
    private MoviesAdapter.OnItemClicked onItemClicked = new MoviesAdapter.OnItemClicked() {
        @Override
        public void onItemClick(Movie movie) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.getId());
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

        moviesList = view.findViewById(R.id.rvMovies);

        moviesData = MoviesData.getInstance();
        moviesList.setLayoutManager(new LinearLayoutManager(getActivity()));

        getGenres();
    }

    private void getGenres() {
        moviesData.getGenres(new getGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                getMovies(genres);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getMovies(final List<Genre> genres) {
        moviesData.getMovies(new getMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MoviesAdapter(movies, getActivity(), genres, onItemClicked);
                moviesList.setAdapter(adapter);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(getContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }

}

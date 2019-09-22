package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MoviesRepo;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.getMoviesCallback;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView moviesRV;
    private ProgressBar progressBar;

    private MoviesRepo moviesRepo;
    private MoviesAdapter adapter;


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
        moviesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        moviesRepo = MoviesRepo.getInstance();
        setGenres();
    }

    private void setGenres() {
        moviesRepo.getGenres(getResources().getString(R.string.language), new getGenresCallback() {
            @Override
            public void onSuccess(ArrayList<Genre> genres) {
                setMovies(genres);
                showLoading(false);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void setMovies(final ArrayList<Genre> genres) {
        moviesRepo.getMovies(getResources().getString(R.string.language), new getMoviesCallback() {
            @Override
            public void onSuccess(ArrayList<MovieItems> movieItems) {
                adapter = new MoviesAdapter(movieItems, getActivity(), genres, onItemClicked);
                moviesRV.setAdapter(adapter);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private MoviesAdapter.OnItemClicked onItemClicked = new MoviesAdapter.OnItemClicked() {
        @Override
        public void onItemClick(MovieItems movieItems) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_ID, movieItems.getId());
            startActivity(intent);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            moviesRV.setVisibility(View.GONE);
        } else {
            moviesRV.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showError() {
        Toast.makeText(getContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }

}
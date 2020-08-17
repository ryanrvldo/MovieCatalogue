package com.ryanrvldo.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ryanrvldo.moviecatalogue.data.Repository;
import com.ryanrvldo.moviecatalogue.data.source.model.Movie;

public class MovieDetailViewModel extends ViewModel {
    private final static String TAG = "movie";

    private LiveData<Movie> movie;

    private Repository repository;

    public MovieDetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Movie> getMovieDetail(int movieId) {
        if (movie == null) {
            movie = new MutableLiveData<>();
            movie = repository.getMovieDetail(movieId);
        }
        return movie;
    }

    public void insertFavMovie(Movie movie) {
        repository.insertFavMovie(movie);
    }

    public Movie getFavMovie(int movieId) {
        return repository.getFavMovie(movieId);
    }

    public void deleteFavMovie(Movie movie) {
        repository.deleteFavMovie(movie);
    }
}
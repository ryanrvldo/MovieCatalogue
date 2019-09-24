package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<MovieResponse> movies;
    private MutableLiveData<GenresResponse> genres;
    private MutableLiveData<MovieItems> itemsMovie;
    private MutableLiveData<TrailerResponse> trailers;

    private Repository repository;

    public LiveData<MovieResponse> getMovies(String language) {
        if (movies == null) {
            movies = new MutableLiveData<>();
            repository = Repository.getInstance();
            movies = repository.getMovies(language);
            Log.d("FragmentMovies", "Created");
        }
        return movies;
    }

    public LiveData<GenresResponse> getGenres(String language) {
        if (genres == null) {
            genres = new MutableLiveData<>();
            repository = Repository.getInstance();
            genres = repository.getMovieGenres(language);
        }
        return genres;
    }

    public LiveData<MovieItems> getMovieItems(int movieId, String language) {
        if (itemsMovie == null) {
            itemsMovie = new MutableLiveData<>();
            repository = Repository.getInstance();
            itemsMovie = repository.getMovieItems(movieId, language);
            Log.d("MovieDetail", "Created!");
        }
        return itemsMovie;
    }

    public LiveData<TrailerResponse> getTrailers(int movieId) {
        if (trailers == null) {
            trailers = new MutableLiveData<>();
            repository = Repository.getInstance();
            trailers = repository.getMovieTrailers(movieId);
        }
        return trailers;
    }

}

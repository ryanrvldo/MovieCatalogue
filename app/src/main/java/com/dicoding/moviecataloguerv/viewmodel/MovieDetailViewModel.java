package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.CreditsResponse;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.Movie;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<GenresResponse> genres;
    private MutableLiveData<Movie> itemsMovie;
    private MutableLiveData<TrailerResponse> trailers;
    private MutableLiveData<SimilarResponse> similar;
    private MutableLiveData<CreditsResponse> credits;

    private Repository repository = Repository.getInstance();
    private final static String type = "movie";

    public LiveData<GenresResponse> getGenres() {
        if (genres == null) {
            genres = new MutableLiveData<>();
            genres = repository.getGenres(type);
        }
        return genres;
    }

    public LiveData<Movie> getMovieItems(int movieId) {
        if (itemsMovie == null) {
            itemsMovie = new MutableLiveData<>();
            itemsMovie = repository.getMovieItems(movieId);
            Log.d("MovieDetail", "Data Created");
        }
        return itemsMovie;
    }

    public LiveData<TrailerResponse> getTrailers(int movieId) {
        if (trailers == null) {
            trailers = new MutableLiveData<>();
            trailers = repository.getTrailers(type, movieId);
        }
        return trailers;
    }

    public LiveData<SimilarResponse> getSimilar(int movieId) {
        if (similar == null) {
            similar = new MutableLiveData<>();
            similar = repository.getSimilar(type, movieId, "similar");
        }
        return similar;
    }

    public LiveData<CreditsResponse> getCredits(int movieId) {
        if (credits == null) {
            credits = new MutableLiveData<>();
            credits = repository.getCredits(type, movieId);
        }
        return credits;
    }
}

package com.dicoding.moviecataloguerv.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.moviecataloguerv.model.Movie;
import com.dicoding.moviecataloguerv.model.TvShow;
import com.dicoding.moviecataloguerv.network.Repository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Movie>> favoriteMovies;
    private LiveData<List<TvShow>> favoriteTvShows;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void addFavoriteMovie(Movie movie) {
        repository.addFavMovie(movie);
        favoriteMovies = repository.getAllFavoriteMovies();
        Log.d("DataMovie", "Added");
    }

    public Movie selectFavMovie(int movieId) {
        Log.d("DataMovie", "Selected");
        return repository.selectFavMovie(movieId);
    }

    public void deleteFavMovie(Movie movie) {
        repository.deleteFavMovie(movie);
        favoriteMovies = repository.getAllFavoriteMovies();
        Log.d("DataMovie", "Deleted");
    }

    public void setFavoriteMovies() {
        favoriteMovies = new MutableLiveData<>();
        favoriteMovies = repository.getAllFavoriteMovies();
        Log.d("FragmentFavoriteMovies", "Created");
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        if (favoriteMovies == null) {
            setFavoriteMovies();
        }
        return favoriteMovies;
    }

    public void addFavoriteTvShow(TvShow tvShow) {
        repository.addFavTv(tvShow);
        favoriteTvShows = repository.getAllFavoriteTv();
        Log.d("DataTv", "Added");
    }

    public TvShow selectFavTv(int tvShowId) {
        Log.d("DataTv", "Selected");
        return repository.selectFavTv(tvShowId);
    }

    public void deleteFavTv(TvShow tvShow) {
        repository.deleteFavTv(tvShow);
        favoriteTvShows = repository.getAllFavoriteTv();
        Log.d("DataTv", "Deleted");
    }

    public void setFavoriteTvShows() {
        favoriteTvShows = new MutableLiveData<>();
        favoriteTvShows = repository.getAllFavoriteTv();
        Log.d("FragmentFavoriteTvShows", "Created");
    }

    public LiveData<List<TvShow>> getFavoriteTvShows() {
        if (favoriteTvShows == null) {
            setFavoriteTvShows();
        }
        return favoriteTvShows;
    }
}

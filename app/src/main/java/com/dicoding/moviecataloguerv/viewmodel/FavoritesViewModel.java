package com.dicoding.moviecataloguerv.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.network.Repository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<MovieItems>> favoriteMovies;
    private LiveData<List<TvShowItems>> favoriteTvShows;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void addFavoriteMovie(MovieItems movieItems) {
        repository.addFavMovie(movieItems);
        favoriteMovies = repository.getAllFavoriteMovies();
        Log.d("DataMovie", "Added");
    }

    public MovieItems selectFavMovie(int movieId) {
        Log.d("DataMovie", "Selected");
        return repository.selectFavMovie(movieId);
    }

    public void deleteFavMovie(MovieItems movieItems) {
        repository.deleteFavMovie(movieItems);
        favoriteMovies = repository.getAllFavoriteMovies();
        Log.d("DataMovie", "Deleted");
    }

    public void setFavoriteMovies() {
        favoriteMovies = new MutableLiveData<>();
        favoriteMovies = repository.getAllFavoriteMovies();
        Log.d("FragmentFavoriteMovies", "Created");
    }

    public LiveData<List<MovieItems>> getFavoriteMovies() {
        if (favoriteMovies == null) {
            setFavoriteMovies();
        }
        return favoriteMovies;
    }

    public void addFavoriteTvShow(TvShowItems tvShowItems) {
        repository.addFavTv(tvShowItems);
        favoriteTvShows = repository.getAllFavoriteTv();
        Log.d("DataTv", "Added");
    }

    public TvShowItems selectFavTv(int tvShowId) {
        Log.d("DataTv", "Selected");
        return repository.selectFavTv(tvShowId);
    }

    public void deleteFavTv(TvShowItems tvShowItems) {
        repository.deleteFavTv(tvShowItems);
        favoriteTvShows = repository.getAllFavoriteTv();
        Log.d("DataTv", "Deleted");
    }

    public void setFavoriteTvShows() {
        favoriteTvShows = new MutableLiveData<>();
        favoriteTvShows = repository.getAllFavoriteTv();
        Log.d("FragmentFavoriteTvShows", "Created");
    }

    public LiveData<List<TvShowItems>> getFavoriteTvShows() {
        if (favoriteTvShows == null) {
            setFavoriteTvShows();
        }
        return favoriteTvShows;
    }
}

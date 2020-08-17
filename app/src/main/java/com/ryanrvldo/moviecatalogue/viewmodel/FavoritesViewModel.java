package com.ryanrvldo.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ryanrvldo.moviecatalogue.data.Repository;
import com.ryanrvldo.moviecatalogue.data.source.model.Movie;
import com.ryanrvldo.moviecatalogue.data.source.model.TvShow;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    private Repository repository;
    private LiveData<List<Movie>> favoriteMovies;
    private LiveData<List<TvShow>> favoriteTvShows;

    public FavoritesViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public void deleteFavMovie(Movie movie) {
        repository.deleteFavMovie(movie);
        favoriteMovies = repository.getFavMovies();
    }

    public void setFavoriteMovies() {
        favoriteMovies = new MutableLiveData<>();
        favoriteMovies = repository.getFavMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        if (favoriteMovies == null) {
            setFavoriteMovies();
        }
        return favoriteMovies;
    }

    public void insertFavoriteTvShow(TvShow tvShow) {
        repository.insertFavTv(tvShow);
        favoriteTvShows = repository.getFavTvShows();
    }

    public TvShow getFavTv(int tvShowId) {
        return repository.getFavTv(tvShowId);
    }

    public void deleteFavTv(TvShow tvShow) {
        repository.deleteFavTv(tvShow);
        favoriteTvShows = repository.getFavTvShows();
    }

    public void setFavoriteTvShows() {
        favoriteTvShows = new MutableLiveData<>();
        favoriteTvShows = repository.getFavTvShows();
    }

    public LiveData<List<TvShow>> getFavoriteTvShows() {
        if (favoriteTvShows == null) {
            setFavoriteTvShows();
        }
        return favoriteTvShows;
    }
}

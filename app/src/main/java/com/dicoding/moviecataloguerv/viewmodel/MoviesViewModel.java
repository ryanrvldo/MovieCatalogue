package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<MovieResponse> popularMovies;
    private MutableLiveData<MovieResponse> nowPlayingMovies;
    private MutableLiveData<MovieResponse> topRatedMovies;
    private MutableLiveData<GenresResponse> genres;
    private MutableLiveData<MovieItems> itemsMovie;
    private MutableLiveData<TrailerResponse> trailers;
    private MutableLiveData<SimilarResponse> similar;
    private MutableLiveData<MovieResponse> searchMovies;

    private Repository repository = Repository.getInstance();
    private String type = "movie";

    public void setPopularMovies(String language) {
        popularMovies = new MutableLiveData<>();
        popularMovies = repository.getMovies("popular", language);
        Log.d("PopularMovies", "Data Created");
    }

    public LiveData<MovieResponse> getPopularMovies(String language) {
        if (popularMovies == null) {
            setPopularMovies(language);
        }
        return popularMovies;
    }

    public void setNowPlayingMovies(String language) {
        nowPlayingMovies = new MutableLiveData<>();
        nowPlayingMovies = repository.getMovies("now_playing", language);
        Log.d("UpcomingMovies", "Data Created");
    }

    public LiveData<MovieResponse> getNowPlayingMovies(String language) {
        if (nowPlayingMovies == null) {
            setNowPlayingMovies(language);
        }
        return nowPlayingMovies;
    }

    public void setTopRatedMovies(String language) {
        topRatedMovies = new MutableLiveData<>();
        topRatedMovies = repository.getMovies("top_rated", language);
        Log.d("TopRatedMovies", "Data Created");
    }

    public LiveData<MovieResponse> getTopRated(String language) {
        if (topRatedMovies == null) {
            setTopRatedMovies(language);
        }
        return topRatedMovies;
    }

    public void setGenres(String language) {
        genres = new MutableLiveData<>();
        genres = repository.getGenres(type, language);
    }

    public LiveData<GenresResponse> getGenres(String language) {
        if (genres == null) {
            setGenres(language);
        }
        return genres;
    }

    private void setItemsMovie(int movieId, String language) {
        itemsMovie = new MutableLiveData<>();
        itemsMovie = repository.getMovieItems(movieId, language);
        Log.d("MovieDetail", "Data Created");
    }

    public LiveData<MovieItems> getMovieItems(int movieId, String language) {
        if (itemsMovie == null) {
            setItemsMovie(movieId, language);
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

    public void setSearchMovies(String query) {
        searchMovies = new MutableLiveData<>();
        searchMovies = repository.searchMovies(query);
        Log.d("SearchMovie", "Fetched");
    }

    public LiveData<MovieResponse> getSearchMovies(String query) {
        if (searchMovies == null) {
            setSearchMovies(query);
        }
        return searchMovies;
    }
}

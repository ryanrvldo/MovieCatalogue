package com.dicoding.moviecataloguerv.data;

import androidx.lifecycle.LiveData;

import com.dicoding.moviecataloguerv.data.source.model.Movie;
import com.dicoding.moviecataloguerv.data.source.model.Search;
import com.dicoding.moviecataloguerv.data.source.model.Season;
import com.dicoding.moviecataloguerv.data.source.model.TvShow;
import com.dicoding.moviecataloguerv.data.source.remote.response.MovieResponse;
import com.dicoding.moviecataloguerv.data.source.remote.response.TvShowResponse;

import java.util.List;

public interface DataSource {
    LiveData<MovieResponse> getMovies(String category);

    LiveData<MovieResponse> searchMovies(String query);

    LiveData<MovieResponse> getNewReleaseMovies();

    LiveData<Movie> getMovieDetail(int movieId);

    LiveData<TvShowResponse> getTvShows(String category);

    LiveData<TvShowResponse> searchTvShows(String query);

    LiveData<TvShowResponse> getNewReleaseTvShows();

    LiveData<TvShow> getTvDetail(int tvShowId);

    LiveData<Season> getSeasonDetail(int tvId, int seasonNumber);

    LiveData<List<Movie>> getFavMovies();

    void insertFavMovie(Movie movie);

    void deleteFavMovie(Movie movie);

    Movie getFavMovie(int movieId);

    LiveData<List<TvShow>> getFavTvShows();

    void insertFavTv(TvShow tvShow);

    void deleteFavTv(TvShow tvShow);

    TvShow getFavTv(int tvShowId);

    LiveData<List<Search>> getSearchHistory();

    void insertSearch(Search search);

    void deleteSearch(Search search);

    void deleteAllSearchHistory();

    String getSearch(String query);
}

package com.ryanrvldo.moviecatalogue.data.source.remote;

import com.ryanrvldo.moviecatalogue.data.source.model.Movie;
import com.ryanrvldo.moviecatalogue.data.source.model.Season;
import com.ryanrvldo.moviecatalogue.data.source.model.TvShow;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.MovieResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbAPI {

    @GET("movie/{category}")
    Call<MovieResponse> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("tv/{category}")
    Call<TvShowResponse> getTvShows(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );


    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendQuery
    );

    @GET("tv/{tv_id}")
    Call<TvShow> getTvShow(
            @Path("tv_id") int id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendQuery
    );

    @GET("search/movie")
    Call<MovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @GET("search/tv")
    Call<TvShowResponse> searchTvShows(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @GET("discover/movie")
    Call<MovieResponse> getNewReleaseMovies(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String todayDate,
            @Query("primary_release_date.lte") String today_date
    );

    @GET("discover/tv")
    Call<TvShowResponse> getNewReleaseTVShow(
            @Query("api_key") String apiKey,
            @Query("first_air_date.gte") String todayDate,
            @Query("first_air_date.lte") String today_date
    );

    @GET("tv/{tv_id}/season/{season_number}")
    Call<Season> getSeasonTvDetail(
            @Path("tv_id") int id,
            @Path("season_number") int number,
            @Query("api_key") String apiKey
    );

}

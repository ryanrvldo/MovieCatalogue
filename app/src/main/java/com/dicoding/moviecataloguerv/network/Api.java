package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.TvShowItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("discover/movie")
    Call<MovieResponse> getDiscoverMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("discover/tv")
    Call<TvShowResponse> getDiscoverTvShows(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<MovieItems> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<TvShowItems> getTvShow(
            @Path("tv_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/movie/list")
    Call<GenresResponse> getMovieGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/tv/list")
    Call<GenresResponse> getTvGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("tv/{tv_id}/videos")
    Call<TrailerResponse> getTvTrailers(
            @Path("tv_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );
}

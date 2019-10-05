package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("movie/{category}")
    Call<MovieResponse> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/{category}")
    Call<TvShowResponse> getTvShows(
            @Path("category") String category,
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

    @GET("genre/{type}/list")
    Call<GenresResponse> getGenres(
            @Path("type") String type,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
//
//    @GET("genre/tv/list")
//    Call<GenresResponse> getTvGenres(
//            @Query("api_key") String apiKey,
//            @Query("language") String language
//    );

    @GET("{type}/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("type") String type,
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
//
//    @GET("tv/{tv_id}/videos")
//    Call<TrailerResponse> getTvTrailers(
//            @Path("tv_id") int id,
//            @Query("api_key") String apiKey,
//            @Query("language") String language
//    );

    @GET("{type}/{movie_id}/similar")
    Call<SimilarResponse> getSimilar(
            @Path("type") String type,
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
//
//    @GET("tv/{tv_id}/similar")
//    Call<SimilarResponse> getTvSimilar(
//            @Path("tv_id") int id,
//            @Query("api_key") String apiKey,
//            @Query("language") String language
//    );
}

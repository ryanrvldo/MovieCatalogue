package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.CreditsResponse;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.Movie;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.Season;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShow;
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

    @GET("genre/{type}/list")
    Call<GenresResponse> getGenres(
            @Path("type") String type,
            @Query("api_key") String apiKey
    );

    @GET("{type}/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("type") String type,
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("{type}/{type_id}/{category}")
    Call<SimilarResponse> getSimilar(
            @Path("type") String type,
            @Path("type_id") int id,
            @Path("category") String category,
            @Query("api_key") String apiKey
    );

    @GET("{type}/{type_id}/credits")
    Call<CreditsResponse> getCredits(
            @Path("type") String type,
            @Path("type_id") int id,
            @Query("api_key") String apiKey
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
    Call<Season> getTvSeasons(
            @Path("tv_id") int id,
            @Path("season_number") int number,
            @Query("api_key") String apiKey
    );
}

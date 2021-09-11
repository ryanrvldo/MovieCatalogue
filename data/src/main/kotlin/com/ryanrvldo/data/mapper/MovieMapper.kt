package com.ryanrvldo.data.mapper

import com.ryanrvldo.data.network.response.commons.GenreResponse
import com.ryanrvldo.data.network.response.movies.MovieDetailsResponse
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.domain.model.movies.Movie
import com.ryanrvldo.domain.model.movies.MovieDetails
import com.ryanrvldo.domain.util.orDefault
import javax.inject.Inject

class MovieMapper @Inject constructor(
    private val imageMapper: ImageMapper,
    private val videoMapper: VideoMapper,
    private val castMapper: CastMapper
) {

    fun mapMovieResponseToDomain(response: MovieResponse) = Movie(
        id = response.id,
        title = response.title,
        overview = response.overview.orEmpty(),
        posterPath = response.posterPath,
        backdropPath = response.backdropPath,
        releaseDate = response.releaseDate,
        rating = response.rating,
        ratingVotes = response.ratingVotes
    )

    fun mapMovieDetailsResponseToDomain(response: MovieDetailsResponse) = MovieDetails(
        id = response.id,
        title = response.title,
        overview = response.overview.orDefault(),
        posterPath = response.posterPath,
        backdropPath = response.backdropPath,
        releaseDate = response.releaseDate,
        rating = response.rating,
        ratingVotes = response.ratingVotes,
        duration = response.duration,
        genres = response.genres.map(GenreResponse::name),
        backdropImages = response.images.backdrops.map(imageMapper::mapImageResponseToDomain),
        videos = response.videos.results.map(videoMapper::mapVideoResponseToDomain),
        casts = response.credits.casts.map(castMapper::mapCastResponseToDomain)
    )

}
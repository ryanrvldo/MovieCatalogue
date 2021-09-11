package com.ryanrvldo.data.mapper

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.PagingResponse
import com.ryanrvldo.data.network.response.commons.GenreResponse
import com.ryanrvldo.data.network.response.movies.MovieDetailsResponse
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.data.util.convertToObjectFromJson
import com.ryanrvldo.domain.model.movies.Movie
import com.ryanrvldo.domain.model.movies.MovieDetails
import com.ryanrvldo.domain.util.orDefault
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class MovieMapperTest {

    private val imageMapper = ImageMapper()
    private val videoMapper = VideoMapper()
    private val castMapper = CastMapper()
    private val mapper = MovieMapper(imageMapper, videoMapper, castMapper)

    // GIVEN
    private val movieResponse: MovieResponse by lazy {
        convertToObjectFromJson<PagingResponse<MovieResponse>>(FakeResponse.POPULAR_MOVIES_PAGE_1)!!
            .results
            .first()
    }
    private val movieDetailsResponse: MovieDetailsResponse by lazy {
        convertToObjectFromJson(FakeResponse.MOVIE_DETAILS_WITH_APPEND_846214)!!
    }

    @Test
    @DisplayName("given movie response, should map response to domain")
    fun testMapMovieResponse_ToDomainModel() {
        // WHEN
        val actual = mapper.mapMovieResponseToDomain(movieResponse)

        // THEN
        val expected = with(movieResponse) {
            Movie(
                id = id,
                title = title,
                overview = overview.orEmpty(),
                posterPath = posterPath,
                backdropPath = backdropPath,
                releaseDate = releaseDate,
                rating = rating,
                ratingVotes = ratingVotes
            )
        }
        assertThat(actual).isInstanceOf(Movie::class.java)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("given movie details response, should map response to domain")
    fun testMapMovieDetailsResponse_ToDomainModel() {
        // WHEN
        val actual = mapper.mapMovieDetailsResponseToDomain(movieDetailsResponse)

        // THEN
        val expected = with(movieDetailsResponse) {
            MovieDetails(
                id = id,
                title = title,
                overview = overview.orDefault(),
                posterPath = posterPath,
                backdropPath = backdropPath,
                releaseDate = releaseDate,
                rating = rating,
                ratingVotes = ratingVotes,
                duration = duration,
                genres = genres.map(GenreResponse::name),
                backdropImages = images.backdrops.map(imageMapper::mapImageResponseToDomain),
                videos = videos.results.map(videoMapper::mapVideoResponseToDomain),
                casts = credits.casts.map(castMapper::mapCastResponseToDomain)
            )
        }
        assertThat(actual).isInstanceOf(MovieDetails::class.java)
        assertThat(actual).isEqualTo(expected)
    }

}
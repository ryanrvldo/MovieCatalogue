package com.ryanrvldo.data.repository

import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.TestCoroutineExtension
import com.ryanrvldo.data.constants.Category
import com.ryanrvldo.data.extension.assertError
import com.ryanrvldo.data.extension.collectDataForTest
import com.ryanrvldo.data.mapper.CastMapper
import com.ryanrvldo.data.mapper.ImageMapper
import com.ryanrvldo.data.mapper.MovieMapper
import com.ryanrvldo.data.mapper.VideoMapper
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.PagingResponse
import com.ryanrvldo.data.network.response.commons.GenreResponse
import com.ryanrvldo.data.network.response.movies.MovieDetailsResponse
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.data.source.remote.MovieRemoteDataSource
import com.ryanrvldo.data.util.convertToObjectFromJson
import com.ryanrvldo.domain.model.movies.Movie
import com.ryanrvldo.domain.model.movies.MovieDetails
import com.ryanrvldo.domain.repository.MovieRepository
import com.ryanrvldo.domain.util.orDefault
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.RegisterExtension

internal class MovieRepositoryTest {

    companion object {
        @JvmField
        @RegisterExtension
        val testCoroutineExtension = TestCoroutineExtension()
    }


    private val mockRemoteSource: MovieRemoteDataSource = mockk()
    private val mockMapper: MovieMapper = mockk()
    private lateinit var repository: MovieRepository

    // GIVEN
    private val moviePagingResponse: PagingResponse<MovieResponse> by lazy {
        convertToObjectFromJson(FakeResponse.POPULAR_MOVIES_PAGE_1)!!
    }
    private val movieDetailsResponse: MovieDetailsResponse by lazy {
        convertToObjectFromJson(FakeResponse.MOVIE_DETAILS_WITH_APPEND_846214)!!
    }

    @BeforeEach
    internal fun setUp() {
        repository = MovieRepositoryImpl(mockRemoteSource, mockMapper)
    }

    @AfterEach
    internal fun tearDown() {
        clearAllMocks()
    }

    @Nested
    @DisplayName("Get movies by category")
    inner class GetMoviesByCategory {

        @Test
        @DisplayName("given success response when get movies by category, should return correct mapped response")
        fun testGetMoviesByCategory_OnSuccess() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val expected = moviePagingResponse.results.map { mapMovieResponse(it) }
            every { mockRemoteSource.getMoviesByCategory(Category.POPULAR) } returns flowOf(
                PagingData.from(moviePagingResponse.results)
            )
            every { mockMapper.mapMovieResponseToDomain(any()) } answers { mapMovieResponse(arg(0)) }

            // WHEN
            val result = repository.getMoviesByCategory(Category.POPULAR)
                .take(1)
                .toList()
                .first()
            val movieList = result.getOrThrow().collectDataForTest()

            // THEN
            assertThat(movieList).isNotEmpty()
            assertThat(movieList).containsExactlyElementsIn(expected).inOrder()
            verify { mockRemoteSource.getMoviesByCategory(Category.POPULAR) }
            verify { mockMapper.mapMovieResponseToDomain(any()) }
        }

        @Test
        @DisplayName("given error response when get movies by category, should handle error")
        fun testGetMoviesByCategory_OnError() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val fakeException = Exception("Error")
            every { mockRemoteSource.getMoviesByCategory(any()) } returns flow { throw fakeException }

            // WHEN
            val result = repository.getMoviesByCategory(Category.POPULAR)
                .take(1)
                .toList()
                .first()

            // THEN
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()).isInstanceOf(fakeException::class.java)
            verify { mockRemoteSource.getMoviesByCategory(any()) }
        }

    }

    @Nested
    @DisplayName("Get movies by category")
    inner class GetMovieDetails {

        @Test
        @DisplayName("given success response when get movies details, should return success result")
        fun testGetMovieDetails_OnSuccess() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val id = 846214
            val expected: MovieDetails = mapMovieDetailsResponse(movieDetailsResponse)
            coEvery { mockRemoteSource.getMovieDetails(id) } returns movieDetailsResponse
            every { mockMapper.mapMovieDetailsResponseToDomain(any()) } answers {
                mapMovieDetailsResponse(arg(0))
            }

            // WHEN
            val actual = repository.getMovieDetails(id).getOrThrow()

            // THEN
            assertThat(actual).isEqualTo(expected)
            coVerify { mockRemoteSource.getMovieDetails(id) }
            verify { mockMapper.mapMovieDetailsResponseToDomain(any()) }
        }

        @Test
        @DisplayName("given error response when get movie details, should return failure result ")
        fun testGetMovieDetails_OnFailure() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val id = Int.MIN_VALUE
            val fakeException = Exception("Error")
            coEvery { mockRemoteSource.getMovieDetails(id) } throws fakeException

            // WHEN
            val result = repository.getMovieDetails(id)

            // THEN
            result.assertError(fakeException)
            coEvery { mockRemoteSource.getMovieDetails(id) }
        }

    }

    private fun mapMovieResponse(response: MovieResponse) = Movie(
        id = response.id,
        title = response.title,
        overview = response.overview.orEmpty(),
        posterPath = response.posterPath,
        backdropPath = response.backdropPath,
        releaseDate = response.releaseDate,
        rating = response.rating,
        ratingVotes = response.ratingVotes
    )

    private fun mapMovieDetailsResponse(response: MovieDetailsResponse) = MovieDetails(
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
        backdropImages = response.images.backdrops.map(ImageMapper()::mapImageResponseToDomain),
        videos = response.videos.results.map(VideoMapper()::mapVideoResponseToDomain),
        casts = response.credits.casts.map(CastMapper()::mapCastResponseToDomain)
    )

}
package com.ryanrvldo.data.source.remote

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.TestCoroutineExtension
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.data.network.service.MovieService
import com.ryanrvldo.data.source.paging.MoviePagingSource
import com.ryanrvldo.data.util.convertToObjectFromJson
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.RegisterExtension

internal class MovieRemoteDataSourceTest {

    companion object {
        @JvmField
        @RegisterExtension
        val testCoroutineExtension = TestCoroutineExtension()
    }

    private val mockMovieService: MovieService = mockk()
    private val mockMoviePagingSource: MoviePagingSource = mockk()
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource

    @BeforeEach
    fun setUp() {
        movieRemoteDataSource = MovieRemoteDataSource(mockMovieService, mockMoviePagingSource)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(mockMovieService, mockMoviePagingSource)
    }

    @Nested
    @DisplayName("Get movie details")
    inner class GetMovieDetails {

        @Test
        @DisplayName("given invalid id when get movie details, should throws exception")
        internal fun testGetMovieDetails_WithInvalidId() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val id = Int.MIN_VALUE
            val fakeException = Exception("Invalid id.")
            coEvery { mockMovieService.getDetails(id) } throws fakeException

            // WHEN
            val actualException = assertThrows<Exception> {
                movieRemoteDataSource.getMovieDetails(id)
            }

            // THEN
            assertThat(actualException).isEqualTo(fakeException)
            coVerify { mockMovieService.getDetails(id) }
        }

        @Test
        @DisplayName("given valid id when get movie details, should return correct response")
        internal fun testGetMovieDetails_WithValidId() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val id = 846214
            val expected: MovieResponse =
                convertToObjectFromJson(FakeResponse.MOVIE_DETAILS_WITH_APPEND_846214)!!
            coEvery { mockMovieService.getDetails(id) } returns expected

            // WHEN
            val actual = movieRemoteDataSource.getMovieDetails(id)

            // THEN
            assertThat(actual).isEqualTo(expected)
            coVerify { mockMovieService.getDetails(id) }
        }

    }

}
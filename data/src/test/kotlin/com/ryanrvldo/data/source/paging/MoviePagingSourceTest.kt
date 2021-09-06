package com.ryanrvldo.data.source.paging

import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingSource.LoadResult.Page
import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.TestCoroutineExtension
import com.ryanrvldo.data.constants.Category
import com.ryanrvldo.data.constants.Constants
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.PagingResponse
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.data.network.service.MovieService
import com.ryanrvldo.data.util.convertToObjectFromJson
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class MoviePagingSourceTest {

    companion object {
        @JvmField
        @RegisterExtension
        val testCoroutineExtension = TestCoroutineExtension()
    }

    private val mockMovieService: MovieService = mockk()
    private lateinit var moviePagingSource: MoviePagingSource

    @BeforeEach
    internal fun setUp() {
        moviePagingSource = MoviePagingSource(mockMovieService)
    }

    @AfterEach
    internal fun tearDown() {
        clearMocks(mockMovieService)
    }

    @Test
    @DisplayName("given success response when load movie page 1, should return correct data, prev key and next key")
    internal fun testLoadReturnPage1_OnSuccess() = testCoroutineExtension.runBlockingTest {
        // GIVEN
        val fakeResponse =
            convertToObjectFromJson<PagingResponse<MovieResponse>>(FakeResponse.POPULAR_MOVIES_PAGE_1)!!
        coEvery { mockMovieService.getByCategory(Category.POPULAR) } returns fakeResponse

        // WHEN
        val actual: Page<Int, MovieResponse> = moviePagingSource.load(
            Refresh(
                key = null,
                loadSize = Constants.DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        ) as Page<Int, MovieResponse>

        // THEN
        val expected = Page(
            data = fakeResponse.results,
            prevKey = null,
            nextKey = 2
        )
        assertThat(actual).isEqualTo(expected)
        assertThat(actual.data).containsExactlyElementsIn(expected.data).inOrder()
        coVerify(exactly = 1) { mockMovieService.getByCategory(Category.POPULAR) }
    }

    @Test
    @DisplayName("given success response when load movie page 2, should return correct data, prev key and next key")
    internal fun testLoadReturnPage2_OnSuccess() = testCoroutineExtension.runBlockingTest {
        // GIVEN
        val fakeResponse =
            convertToObjectFromJson<PagingResponse<MovieResponse>>(FakeResponse.POPULAR_MOVIES_PAGE_2)!!
        coEvery { mockMovieService.getByCategory(Category.POPULAR, 2) } returns fakeResponse

        // WHEN
        val actual = moviePagingSource.load(
            Refresh(
                key = 2,
                loadSize = Constants.DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        ) as Page<Int, MovieResponse>

        // THEN
        val expected = Page(
            data = fakeResponse.results,
            prevKey = 1,
            nextKey = 3
        )
        assertThat(actual).isEqualTo(expected)
        assertThat(actual.data).containsExactlyElementsIn(expected.data).inOrder()
        coVerify(exactly = 1) { mockMovieService.getByCategory(Category.POPULAR, 2) }
    }

    @Test
    @DisplayName("given error response when load movies, should error result")
    internal fun testLoadReturnPage_OnError() = testCoroutineExtension.runBlockingTest {
        // GIVEN
        val fakeException = Exception("Error")
        coEvery { mockMovieService.getByCategory(Category.POPULAR) } throws fakeException

        // WHEN
        val actual = moviePagingSource.load(
            Refresh(
                key = null,
                loadSize = Constants.DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        ) as Error<Int, MovieResponse>

        // THEN
        val expected = Error<Int, MovieResponse>(fakeException)
        assertThat(actual).isEqualTo(expected)
        assertThat(actual.throwable).isEqualTo(expected.throwable)
        coVerify(exactly = 1) { mockMovieService.getByCategory(Category.POPULAR) }
    }

}
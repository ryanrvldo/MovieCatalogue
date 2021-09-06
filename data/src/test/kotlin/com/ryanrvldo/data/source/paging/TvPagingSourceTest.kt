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
import com.ryanrvldo.data.network.response.tvshows.TvShowDetailsResponse
import com.ryanrvldo.data.network.response.tvshows.TvShowResponse
import com.ryanrvldo.data.network.service.TvShowService
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

internal class TvPagingSourceTest {

    companion object {
        @JvmField
        @RegisterExtension
        val testCoroutineExtension = TestCoroutineExtension()
    }

    private val mockTvShowService: TvShowService = mockk()
    private lateinit var tvPagingSource: TvPagingSource

    @BeforeEach
    fun setUp() {
        tvPagingSource = TvPagingSource(mockTvShowService)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(mockTvShowService)
    }

    @Test
    @DisplayName("given success response when load tv page 1, should return correct data, prev key and next key")
    internal fun testLoadReturnPage1_OnSuccess() = testCoroutineExtension.runBlockingTest {
        // GIVEN
        val fakeResponse =
            convertToObjectFromJson<PagingResponse<TvShowResponse>>(FakeResponse.POPULAR_TV_SHOW_PAGE_1)!!
        coEvery { mockTvShowService.getByCategory(Category.POPULAR) } returns fakeResponse

        // WHEN
        val actual = tvPagingSource.load(
            Refresh(
                key = null,
                loadSize = Constants.DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        ) as Page<Int, TvShowResponse>

        // THEN
        val expected = Page(
            data = fakeResponse.results,
            prevKey = null,
            nextKey = 2
        )
        assertThat(actual).isEqualTo(expected)
        assertThat(actual.data).containsExactlyElementsIn(expected.data).inOrder()
        coVerify(exactly = 1) { mockTvShowService.getByCategory(Category.POPULAR) }
    }

    @Test
    @DisplayName("given success response when load tv page 2, should return correct data, prev key and next key")
    internal fun testLoadReturnPage2_OnSuccess() = testCoroutineExtension.runBlockingTest {
        // GIVEN
        val fakeResponse =
            convertToObjectFromJson<PagingResponse<TvShowResponse>>(FakeResponse.POPULAR_TV_SHOW_PAGE_2)!!
        coEvery { mockTvShowService.getByCategory(Category.POPULAR, 2) } returns fakeResponse

        // WHEN
        val actual = tvPagingSource.load(
            Refresh(
                key = 2,
                loadSize = Constants.DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        ) as Page<Int, TvShowResponse>

        // THEN
        val expected = Page(
            data = fakeResponse.results,
            prevKey = 1,
            nextKey = 3
        )
        assertThat(actual).isEqualTo(expected)
        assertThat(actual.data).containsExactlyElementsIn(expected.data).inOrder()
        coVerify(exactly = 1) { mockTvShowService.getByCategory(Category.POPULAR, 2) }
    }

    @Test
    @DisplayName("given error response when load tv shows, should error result")
    internal fun testLoadReturnPage_OnError() = testCoroutineExtension.runBlockingTest {
        // GIVEN
        val fakeException = Exception("Error")
        coEvery { mockTvShowService.getByCategory(Category.POPULAR) } throws fakeException

        // WHEN
        val actual = tvPagingSource.load(
            Refresh(
                key = null,
                loadSize = Constants.DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        ) as Error<Int, TvShowResponse>

        // THEN
        val expected = Error<Int, TvShowDetailsResponse>(fakeException)
        assertThat(actual).isEqualTo(expected)
        assertThat(actual.throwable).isEqualTo(expected.throwable)
        coVerify(exactly = 1) { mockTvShowService.getByCategory(Category.POPULAR) }
    }

}
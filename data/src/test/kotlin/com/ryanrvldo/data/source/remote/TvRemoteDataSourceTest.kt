package com.ryanrvldo.data.source.remote

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.TestCoroutineExtension
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.tvshows.TvShowResponse
import com.ryanrvldo.data.network.service.TvShowService
import com.ryanrvldo.data.source.paging.TvPagingSource
import com.ryanrvldo.data.util.convertToObjectFromJson
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.RegisterExtension

internal class TvRemoteDataSourceTest {

    companion object {
        @JvmField
        @RegisterExtension
        val testCoroutineExtension = TestCoroutineExtension()
    }

    private val mockTvShowService: TvShowService = mockk()
    private val mockTvPagingSource: TvPagingSource = mockk()
    private lateinit var tvRemoteDataSource: TvRemoteDataSource

    @BeforeEach
    fun setUp() {
        tvRemoteDataSource = TvRemoteDataSource(mockTvShowService, mockTvPagingSource)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(mockTvShowService, mockTvPagingSource)
    }

    @Nested
    @DisplayName("Get tv show details")
    inner class GetTvShowDetails {

        @Test
        @DisplayName("given invalid id when get tv show details, should throws exception")
        internal fun testGetTvShowDetails_WithInvalidId() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val id = Int.MIN_VALUE
            val fakeException = Exception("Invalid id.")
            coEvery { mockTvShowService.getDetails(id) } throws fakeException

            // WHEN
            val actualException = assertThrows<Exception> {
                tvRemoteDataSource.getTvShowDetails(id)
            }

            // THEN
            assertThat(actualException).isEqualTo(fakeException)
            coVerify { mockTvShowService.getDetails(id) }
        }

        @Test
        @DisplayName("given valid id when get tv show details, should return correct response")
        internal fun testGetTvShowDetails_WithValidId() = testCoroutineExtension.runBlockingTest {
            // GIVEN
            val id = 95281
            val expected: TvShowResponse =
                convertToObjectFromJson(FakeResponse.TV_SHOW_DETAILS_WITH_APPEND_95281)!!
            coEvery { mockTvShowService.getDetails(id) } returns expected

            // WHEN
            val actual = tvRemoteDataSource.getTvShowDetails(id)

            // THEN
            assertThat(actual).isEqualTo(expected)
            coVerify { mockTvShowService.getDetails(id) }
        }

    }

}
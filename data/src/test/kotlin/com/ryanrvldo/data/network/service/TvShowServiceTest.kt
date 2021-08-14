package com.ryanrvldo.data.network.service

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.BuildConfig
import com.ryanrvldo.data.constants.Category.POPULAR
import com.ryanrvldo.data.constants.HttpMethod
import com.ryanrvldo.data.di.NetworkModule
import com.ryanrvldo.data.network.response.ErrorResponse
import com.ryanrvldo.data.network.response.FakeResponse.NOT_FOUND_ERROR
import com.ryanrvldo.data.network.response.FakeResponse.POPULAR_TV_SHOW_PAGE_1
import com.ryanrvldo.data.network.response.FakeResponse.POPULAR_TV_SHOW_PAGE_2
import com.ryanrvldo.data.network.response.FakeResponse.TV_SHOW_DETAILS_WITH_APPEND_95281
import com.ryanrvldo.data.network.response.FakeResponse.UNAUTHORIZED_ERROR
import com.ryanrvldo.data.util.convertToObjectFromJson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.*
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

internal class TvShowServiceTest : BaseServiceTest() {

    private lateinit var service: TvShowService

    @BeforeEach
    override fun setUp() {
        super.setUp()
        service = NetworkModule.provideTvShowService(fakeRetrofit)
    }

    private val _dispatcher = TvShowDispatcher()
    override val dispatcher: QueueDispatcher
        get() = _dispatcher

    override suspend fun exampleServiceCall(): Any = service.getByCategory(POPULAR)

    @Nested
    @DisplayName("Get tv show by category")
    inner class GetTvShowByCategory {

        @Test
        @DisplayName("given error result when get unknown tv shows category, should return 404 error response")
        internal fun testGetUnknownCategory() = runBlocking {
            // GIVEN
            val mockResponse = enqueueResponse(HTTP_NOT_FOUND, NOT_FOUND_ERROR)
            val expected =
                convertToObjectFromJson<ErrorResponse>(mockResponse.getBody()!!.readUtf8())

            // WHEN
            val exception = assertThrows<HttpException> { service.getByCategory("unknown") }

            // THEN
            val actual = convertToObjectFromJson<ErrorResponse>(
                mockWebServer.dispatcher.peek().getBody()!!.readUtf8()
            )
            assertThat(exception.code()).isEqualTo(HTTP_NOT_FOUND)
            assertThat(exception.message).isEqualTo("HTTP $HTTP_NOT_FOUND Client Error")
            assertThat(actual!!.statusCode).isEqualTo(expected!!.statusCode)
            assertThat(actual.statusMessage).isEqualTo(expected.statusMessage)
            assertThat(actual.success).isNull()
        }

        @Test
        @DisplayName("given success result when get popular tv shows, should have correct url and response code")
        internal fun testGetPopularMoviesThenValidateRequest() {
            givenSuccessResultThenAssertCorrectRequest(
                HttpMethod.GET,
                "/tv/$POPULAR?page=1&api_key=${BuildConfig.TMDB_API_KEY}"
            ) {
                service.getByCategory(POPULAR)
            }
        }

        @Test
        @DisplayName("given success result when get popular tv shows page 1, should have correct response")
        internal fun testGetPopularMoviesPage1() {
            givenSuccessResultThenAssertResponse(
                POPULAR_TV_SHOW_PAGE_1,
                { service.getByCategory(POPULAR) }
            ) { expected, actual ->
                assertThat(actual.page).isEqualTo(expected.page)
                assertThat(actual.results).containsExactlyElementsIn(expected.results).inOrder()
            }
        }

        @Test
        @DisplayName("given success result when get popular tv shows page 2, should have correct response")
        internal fun testGetPopularMoviesPage2() {
            givenSuccessResultThenAssertResponse(
                POPULAR_TV_SHOW_PAGE_2,
                { service.getByCategory(POPULAR, 2) }
            ) { expected, actual ->
                assertThat(actual.page).isEqualTo(expected.page)
                assertThat(actual.results).containsExactlyElementsIn(expected.results).inOrder()
            }
        }

    }

    @Nested
    @DisplayName("Get tv show details")
    inner class GetTvShowDetails {

        private val dummyId = 95281

        @Test
        @DisplayName("given success result when get tv show details, should have correct url and response code")
        internal fun testGetMovieByIdThenValidateRequest() {
            givenSuccessResultThenAssertCorrectRequest(
                HttpMethod.GET,
                "/tv/$dummyId?append_to_response=${TvShowService.DETAIL_APPEND_QUERY}&api_key=${BuildConfig.TMDB_API_KEY}"
            ) {
                service.getDetails(dummyId)
            }
        }

        @Test
        @DisplayName("given success result when get tv show details, should return correct response")
        internal fun testGetMovieById() {
            givenSuccessResultThenAssertResponse(
                TV_SHOW_DETAILS_WITH_APPEND_95281,
                { service.getDetails(dummyId) }
            ) { expected, actual ->
                assertThat(actual.id).isEqualTo(expected.id)

                assertThat(actual.images).isNotNull()
                assertThat(actual.images.backdrops)
                    .containsExactlyElementsIn(expected.images.backdrops)
                    .inOrder()
                assertThat(actual.images.posters)
                    .containsExactlyElementsIn(expected.images.posters)
                    .inOrder()

                assertThat(actual.contentRatings).isNotNull()
                assertThat(actual.contentRatings.id).isNull()
                assertThat(actual.contentRatings.results)
                    .containsExactlyElementsIn(expected.contentRatings.results)
                    .inOrder()

                assertThat(actual.videos).isNotNull()
                assertThat(actual.videos.id).isNull()
                assertThat(actual.videos.results)
                    .containsExactlyElementsIn(expected.videos.results)
                    .inOrder()

                assertThat(actual.credits).isNotNull()
                assertThat(actual.credits.id).isNull()
                assertThat(actual.credits.casts)
                    .containsExactlyElementsIn(expected.credits.casts)
                    .inOrder()
                assertThat(actual.credits.crews)
                    .containsExactlyElementsIn(expected.credits.crews)
                    .inOrder()

                assertThat(actual.similar).isNotNull()
                assertThat(actual.similar.page).isEqualTo(1)
                assertThat(actual.similar.results)
                    .containsExactlyElementsIn(expected.similar.results)
                    .inOrder()
            }
        }

    }

    inner class TvShowDispatcher : QueueDispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path: String = request.path!!
            return when {
                !path.endsWith(BuildConfig.TMDB_API_KEY) -> enqueueResponse(
                    HTTP_UNAUTHORIZED,
                    UNAUTHORIZED_ERROR
                )
                path.matches("/tv/unknown.*".toRegex()) -> enqueueResponse(
                    HTTP_NOT_FOUND,
                    NOT_FOUND_ERROR
                )
                path.matches("/tv/+$POPULAR\\?page=1.*".toRegex()) -> enqueueResponse(responseBody = POPULAR_TV_SHOW_PAGE_1)
                path.matches("/tv/+$POPULAR\\?page=2.*".toRegex()) -> enqueueResponse(responseBody = POPULAR_TV_SHOW_PAGE_2)
                path.matches("/tv/+\\d.*".toRegex()) -> enqueueResponse(responseBody = TV_SHOW_DETAILS_WITH_APPEND_95281)
                else -> super.dispatch(request)
            }
        }
    }

}
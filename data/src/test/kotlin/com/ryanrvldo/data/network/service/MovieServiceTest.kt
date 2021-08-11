package com.ryanrvldo.data.network.service

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.BuildConfig
import com.ryanrvldo.data.constants.Category.MOVIE_POPULAR
import com.ryanrvldo.data.constants.Constants
import com.ryanrvldo.data.constants.HttpMethod
import com.ryanrvldo.data.di.NetworkInjection.provideMovieService
import com.ryanrvldo.data.network.response.ErrorResponse
import com.ryanrvldo.data.network.response.FakeResponse.MOVIE_DETAILS_WITH_APPEND_846214
import com.ryanrvldo.data.network.response.FakeResponse.NOT_FOUND_ERROR
import com.ryanrvldo.data.network.response.FakeResponse.POPULAR_MOVIES_PAGE_1
import com.ryanrvldo.data.network.response.FakeResponse.POPULAR_MOVIES_PAGE_2
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

internal class MovieServiceTest : BaseServiceTest() {

    private lateinit var service: MovieService

    @BeforeEach
    override fun setUp() {
        super.setUp()
        service = provideMovieService(fakeRetrofit)
    }

    override suspend fun exampleServiceCall(): Any = service.getByCategory(MOVIE_POPULAR)

    private val _dispatcher = MovieDispatcher()
    override val dispatcher: QueueDispatcher
        get() = _dispatcher

    @Nested
    @DisplayName("Get movie by category")
    inner class GetMovieByCategory {

        @Test
        @DisplayName("given error result when get unknown movies category, should return 404 error response")
        internal fun testGetUnknownCategory() =
            runBlocking {
                val mockResponse =
                    enqueueResponse(HTTP_NOT_FOUND, responseBody = NOT_FOUND_ERROR)
                val expected =
                    convertToObjectFromJson<ErrorResponse>(mockResponse.getBody()!!.readUtf8())

                val exception = assertThrows<HttpException> { service.getByCategory("unknown") }
                assertThat(exception.code()).isEqualTo(HTTP_NOT_FOUND)
                assertThat(exception.message).isEqualTo("HTTP $HTTP_NOT_FOUND Client Error")

                val actual = convertToObjectFromJson<ErrorResponse>(
                    mockWebServer.dispatcher.peek().getBody()!!.readUtf8()
                )
                assertThat(actual!!.statusCode).isEqualTo(expected!!.statusCode)
                assertThat(actual.statusMessage).isEqualTo(expected.statusMessage)
                assertThat(actual.success).isNull()
            }

        @Test
        @DisplayName("given success result when get popular movies, should have correct url and response code")
        internal fun testGetPopularMoviesThenValidateRequest() {
            givenSuccessResultThenAssertCorrectRequest(
                HttpMethod.GET,
                "/movie/$MOVIE_POPULAR?page=1&api_key=${BuildConfig.TMDB_API_KEY}"
            ) {
                service.getByCategory(MOVIE_POPULAR)
            }
        }

        @Test
        @DisplayName("given success result when get popular movies page 1, should have correct response")
        internal fun testGetPopularMoviesPage1() {
            givenSuccessResultThenAssertResponse(
                POPULAR_MOVIES_PAGE_1,
                { service.getByCategory(MOVIE_POPULAR) }
            ) { expected, actual ->
                assertThat(actual.page).isEqualTo(expected.page)
                assertThat(actual.results).containsExactlyElementsIn(expected.results).inOrder()
            }
        }

        @Test
        @DisplayName("given success result when get popular movies page 2, should have correct response")
        internal fun testGetPopularMoviesPage2() {
            givenSuccessResultThenAssertResponse(
                POPULAR_MOVIES_PAGE_2,
                { service.getByCategory(MOVIE_POPULAR, 2) }
            ) { expected, actual ->
                assertThat(actual.page).isEqualTo(expected.page)
                assertThat(actual.results).containsExactlyElementsIn(expected.results).inOrder()
            }
        }

    }

    @Nested
    @DisplayName("Get movie details")
    inner class GetMovieDetails {

        private val dummyId = 846214

        @Test
        @DisplayName("given success result when get movie details, should have correct url and response code")
        internal fun testGetMovieByIdThenValidateRequest() {
            givenSuccessResultThenAssertCorrectRequest(
                HttpMethod.GET,
                "/movie/$dummyId?append_to_response=${Constants.MOVIE_APPEND_QUERY}&api_key=${BuildConfig.TMDB_API_KEY}"
            ) {
                service.getDetails(dummyId)
            }
        }

        @Test
        @DisplayName("given success result when get movie by id, should return correct response")
        internal fun testGetMovieById() {
            givenSuccessResultThenAssertResponse(
                MOVIE_DETAILS_WITH_APPEND_846214,
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
                assertThat(actual.similars).isNotNull()
                assertThat(actual.similars.page).isEqualTo(1)
                assertThat(actual.similars.results)
                    .containsExactlyElementsIn(expected.similars.results)
                    .inOrder()
            }
        }
    }

    inner class MovieDispatcher : QueueDispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path!!
            return when {
                !path.endsWith(BuildConfig.TMDB_API_KEY) -> enqueueResponse(
                    HTTP_UNAUTHORIZED,
                    responseBody = UNAUTHORIZED_ERROR
                )
                path.matches("/movie/unknown.*".toRegex()) -> enqueueResponse(
                    HTTP_NOT_FOUND,
                    responseBody = NOT_FOUND_ERROR
                )
                path.matches("/movie/+${MOVIE_POPULAR}\\?page=1.*".toRegex()) -> enqueueResponse(
                    responseBody = POPULAR_MOVIES_PAGE_1
                )
                path.matches("/movie/+${MOVIE_POPULAR}\\?page=2.*".toRegex()) -> enqueueResponse(
                    responseBody = POPULAR_MOVIES_PAGE_2
                )
                path.matches("/movie/+\\d.*".toRegex()) -> enqueueResponse(responseBody = MOVIE_DETAILS_WITH_APPEND_846214)
                else -> super.dispatch(request)
            }
        }
    }

}
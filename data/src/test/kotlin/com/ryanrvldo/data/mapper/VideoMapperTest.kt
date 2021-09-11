package com.ryanrvldo.data.mapper

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.commons.VideoResponse
import com.ryanrvldo.data.network.response.movies.MovieDetailsResponse
import com.ryanrvldo.data.util.convertToObjectFromJson
import com.ryanrvldo.domain.model.commons.Video
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class VideoMapperTest {

    private val mapper = VideoMapper()
    private val videoResponse: VideoResponse by lazyOf(
        convertToObjectFromJson<MovieDetailsResponse>(FakeResponse.MOVIE_DETAILS_WITH_APPEND_846214)!!
            .videos
            .results
            .first()
    )

    @Test
    @DisplayName("given video response, should map response to domain model")
    internal fun testMapResponseToDomain() {
        // WHEN
        val actual = mapper.mapVideoResponseToDomain(videoResponse)

        // THEN
        val expected = with(videoResponse) {
            Video(id, key, name, site, type, official, publishedAt)
        }
        assertThat(actual).isInstanceOf(Video::class.java)
        assertThat(actual).isEqualTo(expected)
    }

}
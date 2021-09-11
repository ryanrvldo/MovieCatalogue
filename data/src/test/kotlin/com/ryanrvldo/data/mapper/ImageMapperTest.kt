package com.ryanrvldo.data.mapper

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.commons.ImageResponse
import com.ryanrvldo.data.network.response.movies.MovieDetailsResponse
import com.ryanrvldo.data.util.convertToObjectFromJson
import com.ryanrvldo.domain.model.commons.Image
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ImageMapperTest {

    private val mapper = ImageMapper()
    private val imageResponse: ImageResponse by lazyOf(
        convertToObjectFromJson<MovieDetailsResponse>(FakeResponse.MOVIE_DETAILS_WITH_APPEND_846214)!!
            .images
            .backdrops
            .first()
    )

    @Test
    @DisplayName("given image response, should map response to domain model")
    internal fun testMapResponseToDomain() {
        // WHEN
        val actual = mapper.mapImageResponseToDomain(imageResponse)

        //  THEN
        val expected = with(imageResponse) { Image(filePath, height, width, rating, ratingCount) }
        assertThat(actual).isInstanceOf(Image::class.java)
        assertThat(actual).isEqualTo(expected)
    }

}
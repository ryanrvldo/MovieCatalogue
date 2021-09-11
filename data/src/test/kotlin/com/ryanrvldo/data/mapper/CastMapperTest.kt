package com.ryanrvldo.data.mapper

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.network.response.commons.CastResponse
import com.ryanrvldo.data.network.response.movies.MovieDetailsResponse
import com.ryanrvldo.data.util.convertToObjectFromJson
import com.ryanrvldo.domain.model.commons.Cast
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class CastMapperTest {

    private val mapper = CastMapper()
    private val castResponse: CastResponse by lazyOf(
        convertToObjectFromJson<MovieDetailsResponse>(FakeResponse.MOVIE_DETAILS_WITH_APPEND_846214)!!
            .credits
            .casts
            .first()
    )

    @Test
    @DisplayName("given cast response, should map response to domain model")
    internal fun testMapResponseToDomain() {
        // WHEN
        val actual = mapper.mapCastResponseToDomain(castResponse)

        //  THEN
        val expected = with(castResponse) {
            Cast(
                id = id,
                name = name,
                character = character,
                gender = gender,
                profilePath = profilePath,
                castId = castId,
                creditId = creditId,
                order = order
            )
        }
        assertThat(actual).isInstanceOf(Cast::class.java)
        assertThat(actual).isEqualTo(expected)
    }

}
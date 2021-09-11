package com.ryanrvldo.data.mapper

import com.ryanrvldo.data.network.response.commons.CastResponse
import com.ryanrvldo.domain.model.commons.Cast
import javax.inject.Inject

class CastMapper @Inject constructor() {

    fun mapCastResponseToDomain(response: CastResponse) = Cast(
        id = response.id,
        name = response.name,
        character = response.character,
        gender = response.gender,
        profilePath = response.profilePath,
        castId = response.castId,
        creditId = response.creditId,
        order = response.order
    )

}
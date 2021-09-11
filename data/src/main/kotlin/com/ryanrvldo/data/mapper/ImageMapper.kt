package com.ryanrvldo.data.mapper

import com.ryanrvldo.data.network.response.commons.ImageResponse
import com.ryanrvldo.domain.model.commons.Image
import javax.inject.Inject

class ImageMapper @Inject constructor() {

    fun mapImageResponseToDomain(response: ImageResponse) = Image(
        path = response.filePath,
        height = response.height,
        width = response.width,
        rating = response.rating,
        ratingCount = response.ratingCount
    )

}